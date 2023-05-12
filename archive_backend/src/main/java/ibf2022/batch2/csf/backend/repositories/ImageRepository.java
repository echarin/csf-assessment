package ibf2022.batch2.csf.backend.repositories;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import ibf2022.batch2.csf.backend.models.ArchiveDownload;
import ibf2022.batch2.csf.backend.models.ImageFile;
import ibf2022.batch2.csf.backend.models.S3Upload;
import ibf2022.batch2.csf.exceptions.FileUploadException;

@Repository
public class ImageRepository {

	@Autowired private AmazonS3 s3Client;

    @Value("${do.storage.bucketname}")
    private String bucketName;

	private List<String> IMG_EXTENSIONS = Arrays.asList("png", "gif", "jpg", "jpeg");

	public S3Upload upload(ArchiveDownload ad) throws IOException, FileUploadException {
		System.out.println("Bucket: " + bucketName);
		if (ad.getArchive() == null) {
			throw new FileUploadException("Failed to upload file: No file was provided");
		}

		S3Upload s3u = new S3Upload();
		s3u.setBundleId(this.generateUUID());

		List<ImageFile> images = this.unzipArchive(ad.getArchive());
		Map<String, String> userData = this.returnUserData(ad);

		System.out.println(images.toString());
		
		for (ImageFile image : images) {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(image.getContentType());
			metadata.setContentLength(image.getImageSize());
			metadata.setUserMetadata(userData);
			try (InputStream is = new ByteArrayInputStream(image.getData())) {
				String key = image.getMd5Hash() + "." + image.getFileExtension();
				PutObjectRequest putReq = new PutObjectRequest(bucketName, key, is, metadata);
				putReq.withCannedAcl(CannedAccessControlList.PublicRead);
				s3Client.putObject(putReq);

				String url = s3Client.getUrl(bucketName, key).toString();
				s3u.appendUrl(url);
			}
		}

		s3u.setDate(new Date());
		System.out.println(s3u.toString());
		return s3u;
	}

	private List<ImageFile> unzipArchive(MultipartFile archive) throws IOException {
		ZipInputStream zis = new ZipInputStream(archive.getInputStream());
		ZipEntry ze;
		List<ImageFile> images = new LinkedList<>();
		
		while ((ze = zis.getNextEntry()) != null) {
			String fileName = ze.getName();
			String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			System.out.println(fileExtension);
			if (this.IMG_EXTENSIONS.contains(fileExtension)) {
				System.out.println("Valid file found");
				ImageFile image = new ImageFile();
				image.setFileName(fileName);
				image.setFileExtension(fileExtension);
				image.setContentType("image/" + fileExtension);

				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				IOUtils.copy(zis, bos);
				byte[] byteArray = bos.toByteArray();
				image.setImageSize(byteArray.length);
				image.setData(byteArray);
				image.generateMd5Hash();
				System.out.println(image.toString());

				images.add(image);
			}
			zis.closeEntry();
		}

		zis.close();
		return images;
	}

	private String generateUUID() {
		return UUID.randomUUID().toString().substring(0, 8);
	}

	private Map<String, String> returnUserData(ArchiveDownload ad) {
		Map<String, String> userData = new HashMap<>();
		userData.put("name", ad.getName());
		userData.put("title", ad.getTitle());
		
		if (ad.getComments() != null)
			userData.put("comments", ad.getComments());

		return userData;
	}
}

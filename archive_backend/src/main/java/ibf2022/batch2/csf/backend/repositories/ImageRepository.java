package ibf2022.batch2.csf.backend.repositories;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

	private List<String> IMG_EXTENSIONS = Arrays.asList(".png", ".gif", ".jpg", ".jpeg");

	//TODO: Task 3
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Returns the bundleId of the uploaded bundle
	public S3Upload upload(ArchiveDownload ad) throws IOException, FileUploadException {
		// Expand the ZIP archive, assuming that there are no directories in the ZIP file
		// The ZIP file contains only images in different formats (PNG, GIF, JPG, etc)
		// For each image, do the following
			// Set the content type e.g. image/png for a PNG file, image/gif for GIF, etc
				// Determine image type by examine filename's suffix
			// Set the image size
			// Make the uploaded file publicly available
			// Image files with the same name should not overwrite existing file names with the same name
				// Therefore you should probably hash it together with the date of upload
		// Upload each image into S3 bucket
		if (ad.getArchive() == null) {
			throw new FileUploadException("Failed to upload file: No file was provided");
		}

		S3Upload s3u = new S3Upload();
		s3u.setBundleId(this.generateUUID());

		List<ImageFile> images = this.unzipArchive(ad.getArchive());
		Map<String, String> userData = this.returnUserData(ad);
		for (ImageFile image : images) {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(image.getContentType());
			metadata.setContentLength(image.getImageSize());
			metadata.setUserMetadata(userData);
			try (InputStream is = new FileInputStream(image.getFile())) {
				String key = image.getMd5Hash();
				PutObjectRequest putReq = new PutObjectRequest(bucketName, key, is, metadata);
				putReq.withCannedAcl(CannedAccessControlList.PublicRead);
				s3Client.putObject(putReq);

				s3u.appendUrl(s3Client.getUrl(bucketName, key).toString());
			}
		}

		s3u.setDate(new Date());
		return s3u;
	}

	// Get an input stream out of the MultipartFile archive, which is a ZIP file
	// While there is a next entry, check if the extension matches an image file
	// For each image file, store it into the images folder of the root Spring Boot directory
		// Generate a new ImageFile and then insert metadata through setters
	// Then return List<ImageFile>
	private List<ImageFile> unzipArchive(MultipartFile archive) throws IOException {
		Path path = Paths.get("archive_backend/images");
		ZipInputStream zis = new ZipInputStream(archive.getInputStream());
		ZipEntry ze;
		List<ImageFile> images = new LinkedList<>();
		
		while ((ze = zis.getNextEntry()) != null) {
			String fileName = ze.getName();
			String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			System.out.println(fileExtension);
			if (this.IMG_EXTENSIONS.contains(fileExtension)) {
				ImageFile image = new ImageFile();
				image.setContentType("image/" + fileExtension);
				image.setFileName(fileName);
				image.setImageSize(ze.getSize());
				image.generateMd5Hash();

				File tempFile = Files.createTempFile(path, image.getMd5Hash(), "").toFile();
				try (OutputStream out = Files.newOutputStream(tempFile.toPath())) {
					IOUtils.copy(zis, out);
				}

				image.setFile(tempFile);

				images.add(image);
			} else { continue; }
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

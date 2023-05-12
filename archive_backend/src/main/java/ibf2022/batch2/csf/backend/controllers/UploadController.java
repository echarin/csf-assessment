package ibf2022.batch2.csf.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ibf2022.batch2.csf.backend.models.ArchiveDownload;
import ibf2022.batch2.csf.backend.models.S3Upload;
import ibf2022.batch2.csf.backend.services.ArchiveService;
import ibf2022.batch2.csf.backend.services.ImageService;
import ibf2022.batch2.csf.exceptions.BundleUploadException;
import ibf2022.batch2.csf.exceptions.FileUploadException;
import jakarta.json.Json;

@RestController
@CrossOrigin(origins = "*")
public class UploadController {
	@Autowired private ArchiveService aSvc;
	@Autowired private ImageService imgSvc;

	// TODO: Task 2, Task 3, Task 4
	@PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> postUpload(
		@RequestPart MultipartFile archive, @RequestPart String name, @RequestPart String title, @RequestPart String comments
	) {
		ArchiveDownload ad = ArchiveDownload.create(name, title, comments, archive);

		// To simulate an error
		// FileUploadException fuEx = new FileUploadException("File failed to upload");
		// String errorJsonString = Json.createObjectBuilder().add("error", fuEx.getMessage()).build().toString();
		// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorJsonString);

		try {
			S3Upload s3u = imgSvc.upload(ad);
			String bundleIdJsonString = aSvc.recordBundle(ad, s3u);
			return ResponseEntity.status(HttpStatus.CREATED).body(bundleIdJsonString);
		} catch (FileUploadException | BundleUploadException ex) {
			String errorJsonString = Json.createObjectBuilder().add("error", ex.getMessage()).build().toString();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorJsonString);
		}
	}

	// TODO: Task 5
	

	// TODO: Task 6

}

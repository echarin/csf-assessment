package ibf2022.batch2.csf.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ibf2022.batch2.csf.backend.models.ArchiveDownload;
import ibf2022.batch2.csf.backend.models.ArchiveUpload;
import ibf2022.batch2.csf.backend.models.S3Upload;
import ibf2022.batch2.csf.backend.services.ArchiveService;
import ibf2022.batch2.csf.backend.services.ImageService;
import ibf2022.batch2.csf.exceptions.ArchiveUploadException;
import ibf2022.batch2.csf.exceptions.BundleNotFoundException;
import ibf2022.batch2.csf.exceptions.FileUploadException;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

@RestController
@CrossOrigin(origins = "*")
public class UploadController {
	@Autowired private ArchiveService aSvc;
	@Autowired private ImageService imgSvc;

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
		} catch (FileUploadException | ArchiveUploadException ex) {
			String errorJsonString = Json.createObjectBuilder().add("error", ex.getMessage()).build().toString();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorJsonString);
		}
	}

	@GetMapping(path = "/bundle/{bundleId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getBundleByBundleId(@PathVariable String bundleId) {
		String errorJsonString;
		try {
			ArchiveUpload au = aSvc.getBundleByBundleId(bundleId);
			JsonObject auJsonObject = au.toJson();
			return ResponseEntity.status(HttpStatus.OK).body(auJsonObject.toString());
		} catch (BundleNotFoundException ex) {
			errorJsonString = Json.createObjectBuilder().add("error", ex.getMessage()).build().toString();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorJsonString);
		}
	}
	
	@GetMapping(path = "/bundles", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAllBundles() {
		try {
			List<ArchiveUpload> auList = aSvc.getBundles();
			JsonArray ja = aSvc.bundlesToJson(auList);
			return ResponseEntity.status(HttpStatus.OK).body(ja.toString());
		} catch (Exception ex) {
			String errorJsonString = Json.createObjectBuilder().add("error", ex.getMessage()).build().toString();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorJsonString);
		}
	}
}

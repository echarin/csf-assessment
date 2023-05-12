package ibf2022.batch2.csf.backend.controllers;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ibf2022.batch2.csf.backend.services.ArchiveService;
import ibf2022.batch2.csf.backend.services.ImageService;

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
		try {
			imgSvc.upload();
		} catch (FileUploadException ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body("File created successfully");
	}

	// TODO: Task 5
	

	// TODO: Task 6

}

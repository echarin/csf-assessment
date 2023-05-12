package ibf2022.batch2.csf.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping
public class UploadController {

	// TODO: Task 2, Task 3, Task 4
	/* 
	 * ngOnInit(): void {
    this.uploadForm = this.fb.group({
      name: this.fb.control('', [ Validators.required ]),
      title: this.fb.control('', [ Validators.required ]),
      comments: this.fb.control(''),
      archive: this.fb.control('', [ Validators.required ]),
    });
  }
	*/
	@PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> postUpload(
		@RequestPart MultipartFile archive, @RequestPart String name, @RequestPart String title, @RequestPart String comments
	) {
		return ResponseEntity.status(HttpStatus.CREATED).body("File created successfully");
	}

	// TODO: Task 5
	

	// TODO: Task 6

}

package ibf2022.batch2.csf.backend.repositories;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.amazonaws.services.s3.AmazonS3;

public class ImageRepository {

	@Autowired private AmazonS3 s3Client;

    @Value("${do.storage.bucketname}")
    private String bucketName;

	//TODO: Task 3
	// You are free to change the parameter and the return type
	// Do not change the method's name
	public Object upload(/* any number of parameters here */) throws IOException {
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

		return null;
	}
}

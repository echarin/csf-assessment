package ibf2022.batch2.csf.backend.services;

import java.io.IOException;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf2022.batch2.csf.backend.repositories.ImageRepository;

@Service
public class ImageService {

    @Autowired private ImageRepository imgRepo;

    public Object upload() throws FileUploadException {
        try {
            return this.imgRepo.upload();
        } catch (IOException ex) {
            throw new FileUploadException("File failed to upload", ex);
        }
    }
}

package ibf2022.batch2.csf.backend.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf2022.batch2.csf.backend.models.ArchiveDownload;
import ibf2022.batch2.csf.backend.models.S3Upload;
import ibf2022.batch2.csf.backend.repositories.ImageRepository;
import ibf2022.batch2.csf.exceptions.FileUploadException;

@Service
public class ImageService {

    @Autowired private ImageRepository imgRepo;

    public S3Upload upload(ArchiveDownload fu) throws FileUploadException {
        try {
            return this.imgRepo.upload(fu);
        } catch (IOException ex) {
            throw new FileUploadException("File failed to upload", ex);
        } catch (FileUploadException ex) {
            throw ex;
        }
    }
}

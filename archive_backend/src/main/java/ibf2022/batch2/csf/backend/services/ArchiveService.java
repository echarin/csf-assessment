package ibf2022.batch2.csf.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf2022.batch2.csf.backend.models.ArchiveDownload;
import ibf2022.batch2.csf.backend.models.ArchiveUpload;
import ibf2022.batch2.csf.backend.models.S3Upload;
import ibf2022.batch2.csf.backend.repositories.ArchiveRepository;
import ibf2022.batch2.csf.exceptions.ArchiveUploadException;
import ibf2022.batch2.csf.exceptions.BundleNotFoundException;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

@Service
public class ArchiveService {
    
    @Autowired private ArchiveRepository aRepo;

    public String recordBundle(ArchiveDownload ad, S3Upload s3u) throws ArchiveUploadException {
        ArchiveUpload au = new ArchiveUpload();
        au.setBundleId(s3u.getBundleId());
        au.setDate(s3u.getDate());
        au.setName(ad.getName());
        au.setTitle(ad.getTitle());
        if (ad.getComments() != null)
            au.setComments(ad.getComments());
        au.setUrls(s3u.getUrls());
        
        ArchiveUpload auUploaded; 

        try {
            auUploaded = this.aRepo.recordBundle(au);
            JsonObject jo = Json.createObjectBuilder().add("bundleId", auUploaded.getBundleId()).build();
            return jo.toString();
        } catch (Exception ex) {
            throw new ArchiveUploadException("Archive failed to upload.", ex);
        }
    }

    public ArchiveUpload getBundleByBundleId(String bundleId) throws BundleNotFoundException {
        try {
            ArchiveUpload au = aRepo.getBundleByBundleId(bundleId);
            if (au == null)
                throw new BundleNotFoundException("Bundle not found.");
            return au;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public List<ArchiveUpload> getBundles() {
        try {
            return aRepo.getBundles();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public JsonArray bundlesToJson(List<ArchiveUpload> bundles) {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        bundles.forEach(bundle -> jab.add(bundle.toJson()));
        return jab.build();
    } 
}

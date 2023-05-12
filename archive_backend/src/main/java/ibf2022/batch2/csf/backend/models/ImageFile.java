package ibf2022.batch2.csf.backend.models;

import java.io.File;
import java.util.Date;

import org.springframework.util.DigestUtils;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ImageFile {
    private String contentType;
    private long imageSize;
    private String fileName;
    private File file;
    private String md5Hash;

    public void generateMd5Hash() {
        String toHash = String.valueOf(this.fileName + this.contentType + new Date().toString());
        this.md5Hash = DigestUtils.md5DigestAsHex(toHash.getBytes());
    }
}

package ibf2022.batch2.csf.backend.models;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArchiveDownload {
    private String name;
    private String title;
    private String comments;
    private MultipartFile archive;

    public static ArchiveDownload create(String name, String title, String comments, MultipartFile archive) {
        ArchiveDownload fu = new ArchiveDownload();
        if (name != null) {
            fu.setName(name);
        }
        if (title != null) {
            fu.setTitle(title);
        }
        if (comments != null) {
            fu.setComments(comments);
        }
        if (archive != null) {
            fu.setArchive(archive);
        }
        return fu;
    }
}

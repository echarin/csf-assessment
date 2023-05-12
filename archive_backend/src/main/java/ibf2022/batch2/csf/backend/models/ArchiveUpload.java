package ibf2022.batch2.csf.backend.models;

import java.util.Date;

import jakarta.json.JsonArray;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArchiveUpload {
    public String bundleId;
    public Date date;
    public String name;
    public String title;
    public String comments;
    public JsonArray urls;
}

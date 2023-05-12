package ibf2022.batch2.csf.backend.models;

import java.util.Date;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class S3Upload {
    private String bundleId;
    private Date date;
    private JsonArray urls = Json.createArrayBuilder().build();

    public void appendUrl(String url) {
        this.urls.add(Json.createValue(url));
    }
}

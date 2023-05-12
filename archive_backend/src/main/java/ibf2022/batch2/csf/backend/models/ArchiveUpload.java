package ibf2022.batch2.csf.backend.models;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
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
    public List<String> urls;

    public JsonObject toJson() {
        Instant dateInstant = this.date.toInstant();
        String dateISO = dateInstant.toString();

        JsonArrayBuilder jab = Json.createArrayBuilder();
        urls.forEach(url -> jab.add(url));

        JsonObjectBuilder job = Json.createObjectBuilder()
                                .add("bundleId", bundleId)
                                .add("date", dateISO)
                                .add("name", name)
                                .add("title", title)
                                .add("comments", comments)
                                .add("urls", jab.build());
        
        return job.build();
    }
}

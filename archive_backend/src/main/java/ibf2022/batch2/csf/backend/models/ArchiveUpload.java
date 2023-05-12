package ibf2022.batch2.csf.backend.models;

import java.time.Instant;
import java.util.Date;

import jakarta.json.Json;
import jakarta.json.JsonArray;
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
    public JsonArray urls;

    public JsonObject toJson() {
        Instant dateInstant = this.date.toInstant();
        String dateISO = dateInstant.toString();
        JsonObjectBuilder job = Json.createObjectBuilder()
                                .add("bundleId", bundleId)
                                .add("date", dateISO)
                                .add("name", name)
                                .add("title", title)
                                .add("comments", comments)
                                .add("urls", urls);
        return job.build();
    }
}

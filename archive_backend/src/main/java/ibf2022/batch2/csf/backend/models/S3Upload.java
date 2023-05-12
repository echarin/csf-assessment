package ibf2022.batch2.csf.backend.models;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class S3Upload {
    private String bundleId;
    private Date date;
    private List<String> urls = new LinkedList<>();

    public void appendUrl(String url) {
        this.urls.add(url);
    }
}

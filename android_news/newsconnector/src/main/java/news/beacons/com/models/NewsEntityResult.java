package news.beacons.com.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsEntityResult {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("copyright")
    @Expose
    private String copyright;

    @SerializedName("section")
    @Expose
    private String section;

    @SerializedName("last_updated")
    @Expose
    private String lastUpdated;

    @SerializedName("num_results")
    @Expose
    private Integer numResults;

    @SerializedName("results")
    @Expose
    private List<NewsEntity> newsEntityData = null;

    public String getStatus() {
        return status;
    }

    public String getCopyright() {
        return copyright;
    }

    public String getSection() {
        return section;
    }

    public String getLastUpdatedNewsResult() {
        return lastUpdated;
    }

    public Integer getNumberOfNews() {
        return numResults;
    }

    public List<NewsEntity> getNews() {
        return newsEntityData;
    }
}

package news.agoda.com.newsconnector.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class News {

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
    @SerializedName("num_NewsDatas")
    @Expose
    private Integer numNewsDatas;
    @SerializedName("NewsDatas")
    @Expose
    private List<NewsData> NewsDatas = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Integer getNumNewsDatas() {
        return numNewsDatas;
    }

    public void setNumNewsDatas(Integer numNewsDatas) {
        this.numNewsDatas = numNewsDatas;
    }

    public List<NewsData> getNewsDatas() {
        return NewsDatas;
    }

    public void setNewsDatas(List<NewsData> NewsDatas) {
        this.NewsDatas = NewsDatas;
    }
}

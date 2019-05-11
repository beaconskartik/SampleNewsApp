package news.agoda.com.newsconnector.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

abstract public class NewsEntity {

    @SerializedName("section")
    @Expose
    private String section;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("abstract")
    @Expose
    private String _abstract;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("byline")
    @Expose
    private String byline;

    @SerializedName("published_date")
    @Expose
    private String publishedDate;

    public String getSection() {
        return section;
    }

    public String getTitle() {
        return title;
    }

    public String getAbstract() {
        return _abstract;
    }

    public String getUrl() {
        return url;
    }

    public String getByline() {
        return byline;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    abstract public List<MediaEntity> getMediaEntity();

    abstract public boolean isMediaEntityPresent();
}


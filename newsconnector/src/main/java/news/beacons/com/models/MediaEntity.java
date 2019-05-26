package news.beacons.com.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MediaEntity {

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("format")
    @Expose
    private String format;

    @SerializedName("height")
    @Expose
    private Integer height;

    @SerializedName("width")
    @Expose
    private Integer width;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("subtype")
    @Expose
    private String subtype;

    @SerializedName("caption")
    @Expose
    private String caption;

    @SerializedName("copyright")
    @Expose
    private String copyright;

    public String getUrl() {
        return url;
    }

    public String getFormat() {
        return format;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWidth() {
        return width;
    }

    public String getType() {
        return type;
    }

    public String getSubtype() {
        return subtype;
    }

    public String getCaption() {
        return caption;
    }

    public String getCopyright() {
        return copyright;
    }
}

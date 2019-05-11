package news.agoda.com.newsconnector.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsDataMediaArray extends NewsData {

    @SerializedName("multimedia")
    @Expose
    private List<MediaData> multimedia = null;

    public List<MediaData> getMultimedia() {
        return multimedia;
    }
}

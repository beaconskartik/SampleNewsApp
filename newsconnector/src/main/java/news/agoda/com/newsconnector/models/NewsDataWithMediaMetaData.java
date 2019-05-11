package news.agoda.com.newsconnector.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

class NewsDataWithMediaMetaData extends News {

    @SerializedName("multimedia")
    @Expose
    private List<MediaMetaData> multimedia = null;

    @Override
    public List<MediaMetaData> getMediaMetaData() {
        return multimedia;
    }

    @Override
    public boolean isMediaMetaDataPresent() {
        return multimedia != null && multimedia.size() > 1;
    }
}

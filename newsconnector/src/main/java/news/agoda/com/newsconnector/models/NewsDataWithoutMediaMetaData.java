package news.agoda.com.newsconnector.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

class NewsDataWithoutMediaMetaData extends News {

    @SerializedName("multimedia")
    @Expose
    private String multimedia = "";

    @Override
    public List<MediaMetaData> getMediaMetaData() {
        return new ArrayList<>();
    }

    @Override
    public boolean isMediaMetaDataPresent() {
        return false;
    }
}

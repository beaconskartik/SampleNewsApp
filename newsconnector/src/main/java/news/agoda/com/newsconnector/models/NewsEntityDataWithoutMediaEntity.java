package news.agoda.com.newsconnector.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

class NewsEntityDataWithoutMediaEntity extends NewsEntity {

    @SerializedName("multimedia")
    @Expose
    private String multimedia = "";

    @Override
    public List<MediaEntity> getMediaEntity() {
        return new ArrayList<>();
    }

    @Override
    public boolean isMediaEntityPresent() {
        return false;
    }
}

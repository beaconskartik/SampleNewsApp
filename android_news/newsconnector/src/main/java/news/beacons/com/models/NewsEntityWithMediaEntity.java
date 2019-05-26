package news.beacons.com.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

class NewsEntityWithMediaEntity extends NewsEntity {

    @SerializedName("multimedia")
    @Expose
    private List<MediaEntity> multimedia = null;

    @Override
    public List<MediaEntity> getMediaEntity() {
        return multimedia;
    }

    @Override
    public boolean isMediaEntityPresent() {
        return multimedia != null && multimedia.size() > 0;
    }
}

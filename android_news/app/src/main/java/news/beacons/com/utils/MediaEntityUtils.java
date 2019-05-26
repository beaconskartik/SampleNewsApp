package news.beacons.com.utils;

import android.text.TextUtils;

import java.util.List;

import news.beacons.com.models.MediaEntity;
import news.beacons.com.Models.MediaEntityImageType;

public final class MediaEntityUtils {

    public static String getImageUrlFromMediaEntity(List<MediaEntity> mediaEntityList,
                                                     MediaEntityImageType type) {
        if (type == MediaEntityImageType.thumbNailImage) {
            for (MediaEntity mediaEntity : mediaEntityList) {
                if (!TextUtils.isEmpty(mediaEntity.getUrl())) {
                    return mediaEntity.getUrl();
                }
            }
        } else {
            for (int i = mediaEntityList.size() - 1; i >= 0; i--) {
                if (!TextUtils.isEmpty(mediaEntityList.get(i).getUrl())) {
                    return mediaEntityList.get(i).getUrl();
                }
            }
        }
        return " ";
    }
}

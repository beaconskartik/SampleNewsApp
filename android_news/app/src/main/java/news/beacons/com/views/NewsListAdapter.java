package news.beacons.com.views;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.imagepipeline.request.ImageRequest;

import java.util.List;

import news.beacons.com.R;
import news.beacons.com.models.NewsEntity;
import news.beacons.com.Models.MediaEntityImageType;
import news.beacons.com.utils.MediaEntityUtils;

public class NewsListAdapter extends ArrayAdapter {

    private static class ViewHolder {
        TextView newsTitle;
        DraweeView imageView;
    }

    public NewsListAdapter(Context context, int resource, List<NewsEntity> newsEntityList) {
        super(context, resource, newsEntityList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewsEntity newsEntity = (NewsEntity) getItem(position);
        String thumbnailURL = "";

        if (newsEntity != null && newsEntity.isMediaEntityPresent()) {
            thumbnailURL = MediaEntityUtils.getImageUrlFromMediaEntity(newsEntity.getMediaEntity(),
                    MediaEntityImageType.thumbNailImage);
        }

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_news, parent, false);
            viewHolder.newsTitle = (TextView) convertView.findViewById(R.id.news_title);
            viewHolder.imageView = (DraweeView) convertView.findViewById(R.id.news_item_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.newsTitle.setText(newsEntity.getTitle());
        DraweeController draweeController = Fresco.newDraweeControllerBuilder().setImageRequest(ImageRequest.fromUri
                (Uri.parse(thumbnailURL))).setOldController(viewHolder.imageView.getController()).build();
        viewHolder.imageView.setController(draweeController);
        return convertView;
    }
}

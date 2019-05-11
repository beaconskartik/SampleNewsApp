package news.agoda.com.sample.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.imagepipeline.request.ImageRequest;

import news.agoda.com.sample.R;

public class DetailViewFragment extends Fragment {

    private String storyURL = "";

    public static DetailViewFragment getNewInstance(String storyURL, String title, String summary, String imageUrl) {
        DetailViewFragment detailViewFragment = new DetailViewFragment();

        Bundle bundle = new Bundle();
        bundle.putString("storyURL", storyURL);
        bundle.putString("title", title);
        bundle.putString("summary", summary);
        bundle.putString("imageURL", imageUrl);

        detailViewFragment.setArguments(bundle);
        return detailViewFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle extras = getArguments();
        storyURL = extras.getString("storyURL");
        String title = extras.getString("title");
        String summary = extras.getString("summary");
        String imageURL = extras.getString("imageURL");


        TextView titleView = (TextView) getView().findViewById(R.id.title);
        DraweeView imageView = (DraweeView) getView().findViewById(R.id.news_image);
        TextView summaryView = (TextView) getView().findViewById(R.id.summary_content);
        Button onFullStoryClicked = (Button) getView().findViewById(R.id.full_story_link);

        titleView.setText(title);
        summaryView.setText(summary);

        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(ImageRequest.fromUri(Uri.parse(imageURL)))
                .setOldController(imageView.getController()).build();
        imageView.setController(draweeController);

        onFullStoryClicked.setOnClickListener(this::onFullStoryClicked);
    }

    public void onFullStoryClicked(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(storyURL));
        startActivity(intent);
    }
}

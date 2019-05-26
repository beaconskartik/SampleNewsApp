package news.beacons.com.views;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;

import io.reactivex.disposables.CompositeDisposable;
import news.beacons.com.R;
import news.beacons.com.databinding.FragmentDetailBinding;
import news.beacons.com.viewModels.VmDetailedNews;
import news.beacons.com.viewModels.VmLocator;

public class DetailViewFragment extends Fragment {

    private final static String TAG = "DetailViewFragment: ";

    private FragmentDetailBinding fragmentDetailBinding;
    private VmDetailedNews vmDetailedNews;
    private CompositeDisposable compositeDisposable;

    public static DetailViewFragment getNewInstance() {
        return new DetailViewFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        compositeDisposable = new CompositeDisposable();
        fragmentDetailBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_detail, container, false);
        vmDetailedNews = VmLocator.getInstance().getVmDetailedNews();
        fragmentDetailBinding.setVm(vmDetailedNews);
        return fragmentDetailBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupImageUrlListener();
        setupStoryUrlListener();
    }

    private void setupImageUrlListener() {
        compositeDisposable.add(vmDetailedNews
                .getImageUrl()
                .doOnNext(imageUrl -> {
                    SimpleDraweeView imageView = fragmentDetailBinding.newsImage;
                    DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                            .setImageRequest(ImageRequest.fromUri(Uri.parse(imageUrl)))
                            .setOldController(imageView.getController()).build();
                    imageView.setController(draweeController);
                })
                .subscribe(imageUrl -> {
                    Log.d(TAG, "setupImageUrlListener:onNext " + imageUrl);
                }, throwable -> {
                    Log.e(TAG, "setupImageUrlListener:onError " , throwable);
                }, () -> {
                    Log.d(TAG, "setupImageUrlListener:onComplete ");
                }));
    }

    private void setupStoryUrlListener() {
        compositeDisposable.add(vmDetailedNews.getStoryUrl()
                .doOnNext(storyUrl -> {
                    fragmentDetailBinding.fullStoryLink.setOnClickListener(view -> onFullStoryClicked(storyUrl));
                })
                .subscribe(storyUrl -> {
                    Log.d(TAG, "setupStoryUrlListener:onNext " + storyUrl);
                }, throwable -> {
                    Log.e(TAG, "setupStoryUrlListener:onError " , throwable);
                }, () -> {
                    Log.d(TAG, "setupStoryUrlListener:onComplete ");
                }));
    }

    private void onFullStoryClicked(String storyURL) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(storyURL));
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.clear();
        Log.d(TAG, "onDestroyView: Called");
    }
}

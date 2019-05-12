package news.agoda.com.sample.viewModels;

import android.databinding.ObservableField;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import news.agoda.com.newsconnector.models.NewsEntity;
import news.agoda.com.sample.Models.MediaEntityImageType;
import news.agoda.com.sample.utils.MediaEntityUtils;

public class VmDetailedNews {

    private final ObservableField<String> title;
    private final ObservableField<String> summary;

    private final BehaviorSubject<String> storyUrlObservable;
    private final BehaviorSubject<String> imageUrlObservable;

    VmDetailedNews() {
        title = new ObservableField<>();
        summary = new ObservableField<>();
        storyUrlObservable = BehaviorSubject.createDefault(" ");
        imageUrlObservable = BehaviorSubject.createDefault(" ");
    }

    public Observable<String> getImageUrl() {
        return imageUrlObservable;
    }

    public Observable<String> getStoryUrl() {
        return storyUrlObservable;
    }

    public ObservableField<String> getTitle() {
        return title;
    }

    public ObservableField<String> getSummary() {
        return summary;
    }

    public void updateNewsEntity(NewsEntity newsEntity) {
        title.set(newsEntity.getTitle());
        storyUrlObservable.onNext(newsEntity.getUrl());
        summary.set(newsEntity.getAbstract());

        if (newsEntity.isMediaEntityPresent()) {
            String url = MediaEntityUtils.getImageUrlFromMediaEntity(newsEntity.getMediaEntity(),
                    MediaEntityImageType.largeImage);
            imageUrlObservable.onNext(url);
        } else {
            imageUrlObservable.onNext(" ");
        }
    }
}

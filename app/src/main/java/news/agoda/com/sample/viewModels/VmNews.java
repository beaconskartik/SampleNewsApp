package news.agoda.com.sample.viewModels;

import android.util.Log;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import news.agoda.com.newsconnector.NewsConnector;
import news.agoda.com.newsconnector.NewsConnectorBuilder;
import news.agoda.com.newsconnector.models.NewsEntity;
import news.agoda.com.newsconnector.models.NewsEntityResult;
import okhttp3.OkHttpClient;

public class VmNews {

    private final static String TAG = "VmNews: ";

    private final NewsConnector connector;
    private final CompositeDisposable compositeDisposable;
    private final BehaviorSubject<List<NewsEntity>> newsEntityListObservable;

    VmNews() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        connector = NewsConnectorBuilder.create(okHttpClient);
        compositeDisposable = new CompositeDisposable();
        newsEntityListObservable = BehaviorSubject.create();
    }

    private void fetchNews() {
        compositeDisposable.add(connector
                .fetchLatestNews()
                .subscribeOn(Schedulers.io())
                .map(NewsEntityResult::getNews)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(newsEntityListObservable::onNext)
                .subscribe(newsEntityList -> {
                    Log.d(TAG, "fetchNews:onNext " + newsEntityList.toArray());
                }, throwable -> {
                    Log.e(TAG, "fetchNews:onError " , throwable);
                }, () -> {
                    Log.d(TAG, "fetchNews:onComplete ");
                }));
    }

    public Observable<List<NewsEntity>> getNewsEntityList () {
         if (!newsEntityListObservable.hasValue()) {
                fetchNews();
         }
        return newsEntityListObservable;
    }

    public void release() {
        compositeDisposable.clear();
        Log.d(TAG, "release: Called");
    }
}

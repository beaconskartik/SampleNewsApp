package news.agoda.com.sample.viewModels;

import android.util.Log;

import java.util.List;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
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
    private final NetworkChangeProvider networkChangeProvider;
    private final CompositeDisposable fetchNewsCompositeDisposable;
    private final CompositeDisposable compositeDisposable;
    private final BehaviorSubject<List<NewsEntity>> newsEntityListObservable;
    private final Scheduler scheduler;

    VmNews(NetworkChangeProvider changeProvider) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        connector = NewsConnectorBuilder.create(okHttpClient);
        networkChangeProvider = changeProvider;
        scheduler = Schedulers.from(Executors.newSingleThreadExecutor());
        fetchNewsCompositeDisposable = new CompositeDisposable();
        compositeDisposable = new CompositeDisposable();
        newsEntityListObservable = BehaviorSubject.create();
    }

    private void fetchNews() {
        fetchNewsCompositeDisposable.add(Observable
                .fromCallable(newsEntityListObservable::hasValue)
                .filter(val -> !val)
                .flatMap(val -> connector.fetchLatestNews())
                .subscribeOn(scheduler)
                .map(NewsEntityResult::getNews)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(newsEntityListObservable::onNext)
                .subscribe(newsEntityList -> {
                    Log.d(TAG, "fetchNews:onNext " + newsEntityList.toArray());
                }, throwable -> {
                    Log.e(TAG, "fetchNews:onError ", throwable);
                }, () -> {
                    Log.d(TAG, "fetchNews:onComplete ");
                }));
    }

    private void setupNetworkChange() {
        compositeDisposable.add(networkChangeProvider
                .isNetworkConnected()
                .observeOn(scheduler)
                .filter(Boolean::booleanValue)
                .filter(val -> !newsEntityListObservable.hasValue())
                .map(val -> {
                    fetchNewsCompositeDisposable.clear();
                    fetchNews();
                    return val;
                })
                .subscribe(val -> {
                    Log.d(TAG, "setupNetworkChange:onNext: " + val);
                }, throwable -> {
                    Log.e(TAG, "setupNetworkChange:onError ", throwable);
                }, () -> {
                    Log.d(TAG, "setupNetworkChange:onComplete ");
                }));
    }

    public Observable<List<NewsEntity>> getNewsEntityList () {
        if (!newsEntityListObservable.hasValue()) {
            fetchNews();
            setupNetworkChange();
        }
        return newsEntityListObservable;
    }

    public void release() {
        fetchNewsCompositeDisposable.clear();
        compositeDisposable.clear();
        networkChangeProvider.release();
        Log.d(TAG, "release: Called");
    }
}

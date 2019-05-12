package news.agoda.com.sample.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import news.agoda.com.newsconnector.models.NewsEntity;
import news.agoda.com.sample.R;
import news.agoda.com.sample.viewModels.NetworkChangeProvider;
import news.agoda.com.sample.viewModels.VmLocator;
import news.agoda.com.sample.viewModels.VmNews;

public class NewsListFragment extends ListFragment {

    private final static String TAG = "NewsListFragment: ";

    private List<NewsEntity> newsEntityItemList;
    private CompositeDisposable compositeDisposable;
    private IListClickListener clickListener;
    private VmNews vmNews;

    public static NewsListFragment getNewInstance() {
        return new NewsListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsEntityItemList = new ArrayList<>();
        compositeDisposable = new CompositeDisposable();
        NetworkChangeProvider networkChangeProvider = new NetworkChangeProvider(getContext());
        vmNews = VmLocator.getInstance().getVmNews(networkChangeProvider);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadNews();
        setupClickListener();
    }

    private void loadNews() {
        compositeDisposable.add(vmNews.getNewsEntityList()
                .doOnNext(newsEntityItemList::addAll)
                .subscribe(newsEntityItemList -> {
                    NewsListAdapter adapter = new NewsListAdapter(getContext(),
                            R.layout.list_item_news, newsEntityItemList);
                    setListAdapter(adapter);
                    Log.d(TAG, "fetchNews:onNext " + newsEntityItemList.size());
                }, throwable -> {
                    Log.e(TAG, "fetchNews:onError " , throwable);
                }, () -> {
                    Log.d(TAG, "fetchNews:onComplete ");
                }));
    }

    public void setupListClickListener(IListClickListener listClickListener) {
        clickListener = listClickListener;
    }

    private void setupClickListener() {
        ListView listView = getListView();
        listView.setOnItemClickListener( (AdapterView<?> parent, View view, int position, long id)  -> {
            if (clickListener != null) {
                NewsEntity newsEntity = newsEntityItemList.get(position);
                clickListener.onItemClicked(newsEntity);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        vmNews.release();
        compositeDisposable.clear();
        Log.d(TAG, "onDestroyView: Called");
    }
}

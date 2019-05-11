package news.agoda.com.sample.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import news.agoda.com.newsconnector.NewsConnector;
import news.agoda.com.newsconnector.NewsConnectorBuilder;
import news.agoda.com.newsconnector.models.NewsEntity;
import news.agoda.com.newsconnector.models.NewsEntityResult;
import news.agoda.com.sample.NewsListAdapter;
import news.agoda.com.sample.R;
import okhttp3.OkHttpClient;

public class NewsListFragment extends ListFragment {

    private List<NewsEntity> newsEntityItemList;
    private CompositeDisposable compositeDisposable;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsEntityItemList = new ArrayList<>();
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadResource();
    }

    private void loadResource() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        NewsConnector connector = NewsConnectorBuilder.create(okHttpClient);

        compositeDisposable.add(connector.fetchLatestNews()
                .subscribeOn(Schedulers.io())
                .map(NewsEntityResult::getNews)
                .doOnNext(newsEntityItemList::addAll)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    NewsListAdapter adapter = new NewsListAdapter(getContext(), R.layout.list_item_news, newsEntityItemList);
                    setListAdapter(adapter);

                    ListView listView = getListView();
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            NewsEntity newsEntity = newsEntityItemList.get(position);
                            String title = newsEntity.getTitle();
                            String newsUrl = newsEntity.getUrl();
                            String summary = newsEntity.getAbstract();
                            String imageUrl = "";
                            if (newsEntity.isMediaEntityPresent()) {
                                imageUrl = newsEntity.getMediaEntity().get(3).getUrl();
                            }
                            DetailViewFragment detailViewFragment = DetailViewFragment
                                    .getNewInstance(newsUrl, title, summary, imageUrl);
                            FragmentTransaction fragmentTransaction = getActivity()
                                    .getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.add(R.id.phone_container, detailViewFragment).addToBackStack("detail_fragment");;
                            fragmentTransaction.commit();
                        }
                    });
                }, throwable -> {

                }, () -> {

                }));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.clear();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

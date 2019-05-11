package news.agoda.com.sample;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import news.agoda.com.newsconnector.NewsDataConnector;
import news.agoda.com.newsconnector.NewsDataConnectorBuilder;
import news.agoda.com.newsconnector.models.News;
import news.agoda.com.newsconnector.models.NewsData;
import news.agoda.com.newsconnector.models.NewsDataMediaArray;
import okhttp3.OkHttpClient;

public class MainActivity
        extends ListActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private List<NewsData> newsItemList;
    private Handler handler = new Handler(Looper.getMainLooper());
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fresco.initialize(this);

        newsItemList = new ArrayList<>();

        loadResource();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadResource() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        NewsDataConnector connector = NewsDataConnectorBuilder.create(okHttpClient);

            compositeDisposable.add(connector.fetchLatestNews()
                .subscribeOn(Schedulers.io())
                .map(News::getNewsData)
                .doOnNext(newsItemList::addAll)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    NewsListAdapter adapter = new NewsListAdapter(MainActivity.this, R.layout.list_item_news, newsItemList);
                    setListAdapter(adapter);

                    ListView listView = getListView();
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            NewsData newsEntity = newsItemList.get(position);
                            String title = newsEntity.getTitle();
                            Intent intent = new Intent(MainActivity.this, DetailViewActivity.class);
                            intent.putExtra("title", title);
                            intent.putExtra("storyURL", newsEntity.getUrl());
                            intent.putExtra("summary", newsEntity.getAbstract());
                            String url = "";
                            if (newsEntity instanceof NewsDataMediaArray) {
                                url = ((NewsDataMediaArray)newsEntity).getMultimedia().get(0).getUrl();
                            }
                            intent.putExtra("imageURL", url);
                            startActivity(intent);
                        }
                    });
                }, throwable -> {

                }, () -> {

                }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private static String readStream(InputStream in) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in));) {

            String nextLine = "";
            while ((nextLine = reader.readLine()) != null) {
                sb.append(nextLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}

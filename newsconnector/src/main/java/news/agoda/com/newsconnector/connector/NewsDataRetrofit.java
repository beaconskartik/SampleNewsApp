package news.agoda.com.newsconnector.connector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import news.agoda.com.newsconnector.NewsDataConnector;
import news.agoda.com.newsconnector.models.NewsDeserializer;
import news.agoda.com.newsconnector.models.NewsResult;
import news.agoda.com.newsconnector.models.News;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsDataRetrofit implements NewsDataConnector {

    private final RetrofitApi apiService;

    public NewsDataRetrofit(OkHttpClient client) {
        apiService = getApiService(client);
    }

    @Override
    public Observable<NewsResult> fetchLatestNews() {
        return apiService.fetchLatestNews();
    }

    private RetrofitApi getApiService(OkHttpClient client) {

        RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory
                .createWithScheduler(Schedulers.io());

        Gson customDeserializer = new GsonBuilder()
                .setLenient()
                .registerTypeAdapter(News.class, new NewsDeserializer())
                .create();

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitApi.SERVICE_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(customDeserializer))
                .addCallAdapterFactory(rxAdapter)
                .client(client)
                .build();
        return retrofit.create(RetrofitApi.class);
    }
}

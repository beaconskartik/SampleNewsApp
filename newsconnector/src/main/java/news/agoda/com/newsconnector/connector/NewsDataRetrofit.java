package news.agoda.com.newsconnector.connector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import news.agoda.com.newsconnector.NewsDataConnector;
import news.agoda.com.newsconnector.models.News;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsDataRetrofit implements NewsDataConnector {

    private final RetrofitApi apiService;

    public NewsDataRetrofit(OkHttpClient client) {
        apiService = getApiService(client);
    }

    @Override
    public Observable<News> fetchLatestNews() {
        return apiService.fetchLatestNews();
    }

    private RetrofitApi getApiService(OkHttpClient client) {

        RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory
                .createWithScheduler(Schedulers.io());

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitApi.SERVICE_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxAdapter)
                .client(client)
                .build();
        return retrofit.create(RetrofitApi.class);
    }
}

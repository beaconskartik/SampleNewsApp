package news.beacons.com.connector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import news.beacons.com.NewsConnector;
import news.beacons.com.models.NewsEntityDeserializer;
import news.beacons.com.models.NewsEntity;
import news.beacons.com.models.NewsEntityResult;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsRetrofit implements NewsConnector {

    private final RetrofitApi apiService;

    public NewsRetrofit(OkHttpClient client) {
        apiService = getApiService(client);
    }

    @Override
    public Observable<NewsEntityResult> fetchLatestNews() {
        return apiService.fetchLatestNews();
    }

    private RetrofitApi getApiService(OkHttpClient client) {

        RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory
                .createWithScheduler(Schedulers.io());

        Gson customDeserializer = new GsonBuilder()
                .setLenient()
                .registerTypeAdapter(NewsEntity.class, new NewsEntityDeserializer())
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

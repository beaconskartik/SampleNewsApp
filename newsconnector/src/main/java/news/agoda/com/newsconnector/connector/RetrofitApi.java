package news.agoda.com.newsconnector.connector;

import io.reactivex.Observable;
import news.agoda.com.newsconnector.models.News;
import retrofit2.http.GET;

public interface RetrofitApi {


    String SERVICE_ENDPOINT = "https://api.myjson.com/";

    @GET("bins/nl6jh")
    Observable<News> fetchLatestNews();
}

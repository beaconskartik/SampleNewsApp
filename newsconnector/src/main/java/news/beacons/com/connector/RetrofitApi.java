package news.beacons.com.connector;

import io.reactivex.Observable;
import news.beacons.com.models.NewsEntityResult;
import retrofit2.http.GET;

public interface RetrofitApi {


    String SERVICE_ENDPOINT = "https://api.myjson.com/";

    @GET("bins/nl6jh")
    Observable<NewsEntityResult> fetchLatestNews();
}

package news.beacons.com;

import io.reactivex.Observable;
import news.beacons.com.models.NewsEntityResult;

public interface NewsConnector {

    Observable<NewsEntityResult> fetchLatestNews();
}

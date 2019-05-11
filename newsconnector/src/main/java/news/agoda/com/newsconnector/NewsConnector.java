package news.agoda.com.newsconnector;

import io.reactivex.Observable;
import news.agoda.com.newsconnector.models.NewsEntityResult;

public interface NewsConnector {

    Observable<NewsEntityResult> fetchLatestNews();
}

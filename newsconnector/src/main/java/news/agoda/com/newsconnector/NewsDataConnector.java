package news.agoda.com.newsconnector;

import io.reactivex.Observable;
import news.agoda.com.newsconnector.models.NewsResult;

public interface NewsDataConnector {

    Observable<NewsResult> fetchLatestNews();
}

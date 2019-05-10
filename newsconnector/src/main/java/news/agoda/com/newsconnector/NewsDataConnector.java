package news.agoda.com.newsconnector;

import io.reactivex.Observable;
import news.agoda.com.newsconnector.models.News;

public interface NewsDataConnector {

    Observable<News> fetchLatestNews();
}

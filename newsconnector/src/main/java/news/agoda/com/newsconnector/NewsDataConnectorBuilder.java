package news.agoda.com.newsconnector;

import news.agoda.com.newsconnector.connector.NewsDataRetrofit;
import okhttp3.OkHttpClient;

public class NewsDataConnectorBuilder {

    public static NewsDataConnector create(OkHttpClient client) {
        return new NewsDataRetrofit(client);
    }
}

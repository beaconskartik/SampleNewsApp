package news.agoda.com.newsconnector;

import news.agoda.com.newsconnector.connector.NewsRetrofit;
import okhttp3.OkHttpClient;

public class NewsConnectorBuilder {

    public static NewsConnector create(OkHttpClient client) {
        return new NewsRetrofit(client);
    }
}

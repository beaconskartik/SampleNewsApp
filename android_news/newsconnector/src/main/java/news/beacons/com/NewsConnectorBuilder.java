package news.beacons.com;

import news.beacons.com.connector.NewsRetrofit;
import okhttp3.OkHttpClient;

public class NewsConnectorBuilder {

    public static NewsConnector create(OkHttpClient client) {
        return new NewsRetrofit(client);
    }
}

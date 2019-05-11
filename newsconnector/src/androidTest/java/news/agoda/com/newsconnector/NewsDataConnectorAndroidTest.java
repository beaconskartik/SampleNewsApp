package news.agoda.com.newsconnector;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import news.agoda.com.newsconnector.models.NewsResult;
import okhttp3.OkHttpClient;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class NewsDataConnectorAndroidTest {

    private final String TAG = "NewsDataConnectorAndroidTest:";
    private NewsDataConnector client;

    @Before
    public void setUp() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        client = NewsDataConnectorBuilder.create(okHttpClient);
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("news.agoda.com.newsconnector.test", appContext.getPackageName());
    }

    @Test
    public void fetchLatestNews() {
        TestObserver<NewsResult> testObserver = client
                .fetchLatestNews()
                .subscribeOn(Schedulers.io())
                .test();

        testObserver.awaitTerminalEvent();

        List<NewsResult> datas = testObserver.values();

        testObserver
                .assertNoErrors()
                .assertSubscribed()
                .assertValue(val -> TextUtils.equals(val.getStatus(), "OK"))
                .assertValue(val -> TextUtils.equals(val.getCopyright(), "Copyright (c) 2015 The New York Times Company. All Rights Reserved."))
                .assertValue(val -> val.getNews() != null)
                .assertValue(val -> val.getNews().size() > 0)
                .assertValue(val -> val.getNews().size() == 24)
                .assertValue(val -> TextUtils.equals(val.getNews().get(0).getTitle(), "Work Policies May Be Kinder, but Brutal Competition Isn’t"))
                .assertValue(val -> val.getNews().get(0).getMediaMetaData().get(0).getUrl().equals("http://static01.nyt.com/images/2015/08/18/business/18EMPLOY/18EMPLOY-thumbStandard.jpg"))
                .assertValue(val -> val.getNews().get(0).getMediaMetaData().get(0).getHeight() == 75)
                .assertValue(val -> val.getNews().get(0).getMediaMetaData().get(0).getWidth() == 75);

        testObserver.dispose();
    }
}

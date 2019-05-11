package news.agoda.com.newsconnector;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;
import news.agoda.com.newsconnector.models.News;
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
    private TestScheduler testScheduler;

    @Before
    public void setUp() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        client = NewsDataConnectorBuilder.create(okHttpClient);
        testScheduler = new TestScheduler();
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("news.agoda.com.newsconnector.test", appContext.getPackageName());
    }

    @Test
    public void fetchLatestNews() {
        TestObserver<News> testObserver = client
                .fetchLatestNews()
                .subscribeOn(testScheduler)
                .test();

        testObserver.awaitTerminalEvent();

        testObserver
                .assertNoErrors()
                .assertSubscribed()
                .assertValue(val -> TextUtils.equals(val.getStatus(), "OK"))
                .assertValue(val -> TextUtils.equals(val.getCopyright(), "Copyright (c) 2015 The New York Times Company. All Rights Reserved."))
                .assertValue(val -> val.getResults() != null)
                .assertValue(val -> val.getResults().size() > 0)
                .assertValue(val -> val.getResults().size() == 24)
                .assertValue(val -> TextUtils.equals(val.getResults().get(0).getTitle(), "Work Policies May Be Kinder, but Brutal Competition Isn’t"));

        testObserver.dispose();
    }
}

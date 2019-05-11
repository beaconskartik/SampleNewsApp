package news.agoda.com;

import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import news.agoda.com.newsconnector.connector.RetrofitApi;
import news.agoda.com.newsconnector.models.News;

@RunWith(MockitoJUnitRunner.class)
public class NewsDataRetrofitUnitTest {

    private final String TAG = "NewsDataRetrofitUnitTest";

    private final String STATUS = "OK";
    private final String COPYRIGHT = "The New York Times Company";
    private final String SECTION = "technology";
    private final int NO_OF_RESULT = 24;
    private final String URL = "http://www.nytimes.com/2015/08/18/business/work-policies-may-be-kinder-but-brutal-competition-isnt.html";
    private final String TITLE = "Work Policies May Be Kinder";
    private final String BY_LINE = "By NOAM SCHEIBER";

    @Mock
    private RetrofitApi mockApi;

    @Before
    public void setUp() {
        mockApi = Mockito.mock(RetrofitApi.class);
    }

    private News createNewsResponse() {
        String jsonString = "{\"status\": \"OK\",\"copyright\": \"The New York Times Company\",\"section\": \"technology\",\"last_updated\": \"2015-08-18T10:15:06-05:00\",\"num_results\": 24,\"results\":[{\"section\": \"Business Day\",\"subsection\": \"tech\",\"title\": \"Work Policies May Be Kinder\",\"abstract\": \"Top-tier employers\",\"url\": \"http://www.nytimes.com/2015/08/18/business/work-policies-may-be-kinder-but-brutal-competition-isnt.html\",\"byline\": \"By NOAM SCHEIBER\",\"item_type\": \"Article\"}]} ";
        return new GsonBuilder().create().fromJson(jsonString, News.class);
    }

    @Test
    public void fetchLatestNewsUnitTest() {

        News data = createNewsResponse();
        Mockito.when(mockApi
                .fetchLatestNews())
                .thenReturn(Observable.just(data));

        TestObserver<News> testObserver = mockApi
                .fetchLatestNews()
                .test();

        testObserver
                .assertSubscribed()
                .assertNoErrors()
                .assertValue(val -> val.getCopyright().equals(COPYRIGHT))
                .assertValue(val -> val.getSection().equals(SECTION))
                .assertValue(val -> val.getStatus().equals(STATUS))
                .assertValue(val -> val.getNumResults() == NO_OF_RESULT)
                .assertValue(val -> val.getResults().size() > 0)
                .assertValue(val -> val.getResults().get(0).getTitle().equals(TITLE))
                .assertValue(val -> val.getResults().get(0).getUrl().equals(URL))
                .assertValue(val -> val.getResults().get(0).getByline().equals(BY_LINE));

        testObserver.dispose();
    }
}

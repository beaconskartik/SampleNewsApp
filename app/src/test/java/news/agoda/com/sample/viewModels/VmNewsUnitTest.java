package news.agoda.com.sample.viewModels;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.FieldSetter;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.TestObserver;
import io.reactivex.subjects.BehaviorSubject;
import news.agoda.com.newsconnector.models.MediaEntity;
import news.agoda.com.newsconnector.models.NewsEntity;

@RunWith(MockitoJUnitRunner.class)
public class VmNewsUnitTest extends TestCase {

    private VmNews vmNews;
    private List<NewsEntity> newsEntityList;
    private BehaviorSubject<List<NewsEntity>> newsEntityListObservable;

    @Before
    public void setup() throws NoSuchFieldException {

        vmNews = VmLocator.getInstance().getVmNews();
        newsEntityListObservable = BehaviorSubject.create();
        FieldSetter.setField(vmNews, vmNews.getClass().getDeclaredField("newsEntityListObservable"),
                newsEntityListObservable);

        NewsEntity newsEntity = Mockito.mock(NewsEntity.class);

        MediaEntity mediaEntity = Mockito.mock(MediaEntity.class);
        List<MediaEntity> mediaEntityList = Collections.singletonList(mediaEntity);

        Mockito.when(newsEntity.getAbstract()).thenReturn("summary");
        Mockito.when(newsEntity.getUrl()).thenReturn("storyUrl");
        Mockito.when(newsEntity.getTitle()).thenReturn("title");
        Mockito.when(newsEntity.isMediaEntityPresent()).thenReturn(true);
        Mockito.when(newsEntity.getMediaEntity()).thenReturn(mediaEntityList);

        Mockito.when(mediaEntity.getUrl()).thenReturn("thumbnailURL1");
        newsEntityList = Collections.singletonList(newsEntity);

        newsEntityListObservable.onNext(newsEntityList);
    }

    @Test
    public void testGetNewsEntityList() throws NoSuchFieldException, InterruptedException {
        TestObserver<List<NewsEntity>> testObserver =
                vmNews.getNewsEntityList()
                        .test()
                        .assertSubscribed()
                        .assertNoErrors();
        testObserver.assertValue(val -> val.size() == 1);
        testObserver.assertValue(val -> val.get(0).getTitle().equals("title"));
        testObserver.assertValue(val -> val.get(0).getUrl().equals("storyUrl"));
        testObserver.assertValue(val -> val.get(0).getAbstract().equals("summary"));
        testObserver.assertValue(val -> val.get(0).isMediaEntityPresent());
        testObserver.assertValue(val -> val.get(0).getMediaEntity().get(0).getUrl().equals("thumbnailURL1"));

        testObserver.dispose();
    }

    @Test
    public void testRelease() throws NoSuchFieldException {
        CompositeDisposable compositeDisposable = Mockito.mock(CompositeDisposable.class);
        FieldSetter.setField(vmNews, vmNews.getClass().getDeclaredField("compositeDisposable"), compositeDisposable);
        vmNews.release();
        Mockito.verify(compositeDisposable).clear();
    }
}

package news.agoda.com.sample.viewModels;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import news.agoda.com.newsconnector.models.MediaEntity;
import news.agoda.com.newsconnector.models.NewsEntity;

@RunWith(MockitoJUnitRunner.class)
public class VmDetailedNewsUnitTest extends TestCase {

    private VmDetailedNews vmDetailedNews;
    @Mock private NewsEntity newsEntity;

    @Before
    public void setup() {
        vmDetailedNews = VmLocator.getInstance().getVmDetailedNews();
        newsEntity = Mockito.mock(NewsEntity.class);

        MediaEntity mediaEntity = Mockito.mock(MediaEntity.class);
        List<MediaEntity> mediaEntityList = Collections.singletonList(mediaEntity);

        Mockito.when(newsEntity.getAbstract()).thenReturn("summary");
        Mockito.when(newsEntity.getUrl()).thenReturn("storyUrl");
        Mockito.when(newsEntity.getTitle()).thenReturn("title");
        Mockito.when(newsEntity.isMediaEntityPresent()).thenReturn(true);
        Mockito.when(newsEntity.getMediaEntity()).thenReturn(mediaEntityList);

        Mockito.when(mediaEntity.getUrl()).thenReturn("thumbnailURL1");
    }

    @Test
    public void testGetImageUrl() {
        vmDetailedNews.updateNewsEntity(newsEntity);
        vmDetailedNews.getImageUrl()
                .test()
                .assertValue(val -> val.equals("thumbnailURL1"))
                .dispose();
    }

    @Test
    public void testGetStoryUrl() {
        vmDetailedNews.updateNewsEntity(newsEntity);
        vmDetailedNews.getStoryUrl()
                .test()
                .assertValue(val -> val.equals("storyUrl"))
                .dispose();
    }

    @Test
    public void testGetTitle() {
        vmDetailedNews.updateNewsEntity(newsEntity);
        assertEquals(vmDetailedNews.getTitle().get(), "title");
    }

    @Test
    public void testGetSummary() {
        vmDetailedNews.updateNewsEntity(newsEntity);
        assertEquals(vmDetailedNews.getSummary().get(), "summary");
    }

    @Test
    public void testUpdateNewsEntity() {

        vmDetailedNews.updateNewsEntity(newsEntity);

        assertEquals(vmDetailedNews.getSummary().get(), "summary");
        assertEquals(vmDetailedNews.getTitle().get(), "title");
    }

}

package news.beacons.com;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import news.beacons.com.models.NewsEntity;
import news.beacons.com.models.NewsEntityDeserializer;

@RunWith(JUnit4.class)
public class NewsEntityWithMediaEntityUnitTest extends TestCase {

    private NewsEntity newsEntity;

    @Before
    public void setup() {
        String newsEntityString = "{\"section\": \"Business Day\",\"subsection\": \"DealBook\",\"title\": \"Why Amazon May Take a Page From Walmart’s Labor Playbook\",\"abstract\": \"The perception.\",\"url\": \"http://www.nytimes.com/\",\"byline\": \"By ROB COX\",\"item_type\": \"Article\",\"published_date\": \"2015-08-18T04:00:00-5:00\",\"multimedia\": [{\"url\": \"http://static01.nyt.com/images/2015/08/13/technology/personaltech/13askk-web/13askk-web-thumbStandard.jpg\",\"format\": \"Standard Thumbnail\",\"height\": 75,\"width\": 75,\"type\": \"image\",\"subtype\": \"photo\",\"caption\": \"Microsoft's Safety & Security Center has information on how to protect your computer and personal information.\",\"copyright\": \"\" }]}";

        Gson customDeserializer = new GsonBuilder()
                .setLenient()
                .registerTypeAdapter(NewsEntity.class, new NewsEntityDeserializer())
                .create();
        newsEntity = customDeserializer.fromJson(newsEntityString, NewsEntity.class);
    }

    @Test
    public void testGetSection() {
        String section = "Business Day";
        assertEquals(section, newsEntity.getSection());
    }

    @Test
    public void testGetTitle() {
        String title = "Why Amazon May Take a Page From Walmart’s Labor Playbook";
        assertEquals(title, newsEntity.getTitle());
    }

    @Test
    public void testGetAbstract() {
        String abstracted = "The perception.";
        assertEquals(abstracted, newsEntity.getAbstract());
    }

    @Test
    public void testGetUrl() {
        String url = "http://www.nytimes.com/";
        assertEquals(url, newsEntity.getUrl());
    }

    @Test
    public void testGetByline() {
        String byline = "By ROB COX";
        assertEquals(byline, newsEntity.getByline());
    }

    @Test
    public void testGetPublishedDate() {
        String publishedDate = "2015-08-18T04:00:00-5:00";
        assertEquals(publishedDate, newsEntity.getPublishedDate());
    }

    @Test
    public void testIsMediaEntityPresent() {
        boolean x = newsEntity.isMediaEntityPresent();
        assertTrue(x);
    }

    @Test
    public void testGetMediaEntity() {
        assertEquals(1, newsEntity.getMediaEntity().size());
    }
}

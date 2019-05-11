package news.agoda.com;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

import news.agoda.com.newsconnector.models.NewsEntity;
import news.agoda.com.newsconnector.models.NewsEntityDeserializer;

@RunWith(JUnit4.class)
public class NewsEntityWithoutMediaEntityUnitTest extends TestCase {

    private NewsEntity newsEntity;

    @Before
    public void setup() {
        String newsEntityString = "{\"section\": \"Business Day\",\"subsection\": \"DealBook\",\"title\": \"Why Amazon May Take a Page From Walmart’s Labor Playbook\",\"abstract\": \"The perception.\",\"url\": \"http://www.nytimes.com/\",\"byline\": \"By ROB COX\",\"item_type\": \"Article\",\"published_date\": \"2015-08-18T04:00:00-5:00\",\"multimedia\": \"\"}";

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
        assertFalse(newsEntity.isMediaEntityPresent());
    }

    @Test
    public void testGetMediaEntity() {
        assertEquals(new ArrayList<>(), newsEntity.getMediaEntity());
    }
}

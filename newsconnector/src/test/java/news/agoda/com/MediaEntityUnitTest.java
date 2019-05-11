package news.agoda.com;

import com.google.gson.Gson;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import news.agoda.com.newsconnector.models.MediaEntity;

@RunWith(JUnit4.class)
public class MediaEntityUnitTest extends TestCase {

    private MediaEntity mediaEntity;

    @Before
    public void setup() {
        String multiMediaString = "{\"url\": \"http://static01.nyt.com/images/2015/08/13/technology/personaltech/13askk-web/13askk-web-thumbStandard.jpg\",\"format\": \"Standard Thumbnail\",\"height\": 75,\"width\": 75,\"type\": \"image\",\"subtype\": \"photo\",\"caption\": \"Microsoft's Safety & Security Center has information on how to protect your computer and personal information.\",\"copyright\": \"\" }";
        mediaEntity = new Gson().fromJson(multiMediaString, MediaEntity.class);
    }

    @Test
    public void testGetUrl() {
        String url = "http://static01.nyt.com/images/2015/08/13/technology/personaltech/13askk-web/13askk-web-thumbStandard.jpg";
        assertEquals(url, mediaEntity.getUrl());
    }

    @Test
    public void testGetFormat() {
        String format = "Standard Thumbnail";
        assertEquals(format, mediaEntity.getFormat());
    }

    @Test
    public void testGetHeight() {
        Integer height = 75;
        assertEquals(height, mediaEntity.getHeight());
    }

    @Test
    public void testGetWidth() {
        Integer width = 75;
        assertEquals(width, mediaEntity.getWidth());
    }

    @Test
    public void testGetType() {
        String type = "image";
        assertEquals(type, mediaEntity.getType());
    }

    @Test
    public void testGetSubtype() {
        String subtype = "photo";
        assertEquals(subtype, mediaEntity.getSubtype());
    }

    @Test
    public void testGetCaption() {
        String caption = "Microsoft's Safety & Security Center has information on how to protect your computer and personal information.";
        assertEquals(caption, mediaEntity.getCaption());
    }

    @Test
    public void testGetCopyright() {
        String copyright = "";
        assertEquals(copyright, mediaEntity.getCopyright());
    }
}

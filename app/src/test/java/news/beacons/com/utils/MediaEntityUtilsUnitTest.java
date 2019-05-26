package news.beacons.com.utils;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import news.beacons.com.models.MediaEntity;
import news.beacons.com.Models.MediaEntityImageType;

@RunWith(MockitoJUnitRunner.Silent.class)
public class MediaEntityUtilsUnitTest extends TestCase {

    @Test
    public void testGetImageUrlFromMediaEntity() {

        MediaEntity mediaEntity1 = Mockito.mock(MediaEntity.class);
        MediaEntity mediaEntity2 = Mockito.mock(MediaEntity.class);
        MediaEntity mediaEntity3 = Mockito.mock(MediaEntity.class);
        MediaEntity mediaEntity4 = Mockito.mock(MediaEntity.class);

        Mockito.when(mediaEntity1.getUrl()).thenReturn("thumbnailURL1");
        Mockito.when(mediaEntity2.getUrl()).thenReturn("thumbnailURL2");
        Mockito.when(mediaEntity3.getUrl()).thenReturn("thumbnailURL3");
        Mockito.when(mediaEntity4.getUrl()).thenReturn("thumbnailURL4");

        List<MediaEntity> mediaEntityList = Arrays.asList(mediaEntity1, mediaEntity2, mediaEntity3, mediaEntity4);
        String url = MediaEntityUtils.getImageUrlFromMediaEntity(mediaEntityList, MediaEntityImageType.thumbNailImage);
        assertEquals(url, "thumbnailURL1");

        url = MediaEntityUtils.getImageUrlFromMediaEntity(mediaEntityList, MediaEntityImageType.largeImage);
        assertEquals(url, "thumbnailURL4");
    }
}

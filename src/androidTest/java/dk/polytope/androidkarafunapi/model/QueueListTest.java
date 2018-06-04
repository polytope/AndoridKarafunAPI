package dk.polytope.androidkarafunapi.model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import android.support.test.runner.AndroidJUnit4;
import dk.polytope.androidkarafunapi.InvalidAttributeException;
import dk.polytope.androidkarafunapi.InvalidTagException;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@RunWith(AndroidJUnit4.class)
public class QueueListTest {

  @Test
  public void accept()
      throws IOException, XmlPullParserException, InvalidTagException, InvalidAttributeException {
    String input = " <queue>\n" +
        "<item id=\"1\" status=\"ready\">\n" +
        "<title>{song_name}</title>\n" +
        "<artist>{artist_name}</artist>\n" +
        "<year>2018</year>\n" +
        "<duration>3600</duration>\n" +
        "<singer>singer</singer>\n" +
        "</item>\n" +
        "<item id=\"1\" status=\"ready\">\n" +
        "<title>{song_name}</title>\n" +
        "<artist>{artist_name}</artist>\n" +
        "<year>2018</year>\n" +
        "<duration>3600</duration>\n" +
        "<singer>singer</singer>\n" +
        "</item>\n" +
        "</queue>";
    XmlPullParser parser = SongTest.parserFromString(input);
    QueueList queue = new QueueList().accept(parser);

    Assert.assertEquals(2, queue.size());
  }

  @Test
  public void acceptThrowsIfInvalidTag()
      throws IOException, XmlPullParserException, InvalidTagException, InvalidAttributeException {
    String input = " <invalid>\n" +
        "<item id=\"1\" status=\"ready\">\n" +
        "<title>{song_name}</title>\n" +
        "<artist>{artist_name}</artist>\n" +
        "<year>2018</year>\n" +
        "<duration>3600</duration>\n" +
        "<singer>singer</singer>\n" +
        "</item>\n" +
        "<item id=\"1\" status=\"ready\">\n" +
        "<title>{song_name}</title>\n" +
        "<artist>{artist_name}</artist>\n" +
        "<year>2018</year>\n" +
        "<duration>3600</duration>\n" +
        "<singer>singer</singer>\n" +
        "</item>\n" +
        "</invalid>";
    XmlPullParser parser = SongTest.parserFromString(input);
    try {
      new QueueList().accept(parser);
      fail();
    } catch (InvalidTagException e) {
      assertTrue(true);

    }
  }
}
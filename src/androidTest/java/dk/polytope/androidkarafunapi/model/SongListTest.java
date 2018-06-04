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
public class SongListTest {

  @Test
  public void accept()
      throws IOException, XmlPullParserException, InvalidTagException, InvalidAttributeException {
    String input = "<list total=\"1\">\n" +
        "<item id=\"1\">\n" +
        "<title>{song_name}</title>\n" +
        "<artist>{artist_name}</artist>\n" +
        "<year>2018</year>\n" +
        "<duration>1</duration>\n" +
        "</item>\n" +
        "</list>";

    XmlPullParser parser = SongTest.parserFromString(input);
    SongList queue = new SongList().accept(parser);

    Assert.assertEquals(1, queue.size());
  }

  @Test
  public void acceptThrowsIfInvalidTag()
      throws IOException, XmlPullParserException, InvalidTagException, InvalidAttributeException {
    String input = "<invalid total=\"1\">\n" +
        "<item id=\"1\">\n" +
        "<title>{song_name}</title>\n" +
        "<artist>{artist_name}</artist>\n" +
        "<year>2018</year>\n" +
        "<duration>1</duration>\n" +
        "</item>\n" +
        "</invalid>";

    XmlPullParser parser = SongTest.parserFromString(input);

    try {
      new SongList().accept(parser);
      fail();
    } catch (InvalidTagException e) {
      assertTrue(true);

    }
  }

  @Test
  public void acceptThrowsIfInvalidAttribute()
      throws IOException, XmlPullParserException, InvalidTagException, InvalidAttributeException {
    String input = "<list total=\"1\" invalid=\"invalid\">\n" +
        "<item id=\"1\">\n" +
        "<title>{song_name}</title>\n" +
        "<artist>{artist_name}</artist>\n" +
        "<year>2018</year>\n" +
        "<duration>1</duration>\n" +
        "</item>\n" +
        "</list>";

    XmlPullParser parser = SongTest.parserFromString(input);

    try {
      new SongList().accept(parser);
      fail();
    } catch (InvalidAttributeException e) {
      assertTrue(true);

    }
  }
}
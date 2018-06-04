package dk.polytope.androidkarafunapi.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import android.support.test.runner.AndroidJUnit4;
import dk.polytope.androidkarafunapi.InvalidAttributeException;
import dk.polytope.androidkarafunapi.InvalidTagException;
import dk.polytope.androidkarafunapi.KarafunXMLHelpers;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;


@RunWith(AndroidJUnit4.class)
public class SongTest {

  public static XmlPullParser parserFromString(String input)
      throws XmlPullParserException, IOException {
    InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
    return KarafunXMLHelpers.createParser(stream);
  }

  @Test
  public void accept()
      throws IOException, XmlPullParserException, InvalidTagException, InvalidAttributeException {
    String status = "ready";
    String songName = "Song";
    String artist = "Artist";
    int year = 2018;
    int duration = 3600;
    String singer = "Test Singer";

    String input = "<item id=\"1\" status=\"" + status + "\">\n" +
        "<title>" + songName + "</title>\n" +
        "<artist>" + artist + "</artist>\n" +
        "<year>" + year + "</year>\n" +
        "<duration>" + duration + "</duration>\n" +
        "<singer>" + singer + "</singer>\n" +
        "</item>";

    XmlPullParser parser = parserFromString(input);
    Song song = new Song().accept(parser);

    assertEquals(status, song.getStatus().name());
    assertEquals(songName, song.getTitle());
    assertEquals(artist, song.getArtist());
    assertEquals(year, song.getYear());
    assertEquals(duration, song.getDuration());
    assertEquals(singer, song.getSinger());
  }

  @Test
  public void acceptThrowsWithInvalidTag()
      throws IOException, XmlPullParserException, InvalidTagException, InvalidAttributeException {
    String input = "<invalidTag id=\"1\" status=\"ready\">\n" +
        "<title>songname</title>\n" +
        "<artist>artist</artist>\n" +
        "<year>year</year>\n" +
        "<duration>duration</duration>\n" +
        "<singer>singer</singer>\n" +
        "</invalidTag>";

    XmlPullParser parser = parserFromString(input);

    try {
      new Song().accept(parser);
      fail();
    } catch (InvalidTagException e) {
      assertTrue(true);

    }
  }

  @Test
  public void acceptThrowsWithInvalidAttribute()
      throws IOException, XmlPullParserException, InvalidTagException, InvalidAttributeException {
    String input = "<item id=\"1\" invalidAttribute=\"value\"  status=\"ready\">\n" +
        "<title>songname</title>\n" +
        "<artist>artist</artist>\n" +
        "<year>2016</year>\n" +
        "<duration>3600</duration>\n" +
        "<singer>singer</singer>\n" +
        "</item>";

    XmlPullParser parser = parserFromString(input);

    try {
      new Song().accept(parser);
      fail();
    } catch (InvalidAttributeException e) {
      assertTrue(true);

    }
  }

  @Test
  public void acceptThrowsWithUnknownChildren()
      throws IOException, XmlPullParserException, InvalidTagException, InvalidAttributeException {
    String input = "<item id=\"1\" status=\"ready\">\n" +
        "<title>songname</title>\n" +
        "<artist>artist</artist>\n" +
        "<year>2016</year>\n" +
        "<duration>3600</duration>\n" +
        "<singer>singer</singer>\n" +
        "<unknown>unknown</unknown>\n" +
        "</item>";

    XmlPullParser parser = parserFromString(input);

    try {
      new Song().accept(parser);
      fail();
    } catch (InvalidTagException e) {
      assertTrue(true);

    }
  }
}
package dk.polytope.androidkarafunapi.model;

import static dk.polytope.androidkarafunapi.model.SongTest.parserFromString;
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
public class StatusTest {

  @Test
  public void accept()
      throws IOException, XmlPullParserException, InvalidTagException, InvalidAttributeException {
    String state = "idle";
    int position = 3600;
    int pitch = 60;
    int tempo = 61;

    String input = "<status state=\"" + state + "\">\n" +
        "<position>" + position + "</position>\n" +
        "<volumeList>\n" +
        "</volumeList>\n" +
        "<pitch>" + pitch + "</pitch>\n" +
        "<tempo>" + tempo + "</tempo>\n" +
        "<queue>\n" +
        "</queue>\n" +
        "</status>";

    XmlPullParser parser = parserFromString(input);
    Status status = new Status().accept(parser);

    Assert.assertEquals(state, status.getState().name());
    Assert.assertEquals(position, status.getPosition());
    Assert.assertEquals(0, status.getVolumeList().size());
    Assert.assertEquals(pitch, status.getPitch());
    Assert.assertEquals(tempo, status.getTempo());
    Assert.assertEquals(0, status.getQueue().size());
  }

  @Test
  public void acceptThrowsIfInvalidTag()
      throws IOException, XmlPullParserException, InvalidTagException, InvalidAttributeException {
    String input = "<invalid state=\"idle\">\n" +
        "<position>1</position>\n" +
        "<volumeList>\n" +
        "</volumeList>\n" +
        "<pitch>1</pitch>\n" +
        "<tempo>1</tempo>\n" +
        "<queue>\n" +
        "</queue>\n" +
        "</invalid>";

    XmlPullParser parser = parserFromString(input);
    try {
      Status status = new Status().accept(parser);
      fail();
    } catch (InvalidTagException e) {
      assertTrue(true);

    }
  }

  @Test
  public void acceptThrowsIfInvalidAttribute()
      throws IOException, XmlPullParserException, InvalidTagException, InvalidAttributeException {
    String input = "<status state=\"idle\" invalid=\"invalid\">\n" +
        "<position>1</position>\n" +
        "<volumeList>\n" +
        "</volumeList>\n" +
        "<pitch>1</pitch>\n" +
        "<tempo>1</tempo>\n" +
        "<queue>\n" +
        "</queue>\n" +
        "<invalid>\n" +
        "</invalid>\n" +
        "</status>";

    XmlPullParser parser = parserFromString(input);
    try {
      Status status = new Status().accept(parser);
      fail();
    } catch (InvalidAttributeException e) {
      assertTrue(true);

    }
  }

  @Test
  public void acceptThrowsIfInvalidChild()
      throws IOException, XmlPullParserException, InvalidTagException, InvalidAttributeException {
    String input = "<status state=\"idle\">\n" +
        "<position>1</position>\n" +
        "<volumeList>\n" +
        "</volumeList>\n" +
        "<pitch>1</pitch>\n" +
        "<tempo>1</tempo>\n" +
        "<queue>\n" +
        "</queue>\n" +
        "<invalid>\n" +
        "</invalid>\n" +
        "</status>";

    XmlPullParser parser = parserFromString(input);
    try {
      Status status = new Status().accept(parser);
      fail();
    } catch (InvalidTagException e) {
      assertTrue(true);

    }
  }
}
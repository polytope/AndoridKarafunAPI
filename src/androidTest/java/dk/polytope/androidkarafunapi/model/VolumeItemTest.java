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
public class VolumeItemTest {

  @Test
  public void accept()
      throws IOException, XmlPullParserException, InvalidTagException, InvalidAttributeException {
    String caption = "{caption}";
    int volume = 100;
    String color = "red";

    for (String tag : new String[]{"general", "bv", "lead1", "lead2"}) {
      String input = "<" + tag + " caption=\"" + caption + "\" color=\"" + color + "\">"
          + volume + "</" + tag + ">";

      XmlPullParser parser = SongTest.parserFromString(input);

      VolumeItem item = new VolumeItem().accept(parser);
      Assert.assertEquals(tag, item.getType().name());
      Assert.assertEquals(caption, item.getCaption());
      Assert.assertEquals(color, item.getColor());
      Assert.assertEquals(volume, item.getVolume());
    }
  }

  @Test
  public void acceptThrowsIfInvalidTag()
      throws IOException, XmlPullParserException, InvalidTagException, InvalidAttributeException {
    String caption = "{caption}";
    int volume = 100;
    String color = "red";

    String input = "<invalid caption=\"" + caption + "\" color=\"" + color + "\">"
        + volume + "</invalid>";

    XmlPullParser parser = SongTest.parserFromString(input);

    try {
      new VolumeItem().accept(parser);
      fail();
    } catch (InvalidTagException e) {
      assertTrue(true);
    }
  }

  @Test
  public void acceptThrowsIfInvalidAttribute()
      throws IOException, XmlPullParserException, InvalidTagException, InvalidAttributeException {
    String caption = "{caption}";
    int volume = 100;
    String color = "red";

    String input = "<bv invalid=\"" + caption + "\" color=\"" + color + "\">"
        + volume + "</bv>";

    XmlPullParser parser = SongTest.parserFromString(input);

    try {
      new VolumeItem().accept(parser);
      fail();
    } catch (InvalidAttributeException e) {
      assertTrue(true);
    }
  }
}
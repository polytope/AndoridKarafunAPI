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
public class VolumeListTest {

  @Test
  public void accept()
      throws IOException, XmlPullParserException, InvalidTagException, InvalidAttributeException {
    String input = "<volumeList>\n" +
        "<general caption=\"{caption}\">100</general>\n" +
        "<bv caption=\"{caption}\">100</bv>\n" +
        "<lead1 caption=\"{caption}\" color=\"{color}\">100</lead1>\n" +
        "<lead2 caption=\"{caption}\" color=\"{color}\">100</lead2>\n" +
        "</volumeList>";

    XmlPullParser parser = SongTest.parserFromString(input);
    VolumeList vlist = new VolumeList().accept(parser);

    Assert.assertEquals(4, vlist.size());
  }

  @Test
  public void acceptThrowsIfInvalidTag()
      throws IOException, XmlPullParserException, InvalidTagException, InvalidAttributeException {
    String input = "<invalid>\n" +
        "<general caption=\"{caption}\">100</general>\n" +
        "<bv caption=\"{caption}\">100</bv>\n" +
        "<lead1 caption=\"{caption}\" color=\"{color}\">100</lead1>\n" +
        "<lead2 caption=\"{caption}\" color=\"{color}\">100</lead2>\n" +
        "</invalid>";

    XmlPullParser parser = SongTest.parserFromString(input);

    try {
      new VolumeList().accept(parser);
      fail();
    } catch (InvalidTagException e) {
      assertTrue(true);

    }
  }
}
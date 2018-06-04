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
public class CatalogListTest {

  @Test
  public void accept()
      throws IOException, XmlPullParserException, InvalidTagException, InvalidAttributeException {
    String input = "<catalogList>\n" +
        "<catalog id=\"123\" type=\"{type}\">{caption}</catalog>\n" +
        "<catalog id=\"123\" type=\"{type}\">{caption}</catalog>\n" +
        "</catalogList>";
    XmlPullParser parser = SongTest.parserFromString(input);
    CatalogList catalogList = new CatalogList().accept(parser);

    Assert.assertEquals(2, catalogList.size());
  }

  @Test
  public void acceptThrowsIfInvalidTag()
      throws IOException, XmlPullParserException, InvalidTagException, InvalidAttributeException {
    String input = "<invalid>\n" +
        "<catalog id=\"123\" type=\"{type}\">{caption}</catalog>\n" +
        "<catalog id=\"123\" type=\"{type}\">{caption}</catalog>\n" +
        "</invalid>";

    XmlPullParser parser = SongTest.parserFromString(input);
    try {
      new CatalogList().accept(parser);
      fail();
    } catch (InvalidTagException e) {
      assertTrue(true);

    }
  }
}
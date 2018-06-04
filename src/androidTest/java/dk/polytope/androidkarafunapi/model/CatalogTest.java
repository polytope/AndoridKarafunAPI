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
public class CatalogTest {

  @Test
  public void accept()
      throws IOException, XmlPullParserException, InvalidTagException, InvalidAttributeException {
    int id = 123;
    String type = "onlineComplete";
    String caption = "caption";

    String input = "<catalog id=\"" + id + "\" type=\"" + type + "\">" + caption + "</catalog>";
    XmlPullParser parser = SongTest.parserFromString(input);

    Catalog catalog = new Catalog().accept(parser);

    Assert.assertEquals(id, catalog.getId());
    Assert.assertEquals(type, catalog.getType());
    Assert.assertEquals(caption, catalog.getCaption());
  }

  @Test
  public void acceptThrowsIfInvalidTag()
      throws IOException, XmlPullParserException, InvalidTagException, InvalidAttributeException {
    String input = "<invalid id=\"1\" type=\"onlineComplete\">Caption</invalid>";
    XmlPullParser parser = SongTest.parserFromString(input);

    try {
      new Catalog().accept(parser);
      fail();
    } catch (InvalidTagException e) {
      assertTrue(true);
    }
  }

  @Test
  public void acceptThrowsIfInvalidAttribute()
      throws IOException, XmlPullParserException, InvalidTagException, InvalidAttributeException {
    String input = "<catalog id=\"1\" type=\"onlineComplete\" invalid=\"invalid\">Caption</catalog>";
    XmlPullParser parser = SongTest.parserFromString(input);

    try {
      new Catalog().accept(parser);
      fail();
    } catch (InvalidAttributeException e) {
      assertTrue(true);
    }
  }
}
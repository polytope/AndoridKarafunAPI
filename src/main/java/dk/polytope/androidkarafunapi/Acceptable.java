package dk.polytope.androidkarafunapi;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public interface Acceptable {
    Acceptable accept(XmlPullParser parser) throws XmlPullParserException, IOException, InvalidTagException, InvalidAttributeException;
}

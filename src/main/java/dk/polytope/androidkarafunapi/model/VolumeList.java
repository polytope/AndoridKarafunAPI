package dk.polytope.androidkarafunapi.model;

import dk.polytope.androidkarafunapi.Acceptable;
import dk.polytope.androidkarafunapi.InvalidAttributeException;
import dk.polytope.androidkarafunapi.InvalidTagException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class VolumeList extends ArrayList<VolumeItem> implements Acceptable {

    @Override
    public VolumeList accept(XmlPullParser parser) throws XmlPullParserException, IOException, InvalidTagException, InvalidAttributeException {
        if (!parser.getName().equals("volumeList")) {
            throw new InvalidTagException(parser.getName());
        }

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            add(new VolumeItem().accept(parser));
        }

        return this;
    }
}

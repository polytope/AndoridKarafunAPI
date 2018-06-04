package dk.polytope.androidkarafunapi.model;

import dk.polytope.androidkarafunapi.Acceptable;
import dk.polytope.androidkarafunapi.InvalidAttributeException;
import dk.polytope.androidkarafunapi.InvalidTagException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by hamburger on 24/01/2018.
 */

public class SongList extends ArrayList<Song> implements Acceptable {
    private int total;

    public int getTotal() {
        return total;
    }

    @Override
    public SongList accept(XmlPullParser parser) throws XmlPullParserException, IOException, InvalidTagException, InvalidAttributeException {
        if (!parser.getName().equals("list")) {
            throw new InvalidTagException(parser.getName());
        }

        for (int i = 0; i < parser.getAttributeCount(); i++) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);

            if (name.equals("total")) {
                total = Integer.parseInt(value);
            } else {
                throw new InvalidAttributeException(name, value);
            }
        }

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            add(new Song().accept(parser));
        }

        return this;
    }
}

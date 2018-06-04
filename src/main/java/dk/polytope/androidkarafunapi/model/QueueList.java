package dk.polytope.androidkarafunapi.model;

import dk.polytope.androidkarafunapi.Acceptable;
import dk.polytope.androidkarafunapi.InvalidAttributeException;
import dk.polytope.androidkarafunapi.InvalidTagException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class QueueList extends ArrayList<Song> implements Acceptable  {

    @Override
    public QueueList accept(XmlPullParser parser) throws XmlPullParserException, IOException, InvalidTagException, InvalidAttributeException {
        if (!parser.getName().equals("queue")) {
            throw new InvalidTagException(parser.getName());
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

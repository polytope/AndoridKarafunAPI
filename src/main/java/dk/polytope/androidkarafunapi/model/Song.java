package dk.polytope.androidkarafunapi.model;

import dk.polytope.androidkarafunapi.Acceptable;
import dk.polytope.androidkarafunapi.InvalidAttributeException;
import dk.polytope.androidkarafunapi.InvalidTagException;
import dk.polytope.androidkarafunapi.KarafunXMLHelpers;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by hamburger on 24/01/2018.
 */

public class Song implements Acceptable {
    private int id;
    private String title;
    private String artist;
    private int year;
    private int duration; // in seconds
    private String singer;
    private ItemState status;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getYear() {
        return year;
    }

    public int getDuration() {
        return duration;
    }

    public String getSinger() {
        return singer;
    }

    public ItemState getStatus() {
        return status;
    }

    @Override
    public Song accept(XmlPullParser parser) throws XmlPullParserException, IOException, InvalidTagException, InvalidAttributeException {
        if (!parser.getName().equals("item")) {
            throw new InvalidTagException(parser.getName());
        }

        for (int i = 0; i < parser.getAttributeCount(); i++) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);

            switch (name) {
                case "id":
                    id = Integer.parseInt(value);
                    break;
                case "status":
                    status = ItemState.valueOf(value);
                    break;
                default:
                    throw new InvalidAttributeException(name, value);
            }
        }


        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            switch (name) {
                case "title":
                    title = KarafunXMLHelpers.readTag(parser, "title");
                    break;
                case "artist":
                    artist = KarafunXMLHelpers.readTag(parser, "artist");
                    break;
                case "year":
                    year = KarafunXMLHelpers.readTagAsInt(parser, "year");
                    break;
                case "duration":
                    duration = KarafunXMLHelpers.readTagAsInt(parser, "duration");
                    break;
                case "singer":
                    singer = KarafunXMLHelpers.readTag(parser, "singer");
                    break;
                default:
                    throw new InvalidTagException(parser.getName());
                    // skip(parser);
            }
        }

        return this;
    }

}

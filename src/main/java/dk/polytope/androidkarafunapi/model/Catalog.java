package dk.polytope.androidkarafunapi.model;

import dk.polytope.androidkarafunapi.Acceptable;
import dk.polytope.androidkarafunapi.InvalidAttributeException;
import dk.polytope.androidkarafunapi.InvalidTagException;
import dk.polytope.androidkarafunapi.KarafunXMLHelpers;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class Catalog implements Acceptable {
    private int id;
    private String type;
    private String caption;

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getCaption() {
        return caption;
    }

    @Override
    public Catalog accept(XmlPullParser parser) throws XmlPullParserException, IOException, InvalidTagException, InvalidAttributeException {
        if (!parser.getName().equals("catalog")) {
            throw new InvalidTagException(parser.getName());
        }

        for (int i = 0; i < parser.getAttributeCount(); i++) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);

            switch (name) {
                case "id":
                    id = Integer.valueOf(value);
                    break;
                case "type":
                    type = value;
                    break;
                default:
                    throw new InvalidAttributeException(name, value);
            }
        }

        caption = KarafunXMLHelpers.readTag(parser, parser.getName());
        return this;
    }
}

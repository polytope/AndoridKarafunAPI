package dk.polytope.androidkarafunapi.model;

import dk.polytope.androidkarafunapi.Acceptable;
import dk.polytope.androidkarafunapi.InvalidAttributeException;
import dk.polytope.androidkarafunapi.InvalidTagException;
import dk.polytope.androidkarafunapi.KarafunXMLHelpers;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class VolumeItem implements Acceptable {
    private VolumeType type;
    private String caption;
    private String color;
    private int volume;

    public VolumeType getType() {
        return type;
    }

    public String getCaption() {
        return caption;
    }

    public String getColor() {
        return color;
    }

    public int getVolume() {
        return volume;
    }

    @Override
    public VolumeItem accept(XmlPullParser parser) throws XmlPullParserException, IOException, InvalidTagException, InvalidAttributeException {
        String tn = parser.getName();

        if (!tn.equals("general") && !tn.equals("bv") && !tn.equals("lead1") && !tn.equals("lead2")) {
            throw new InvalidTagException(tn);
        }

        type = VolumeType.valueOf(tn);

        for (int i = 0; i < parser.getAttributeCount(); i++) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);

            switch (name) {
                case "caption":
                    caption = value;
                    break;
                case "color":
                    color = value;
                    break;
                default:
                    throw new InvalidAttributeException(name, value);
            }
        }

        volume = Integer.parseInt(KarafunXMLHelpers.readTag(parser, parser.getName()));
        return this;
    }
}

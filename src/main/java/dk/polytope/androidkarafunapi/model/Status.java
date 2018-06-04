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


public class Status implements Acceptable {
    private PlayerState state;
    private int position;
    private VolumeList volumeList;
    private int pitch;
    private int tempo;
    private QueueList queue;

    public PlayerState getState() {
        return state;
    }

    public int getPosition() {
        return position;
    }

    public VolumeList getVolumeList() {
        return volumeList;
    }

    public int getPitch() {
        return pitch;
    }

    public int getTempo() {
        return tempo;
    }

    public QueueList getQueue() {
        return queue;
    }

    @Override
    public Status accept(XmlPullParser parser) throws XmlPullParserException, IOException, InvalidTagException, InvalidAttributeException {
        if (!parser.getName().equals("status")) {
            throw new InvalidTagException(parser.getName());
        }

        for (int i = 0; i < parser.getAttributeCount(); i++) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);

            if(name.equals("state")){
                state = PlayerState.valueOf(value);
            } else {
                throw new InvalidAttributeException(name, value);
            }
        }

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            switch (parser.getName()) {
                case "position":
                    position = KarafunXMLHelpers.readTagAsInt(parser, parser.getName());
                    break;
                case "volumeList":
                    volumeList = new VolumeList().accept(parser);
                    break;
                case "pitch":
                    pitch = KarafunXMLHelpers.readTagAsInt(parser, parser.getName());
                    break;
                case "tempo":
                    tempo = KarafunXMLHelpers.readTagAsInt(parser, parser.getName());
                    break;
                case "queue":
                    queue = new QueueList().accept(parser);
                    break;
                default:
                    throw new InvalidTagException(parser.getName());
            }
        }

        return this;
    }
}

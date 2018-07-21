package dk.polytope.androidkarafunapi;

import dk.polytope.androidkarafunapi.model.CatalogList;
import dk.polytope.androidkarafunapi.model.SongList;
import dk.polytope.androidkarafunapi.model.Status;
import dk.polytope.androidkarafunapi.model.VolumeType;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.apache.commons.lang3.StringEscapeUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class KarafunClient {

  private Socket socket;
  private KarafunListener listener;

  public KarafunClient() {
  }

  public KarafunClient(Socket socket) {
    this.socket = socket;
    socket.setResponseCallback(new Callback<String>() {
      @Override
      public void call(String result) {
        parseMessage(result);
      }
    }, new Callback<Boolean>() {
      @Override
      public void call(Boolean result) {
        connectionHandler(result);
      }
    });
  }

  public void connect(String url) {
    socket = new WebSocket(url, new Callback<String>() {
      @Override
      public void call(String result) {
        parseMessage(result);
      }
    }, new Callback<Boolean>() {
      @Override
      public void call(Boolean result) {
        connectionHandler(result);
      }
    });
  }

  public void close() {
    if (socket != null) {
      socket.close();
    }
    socket = null;
  }

  public KarafunListener getListener() {
    return listener;
  }

  public void setListener(KarafunListener listener) {
    this.listener = listener;
  }

  public void getStatus() {
    getStatus(false);
  }

  public void getStatus(boolean noQueue) {
    throwIfNotConnected();
    String queue = noQueue ? " noqueue" : "";
    String toSend = "<action type=\"getStatus\"" + queue + "></action>";
    socket.send(toSend);
  }

  public void getCatalogList() {
    throwIfNotConnected();
    String toSend = "<action type=\"getCatalogList\"></action>";
    socket.send(toSend);
  }

  public void getList(int id, int offset, int limit) {
    throwIfNotConnected();

    String toSend = "<action " +
        "type=\"getList\" " +
        "id=\"" + id + "\" " +
        "offset=\"" + offset + "\" " +
        "limit=\"" + limit + "\"></action>";
    socket.send(toSend);
  }

  public void search(int offset, int limit, String searchString) {
    throwIfNotConnected();

    String escapedSearchString = StringEscapeUtils.escapeXml11(searchString);

    String toSend = "<action type=\"search\" " +
        "offset=\"" + offset + "\" " +
        "limit=\"" + limit + "\">" + escapedSearchString + "</action>";
    socket.send(toSend);
  }

  public void play() {
    throwIfNotConnected();
    socket.send("<action type=\"play\"></action>");
  }

  public void pause() {
    throwIfNotConnected();
    socket.send("<action type=\"pause\"></action>");
  }

  public void next() {
    throwIfNotConnected();
    socket.send("<action type=\"next\"></action>");
  }

  public void seek(int timeInSeconds) {
    throwIfNotConnected();
    socket.send("<action type=\"seek\">" + timeInSeconds + "</action>");
  }

  public void pitch(int pitch) {
    throwIfNotConnected();
    socket.send("<action type=\"pitch\">" + pitch + "</action>");
  }

  public void tempo(int tempo) {
    throwIfNotConnected();
    socket.send("<action type=\"tempo\">" + tempo + "</action>");
  }

  public void setVolume(VolumeType type, int volume) {
    throwIfNotConnected();
    if (volume < 0 || volume > 100) {
      throw new RuntimeException("Volume needs to be between 0 and 100, selected was " + volume);
    }

    String toSend = "<action type=\"setVolume\" " +
        "volume_type=\"" + type.name() + "\">" + volume + "</action>";
    socket.send(toSend);
  }

  public void clearQueue() {
    throwIfNotConnected();
    String toSend = "<action type=\"clearQueue\"></action>";
    socket.send(toSend);
  }

  public void addToQueue(int songId, String singerName, int position) {
    throwIfNotConnected();
    String escapedSinger = StringEscapeUtils.escapeXml11(singerName);
    String toSend = "<action type=\"addToQueue\" " +
        "song=\"" + songId + "\" " +
        "singer=\"" + escapedSinger + "\">" + position + "</action>";
    socket.send(toSend);
  }

  public void removeFromQueue(int queuePosition) {
    throwIfNotConnected();
    String toSend = "<action type=\"removeFromQueue\" id=\"" + queuePosition + "\"></action>";
    socket.send(toSend);
  }

  public void changeQueuePosition(int oldPosition, int newPosition) {
    throwIfNotConnected();
    String toSend = "<action type=\"changeQueuePosition\" " +
        "id=\"" + oldPosition + "\">" + newPosition + "</action>";
    socket.send(toSend);
  }

  // Helpers

  private void connectionHandler(boolean connection) {
    if (connection) {
      listener.onConnected();
    } else {
      listener.onDisconnected();
    }
  }

  private void parseMessage(String msg) {
    if (listener == null) {
      return;
    }

    InputStream stream = new ByteArrayInputStream(msg.getBytes(StandardCharsets.UTF_8));

    try {
      XmlPullParser parser = KarafunXMLHelpers.createParser(stream);

      switch (parser.getName()) {
        case "status":
          listener.onPlayerStatus(new Status().accept(parser));
          break;
        case "catalogList":
          listener.onCatalogList(new CatalogList().accept(parser));
          break;
        case "list":
          listener.onList(new SongList().accept(parser));
          break;
      }
    } catch (XmlPullParserException | IOException | InvalidAttributeException | InvalidTagException e) {
      e.printStackTrace();
    }
  }

  private void throwIfNotConnected() {
    if (socket == null || !socket.isConnected()) {
      throw new NotConnectedException();
    }
  }

}

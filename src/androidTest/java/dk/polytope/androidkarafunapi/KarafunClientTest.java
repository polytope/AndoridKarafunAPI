package dk.polytope.androidkarafunapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import android.support.test.runner.AndroidJUnit4;
import dk.polytope.androidkarafunapi.mock.MockKarafunListener;
import dk.polytope.androidkarafunapi.mock.MockWebSocket;
import dk.polytope.androidkarafunapi.model.VolumeType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class KarafunClientTest {

  private MockWebSocket socket;
  private KarafunClient client;

  @Before
  public void beforeEach() {
    socket = new MockWebSocket();
    client = new KarafunClient(socket);
  }

  @Test
  public void close() {
    client.close();
    assertTrue(socket.closeCalled);
  }

  @Test
  public void setListener() {
    MockKarafunListener listener = new MockKarafunListener();

    client.setListener(listener);
    assertEquals(listener, client.getListener());
  }

  @Test
  public void pubishesOnConnected() {
    MockKarafunListener listener = new MockKarafunListener();
    client.setListener(listener);
    socket.connect();
    assertTrue(listener.onConnectedCalled);
  }

  @Test
  public void pubishesOnDisConnected() {
    MockKarafunListener listener = new MockKarafunListener();
    client.setListener(listener);
    socket.disconnect();
    assertTrue(listener.onDisconnectedCalled);
  }

  @Test
  public void publishesOnNewStatus() {
    String input = "<status state=\"idle\">\n" +
        "<position>0</position>\n" +
        "<volumeList>\n" +
        "</volumeList>\n" +
        "<pitch>1</pitch>\n" +
        "<tempo>1</tempo>\n" +
        "<queue>\n" +
        "</queue>\n" +
        "</status>";

    MockKarafunListener listener = new MockKarafunListener();

    client.setListener(listener);
    socket.publish(input);

    assertTrue(listener.playerStatusCalled);
    assertNotNull(listener.status);
  }

  @Test
  public void publishesOnNewCatalogList() {
    String input = "<catalogList>\n" +
        "<catalog id=\"123\" type=\"{type}\">{caption}</catalog>\n" +
        "<catalog id=\"123\" type=\"{type}\">{caption}</catalog>\n" +
        "</catalogList>";

    MockKarafunListener listener = new MockKarafunListener();

    client.setListener(listener);
    socket.publish(input);
    assertTrue(listener.catalogListCalled);
    assertNotNull(listener.catalogList);
  }

  @Test
  public void publishesOnNewList() {
    String input = "<list total=\"1\">\n" +
        "<item id=\"1\">\n" +
        "<title>{song_name}</title>\n" +
        "<artist>{artist_name}</artist>\n" +
        "<year>2018</year>\n" +
        "<duration>1</duration>\n" +
        "</item>\n" +
        "</list>";

    MockKarafunListener listener = new MockKarafunListener();

    client.setListener(listener);
    socket.publish(input);
    assertTrue(listener.listCalled);
    assertNotNull(listener.list);
  }

  @Test
  public void getStatus() {
    client.getStatus();
    assertIsConnectedCalled();
    assertTrue(socket.sendCalled);

    assertEquals("<action type=\"getStatus\"></action>", socket.sendString);
  }

  @Test
  public void getStatusWithNoQueue() {
    client.getStatus(true);
    assertIsConnectedCalled();
    assertTrue(socket.sendCalled);

    assertEquals("<action type=\"getStatus\" noqueue></action>", socket.sendString);
  }

  @Test
  public void getCatalogList() {
    client.getCatalogList();
    assertIsConnectedCalled();
    assertTrue(socket.sendCalled);

    assertEquals("<action type=\"getCatalogList\"></action>", socket.sendString);
  }

  @Test
  public void getList() {
    int id = 123321, offset = 234432, limit = 345543;

    client.getList(id, offset, limit);
    assertIsConnectedCalled();
    assertTrue(socket.sendCalled);

    String expected = String.format(
        "<action type=\"getList\" id=\"%d\" offset=\"%d\" limit=\"%d\"></action>",
        id, offset, limit
    );

    assertEquals(expected, socket.sendString);
  }

  @Test
  public void search() {
    int offset = 123321, limit = 234432;
    String searchString = "SEARCHSTRING";
    client.search(offset, limit, searchString);
    assertIsConnectedCalled();
    assertTrue(socket.sendCalled);

    String expected = String.format(
        "<action type=\"search\" offset=\"%d\" limit=\"%d\">%s</action>",
        offset, limit, searchString
    );

    assertEquals(expected, socket.sendString);
  }

  @Test
  public void play() {
    client.play();
    assertIsConnectedCalled();
    assertTrue(socket.sendCalled);

    assertEquals("<action type=\"play\"></action>", socket.sendString);
  }

  @Test
  public void pause() {
    client.pause();
    assertIsConnectedCalled();
    assertTrue(socket.sendCalled);

    assertEquals("<action type=\"pause\"></action>", socket.sendString);

  }

  @Test
  public void next() {
    client.next();
    assertIsConnectedCalled();
    assertTrue(socket.sendCalled);

    assertEquals("<action type=\"next\"></action>", socket.sendString);
  }

  @Test
  public void seek() {
    int time = 123321;
    client.seek(time);
    assertIsConnectedCalled();
    assertTrue(socket.sendCalled);

    assertEquals("<action type=\"seek\">" + time + "</action>", socket.sendString);

  }

  @Test
  public void pitch() {
    int tempo = 123321;
    client.pitch(tempo);
    assertIsConnectedCalled();
    assertTrue(socket.sendCalled);

    assertEquals("<action type=\"pitch\">" + tempo + "</action>", socket.sendString);

  }

  @Test
  public void tempo() {
    int tempo = 123321;
    client.tempo(tempo);
    assertIsConnectedCalled();
    assertTrue(socket.sendCalled);

    assertEquals("<action type=\"tempo\">" + tempo + "</action>", socket.sendString);
  }

  @Test
  public void setVolume() {
    VolumeType type = VolumeType.general;
    int volume = 100;

    client.setVolume(type, volume);
    assertIsConnectedCalled();
    assertTrue(socket.sendCalled);

    String expected = String.format(
        "<action type=\"setVolume\" volume_type=\"%s\">%d</action>",
        type.toString(), volume
    );

    assertEquals(expected, socket.sendString);
  }

  @Test
  public void setVolumeThrowsIfLessThanZero() {
    VolumeType type = VolumeType.general;
    int volume = -1;

    try {
      client.setVolume(type, volume);
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }
  }

  @Test
  public void setVolumeThrowsIfGreaterThanHundred() {
    VolumeType type = VolumeType.general;
    int volume = 101;

    try {
      client.setVolume(type, volume);
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }
  }

  @Test
  public void clearQueue() {
    client.clearQueue();
    assertIsConnectedCalled();
    assertTrue(socket.sendCalled);

    assertEquals("<action type=\"clearQueue\"></action>", socket.sendString);
  }

  @Test
  public void addToQueue() {
    int id = 123231, position = 234432;
    String singer = "SINGERNAME";

    client.addToQueue(id, singer, position);
    assertIsConnectedCalled();
    assertTrue(socket.sendCalled);

    String expected = String.format(
        "<action type=\"addToQueue\" song=\"%d\" singer=\"%s\">%d</action>",
        id, singer, position
    );

    assertEquals(expected, socket.sendString);
  }

  @Test
  public void removeFromQueue() {
    int position = 123231;

    client.removeFromQueue(position);
    assertIsConnectedCalled();
    assertTrue(socket.sendCalled);

    String expected = String.format(
        "<action type=\"removeFromQueue\" id=\"%d\"></action>",
        position
    );

    assertEquals(expected, socket.sendString);
  }

  @Test
  public void changeQueuePosition() {
    int oldPos = 123321, newPos = 234432;

    client.changeQueuePosition(oldPos, newPos);
    assertIsConnectedCalled();
    assertTrue(socket.sendCalled);

    String expected = String.format(
        "<action type=\"changeQueuePosition\" id=\"%d\">%d</action>",
        oldPos, newPos
    );

    assertEquals(expected, socket.sendString);
  }

  private void assertIsConnectedCalled() {
    if (!socket.isConnectedCalled) {
      fail();
    }
  }
}
# Karafun Android Client

This library includes the complete 
[Karafun Player API](http://www.karafun.com/developers/karafun-player-api.html).

**Dependencies:**

* OKHttp: for WebSocket
* Apache-Common-Lang3: for escaping XML

## Usage: 

Create a KarafunListener by implementing  that interface:

```java
public class SimpleKarafunListener implements KarafunListener {
  @Override
  public void onPlayerStatus(Status status) {
  }
  
  @Override
  public void onCatalogList(CatalogList list) {
  }
  
  @Override
  public void onList(SongList list) {
  }
  
  @Override
  public void onConnected() {
  }
  
  @Override
  public void onDisconnected() {
  }
}
```

Then create a new client with:

```java
KarafunListener listener = new SimpleKarafunListener();
KarafunClient client = 
  new KarafunClient().connect("ws://someAddress:57570");
client.setListener(listener);
```

Now getting status shouldn't be a problem:

```java
client.getStatus();
```

Now the `onPlayerStatus` should be called with the response from the server.

## References:

Default constructor:
```java
public KarafunClient();
```

Constructor with socket injection:
```java
public KarafunClient(Socket socket);
```

Connect to server. Provide a websocket URL:
```java
public void connect(String url);
```

Close the WebSocket connection:
```java
public void close();
```

Get the current listener:
```java
public KarafunListener getListener();
```

Set the listener:
```java
public void setListener(KarafunListener listener);
```

Sends a [getStatus](http://www.karafun.com/developers/karafun-player-api.html#status) request:
```java
public void getStatus();
```

Sends a [getStatus](http://www.karafun.com/developers/karafun-player-api.html#status) request without queue, if `noQueue` is set to `true`:
```java
public void getStatus(boolean noQueue);
```

Sends a [getCatalogList](http://www.karafun.com/developers/karafun-player-api.html#getcatalog), request:
```java
public void getCatalogList();
```

Sends a [getList](http://www.karafun.com/developers/karafun-player-api.html#getcontent) request, given the ID of the list, and the offset and limit:
```java
public void getList(int id, int offset, int limit);
```

Sends a [search](http://www.karafun.com/developers/karafun-player-api.html#search) request, given a offset, limit, and a search string:
```java
public void search(int offset, int limit, String searchString);
``` 

Requests for [audio control and transport](http://www.karafun.com/developers/karafun-player-api.html#transport):
```java
public void play();
public void pause();
public void next();
public void seek(int timeInSeconds);
public void pitch(int pitch);
public void tempo(int tempo);
```

Sends a [setVolume](http://www.karafun.com/developers/karafun-player-api.html#volumes) request, given the volume type, and volume:
```java
public void setVolume(VolumeType type, int volume);
```

Requests for [song queue management](http://www.karafun.com/developers/karafun-player-api.html#queue):
```java
public void clearQueue();
public void addToQueue(int songId, String singerName, int position);
public void removeFromQueue(int queuePosition);
public void changeQueuePosition(int oldPosition, int newPosition);
```

Complete Karafun Player API documentation:
[http://www.karafun.com/developers/karafun-player-api.html](http://www.karafun.com/developers/karafun-player-api.html)
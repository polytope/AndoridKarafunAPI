package dk.polytope.androidkarafunapi;

import android.util.Log;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocketListener;

public class WebSocket extends WebSocketListener implements Socket {

  private OkHttpClient client;
  private okhttp3.WebSocket webSocket;
  private boolean isConnected;
  private Callback<String> responseCallback;
  private Callback<Boolean> connectedCallback;

  public WebSocket(String serverUrl, Callback<String> responseCallback,
      Callback<Boolean> connectedCallback) {
    client = new OkHttpClient.Builder()
        .readTimeout(3, TimeUnit.SECONDS)
        .build();
    this.responseCallback = responseCallback;
    this.connectedCallback = connectedCallback;
    webSocket = client.newWebSocket(new Request.Builder().url(serverUrl).build(), this);
  }

  @Override
  public boolean send(final String message) {
    return webSocket.send(message);
  }

  @Override
  public boolean close() {
    boolean res = webSocket.close(1000, "User disconnected");
    client = null;
    webSocket = null;
    responseCallback = null;
    connectedCallback = null;
    return res;
  }

  @Override
  public void onOpen(okhttp3.WebSocket webSocket, Response response) {
    super.onOpen(webSocket, response);
    this.isConnected = true;
    connectedCallback.call(true);
  }

  @Override
  public void onMessage(okhttp3.WebSocket webSocket, String text) {
    super.onMessage(webSocket, text);
    if(responseCallback != null) {
      responseCallback.call(text);
    }
    Log.v("WebSocket", text);
  }

  @Override
  public void onClosing(okhttp3.WebSocket webSocket, int code, String reason) {
    super.onClosing(webSocket, code, reason);
  }

  @Override
  public void onClosed(okhttp3.WebSocket webSocket, int code, String reason) {
    super.onClosed(webSocket, code, reason);
    this.isConnected = false;
    if(connectedCallback != null) {
      connectedCallback.call(false);
    }
  }

  @Override
  public void onFailure(okhttp3.WebSocket webSocket, Throwable t, Response response) {
    super.onFailure(webSocket, t, response);
    String message = response != null ? response.message() : t.getMessage();
    this.isConnected = false;
    if(connectedCallback != null) {
      connectedCallback.call(false);
    }
    Log.v("WebSocket", message);
  }

  @Override
  public void setResponseCallback(Callback<String> responseCallback,
      Callback<Boolean> connectedCallback) {
    this.responseCallback = responseCallback;
    this.connectedCallback = connectedCallback;
  }

  @Override
  public boolean isConnected() {
    return isConnected;
  }
}
package dk.polytope.androidkarafunapi.mock;

import dk.polytope.androidkarafunapi.Callback;
import dk.polytope.androidkarafunapi.Socket;

public class MockWebSocket implements Socket {

  public boolean sendCalled = false;
  public boolean closeCalled = false;
  public boolean setResponseCalled = false;
  public boolean isConnectedCalled = false;
  public String sendString;
  private Callback<String> responseCallback;
  private Callback<Boolean> connectedCallback;

  @Override
  public boolean send(String message) {
    this.sendString = message;
    this.sendCalled = true;
    return false;
  }

  @Override
  public boolean close() {
    this.closeCalled = true;
    return false;
  }

  @Override
  public void setResponseCallback(Callback<String> responseCallback,
      Callback<Boolean> connectedCallback) {
    this.responseCallback = responseCallback;
    this.connectedCallback = connectedCallback;
    this.setResponseCalled = true;
  }

  @Override
  public boolean isConnected() {
    isConnectedCalled = true;
    return true;
  }

  public void publish(String media) {
    responseCallback.call(media);
  }

  public void connect() {
    connectedCallback.call(true);
  }

  public void disconnect() {
    connectedCallback.call(false);
  }
}

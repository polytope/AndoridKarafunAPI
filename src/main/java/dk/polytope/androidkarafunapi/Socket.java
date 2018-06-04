package dk.polytope.androidkarafunapi;

public interface Socket {

  boolean send(final String message);

  boolean close();

  void setResponseCallback(Callback<String> responseCallback, Callback<Boolean> connectedCallback);

  boolean isConnected();
}

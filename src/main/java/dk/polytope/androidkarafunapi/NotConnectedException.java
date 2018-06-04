package dk.polytope.androidkarafunapi;

public class NotConnectedException extends RuntimeException {
    @Override
    public String getMessage(){
        return "Not connected to server.";
    }
}

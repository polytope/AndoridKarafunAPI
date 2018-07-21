package dk.polytope.androidkarafunapi;

import static org.junit.Assert.*;

import android.support.test.runner.AndroidJUnit4;
import java.security.InvalidParameterException;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class WebSocketTest {
  @Test(expected = InvalidParameterException.class)
  public void throwsWhenURLDoesNotStartWithWS() {
    new WebSocket("urlWhichDoesNotStartWithWS.com:57570", null, null);
  }
}
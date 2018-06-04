package dk.polytope.androidkarafunapi.mock;

import dk.polytope.androidkarafunapi.KarafunListener;
import dk.polytope.androidkarafunapi.model.CatalogList;
import dk.polytope.androidkarafunapi.model.SongList;
import dk.polytope.androidkarafunapi.model.Status;

public class MockKarafunListener implements KarafunListener {
  public boolean playerStatusCalled = false;
  public boolean catalogListCalled = false;
  public boolean listCalled = false;
  public boolean onConnectedCalled;
  public boolean onDisconnectedCalled;
  public Status status;
  public CatalogList catalogList;
  public SongList list;

  @Override
  public void onPlayerStatus(Status status) {
    playerStatusCalled = true;
    this.status = status;
  }

  @Override
  public void onCatalogList(CatalogList list) {
    catalogListCalled = true;
    catalogList = list;
  }

  @Override
  public void onList(SongList list) {
    listCalled = true;
    this.list = list;
  }

  @Override
  public void onConnected() {
    onConnectedCalled = true;
  }

  @Override
  public void onDisconnected() {
    onDisconnectedCalled = true;
  }
}

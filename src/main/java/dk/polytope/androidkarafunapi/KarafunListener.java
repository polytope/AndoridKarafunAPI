package dk.polytope.androidkarafunapi;

import dk.polytope.androidkarafunapi.model.CatalogList;
import dk.polytope.androidkarafunapi.model.SongList;
import dk.polytope.androidkarafunapi.model.Status;

public interface KarafunListener {

  void onPlayerStatus(Status status);

  void onCatalogList(CatalogList list);

  void onList(SongList list);

  void onConnected();

  void onDisconnected();
}

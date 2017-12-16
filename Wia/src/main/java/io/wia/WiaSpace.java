package io.wia;

import java.util.List;

import retrofit2.Call;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Represents a space.
 */
public class WiaSpace extends WiaResource {

  String id;

  String name;

  /**
   * @return id of this space.
   */
  public String id() {
    return id;
  }

  /**
   * @return name of this space.
   */
  public String name() {
    return name;
  }

  // /**
  //  * @return a list of locales of this space.
  //  */
  // public List<CDALocale> locales() {
  //   return locales;
  // }

  /**
   * Create a String from this object.
   * @return a String containing the id and name of this space
   */
  @Override public String toString() {
    return "WiaSpace{"
        + "id='" + id() + '\''
        + ", name='" + name + '\''
        + '}';
  }

  public static Observable<WiaSpacesResponse> list() {
    return WiaPlugins.get().wiaService().listSpaces();
  }

  // public static Observable<WiaSpace> retrieve(String id) {
  //   return WiaPlugins.get().wiaService().retrieveSpace(id);
  // }
}

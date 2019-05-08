package io.wia;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Represents a event.
 */
public class WiaEvent1 extends WiaResource {

  String id;

  String name;

  JsonElement data;

  JsonElement file;

  String timestamp;

  /**
   * @return id of this event.
   */
  public String id() {
    return id;
  }

  /**
   * @return name of this event.
   */
  public String name() {
    return name;
  }

  /**
   * @return data of this event.
   */
  public JsonElement data() {
    return data;
  }

  /**
   * @return file of this event.
   */
  public JsonElement file() {
    return file;
  }

  /**
   * @return timestamp of this event.
   */
  public String timestamp() {
    return timestamp;
  }

  // @Override
  // public WiaEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
  //         throws JsonParseException {
  //     WiaEvent bean = new Gson().fromJson(json, WiaEvent.class);
  //     JsonObject jsonObject = json.getAsJsonObject();
  //
  //     if (jsonObject.has("data")) {
  //         JsonArray array = jsonObject.getAsJsonArray("data");
  //         // if (array != null && !array.isJsonNull()) {
  //         //     List<Data> data = new Gson().fromJson(array, new TypeToken<ArrayList<Data>>() {}.getType());
  //         //     bean.realData = data;
  //         // }
  //     }
  //
  //     return bean;
  // }

  /**
   * Create a String from this object.
   * @return a String containing the id and name of this event
   */
  @Override public String toString() {
    String dataString = "";

    if (data != null) {
      dataString = data.toString();
    }

    return "WiaEvent{"
        + "id='" + id() + '\''
        + ", name='" + name + '\''
        + ", timestamp='" + timestamp() + '\''
        + ", data='" + dataString + '\''
        + '}';
  }
}

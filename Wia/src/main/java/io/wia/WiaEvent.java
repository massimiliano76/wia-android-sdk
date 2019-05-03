package io.wia;

import java.util.List;
import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonParseException;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;

/**
 * Represents a event.
 */
public class WiaEvent extends WiaResource {

  String id;

  String name;

  JsonElement data;

  JsonElement file;

  /// The timestamp milliseconds of this Event
  @SerializedName("timestamp")
  private long timestampMs;

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
  public Date timestamp() {
    return new Date(timestampMs);
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

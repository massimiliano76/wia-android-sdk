package io.wia;

import com.google.gson.JsonElement;

public class WiaCreateEventRequest {
    private String name;

    private JsonElement data;

    public WiaCreateEventRequest(String name) {
      this.name = name;
    }

    public WiaCreateEventRequest(String name, JsonElement data) {
      this.name = name;
      this.data = data;
    }
}

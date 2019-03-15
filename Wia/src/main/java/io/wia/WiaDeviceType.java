package io.wia;

import java.util.List;

public class WiaDeviceType extends WiaResource {
    String id;

    String manufacturer;

    String model;

    String slug;

    /**
     * @return id of this device type.
     */
    public String id() {
        return id;
    }

    /**
     * @return manufacturer of this device type.
     */
    public String manufacturer() {
        return manufacturer;
    }

    /**
     * @return model of this device type.
     */
    public String model() {
        return model;
    }

    /**
     * @return slug of this device type.
     */
    public String slug() {
        return slug;
    }

    /**
     * Create a String from this object.
     * @return a String containing the id and name of this device type
     */
    @Override public String toString() {
        return "WiaDeviceType{"
                + "id='" + id() + '\''
                + ", manufacturer='" + manufacturer + '\''
                + ", model='" + model + '\''
                + ", slug='" + slug + '\''
                + '}';
    }
}
package io.wia;

/**
 * Represents a product.
 */
public class WiaProduct extends WiaResource {

    String id;
    String name;
    String colour;
    String description;
    String image;
    String manufacturer;
    String model;

    /**
     * @return id of this product.
     */
    public String id() {
        return id;
    }

    /**
     * @return name of this product.
     */
    public String name() {
        return name;
    }

    /**
     * @return colour of this product.
     */
    public String colour() {
        return colour;
    }

    /**
     * @return description of this product.
     */
    public String description() {
        return description;
    }

    /**
     * @return image of this product.
     */
    public String image() {
        return image;
    }

    /**
     * @return manufacturer of this product.
     */
    public String manufacturer() {
        return manufacturer;
    }

    /**
     * @return model of this product.
     */
    public String model() {
        return model;
    }

    /**
     * Create a String from this object.
     * @return a String containing the id and name of this product
     */
    @Override public String toString() {
        return "WiaProduct{"
                + "id='" + id() + '\''
                + ", name='" + name + '\''
                + '}';
    }
}

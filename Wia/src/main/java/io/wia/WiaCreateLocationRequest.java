package io.wia;

public class WiaCreateLocationRequest {
    Double latitude;

    Double longitude;

    Double altitude;

    public WiaCreateLocationRequest(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public WiaCreateLocationRequest(Double latitude, Double longitude, Double altitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }
}

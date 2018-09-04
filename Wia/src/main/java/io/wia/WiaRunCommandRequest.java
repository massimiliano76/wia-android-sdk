package io.wia;

public class WiaRunCommandRequest {
    private String deviceId;
    private String slug;

    public WiaRunCommandRequest(String deviceId, String slug) {
        this.deviceId = deviceId;
        this.slug = slug;
    }
}

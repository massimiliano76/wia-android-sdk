package io.wia;

public class WiaRunCommandRequest {
    private String slug;
    private Device device;
    private class Device {
        private String id;
    }

    public WiaRunCommandRequest(String deviceId, String slug) {
        Device deviceObj = new Device();
        deviceObj.id = deviceId;
        this.device = deviceObj;
        this.slug = slug;
    }
}
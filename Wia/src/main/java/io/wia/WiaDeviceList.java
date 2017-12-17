package io.wia;

import java.util.List;

public class WiaDeviceList {
    private List<WiaDevice> devices;
    private Integer count;

    public List<WiaDevice> devices() {
        return devices;
    }

    public void devices(List<WiaDevice> devices) {
        this.devices = devices;
    }

    public Integer count() {
        return count;
    }

    public void count(Integer count) {
        this.count = count;
    }
}

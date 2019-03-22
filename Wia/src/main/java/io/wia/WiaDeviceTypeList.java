package io.wia;

import java.util.List;

public class WiaDeviceTypeList {
    private List<WiaDeviceType> deviceTypes;
    private Integer count;

    public List<WiaDeviceType> deviceTypes() {
        return deviceTypes;
    }

    public void deviceTypes(List<WiaDeviceType> deviceTypes) {
        this.deviceTypes = deviceTypes;
    }

    public Integer count() {
        return count;
    }

    public void count(Integer count) {
        this.count = count;
    }
}
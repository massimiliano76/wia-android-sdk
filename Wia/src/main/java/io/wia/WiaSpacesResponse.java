package io.wia;

import java.util.List;

public class WiaSpacesResponse {
    private List<WiaSpace> spaces;
    private Integer count;

    public List<WiaSpace> getSpaces() {
        return spaces;
    }

    public void setSpaces(List<WiaSpace> spaces) {
        this.spaces = spaces;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}

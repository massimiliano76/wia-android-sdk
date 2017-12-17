package io.wia;

import java.util.List;

public class WiaSpaceList {
    private List<WiaSpace> spaces;
    private Integer count;

    public List<WiaSpace> spaces() {
        return spaces;
    }

    public void spaces(List<WiaSpace> spaces) {
        this.spaces = spaces;
    }

    public Integer count() {
        return count;
    }

    public void count(Integer count) {
        this.count = count;
    }
}

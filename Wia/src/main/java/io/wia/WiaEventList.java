package io.wia;

import java.util.List;

public class WiaEventList {
    private List<WiaEvent1> events;
    private Integer count;

    public List<WiaEvent1> events() {
        return events;
    }

    public void events(List<WiaEvent1> events) {
        this.events = events;
    }

    public Integer count() {
        return count;
    }

    public void count(Integer count) {
        this.count = count;
    }
}

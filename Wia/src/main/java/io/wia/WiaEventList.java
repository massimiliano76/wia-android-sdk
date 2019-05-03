package io.wia;

import java.util.List;

public class WiaEventList {
    private List<WiaEvent> events;
    private Integer count;

    public List<WiaEvent> events() {
        return events;
    }

    public void events(List<WiaEvent> events) {
        this.events = events;
    }

    public Integer count() {
        return count;
    }

    public void count(Integer count) {
        this.count = count;
    }
}

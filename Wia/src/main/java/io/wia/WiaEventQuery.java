package io.wia;

import java.util.List;

public class WiaEventQuery {
    private List<WiaEventQueryItem> result;

    public List<WiaEventQueryItem> result() {
        return result;
    }

    public void result(List<WiaEventQueryItem> result) {
        this.result = result;
    }
}

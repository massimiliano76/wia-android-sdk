package io.wia;

import java.util.List;

public class WiaWidgetList {
    private List<WiaWidget> widgets;
    private Integer count;

    public List<WiaWidget> widgets() {
        return widgets;
    }

    public void widgets(List<WiaWidget> widgets) {
        this.widgets = widgets;
    }

    public Integer count() {
        return count;
    }

    public void count(Integer count) {
        this.count = count;
    }
}

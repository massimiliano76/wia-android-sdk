package io.wia;

import java.util.List;

public class WiaUserList {
    private List<WiaUser> users;
    private Integer count;

    public List<WiaUser> users() {
        return users;
    }

    public void users(List<WiaUser> users) {
        this.users = users;
    }

    public Integer count() {
        return count;
    }

    public void count(Integer count) {
        this.count = count;
    }
}

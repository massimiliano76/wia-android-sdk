package io.wia;

public class WiaLoginRequest {
    private String username;
    private String password;
    private String scope;
    private String grantType;

    public WiaLoginRequest(String username, String password, String scope, String grantType) {
        this.username = username;
        this.password = password;
        this.scope = scope;
        this.grantType = grantType;
    }
}

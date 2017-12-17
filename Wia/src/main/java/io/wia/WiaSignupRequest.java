package io.wia;

public class WiaSignupRequest {
    private String fullName;
    private String emailAddress;
    private String password;

    public WiaSignupRequest(String fullName, String emailAddress, String password) {
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.password = password;
    }
}

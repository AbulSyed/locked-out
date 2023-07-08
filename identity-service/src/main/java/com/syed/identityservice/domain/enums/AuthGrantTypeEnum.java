package com.syed.identityservice.domain.enums;

public enum AuthGrantTypeEnum {
    AUTHORIZATION_CODE("authorization_code"),
    REFRESH_TOKEN("refresh_token"),
    CLIENT_CREDENTIALS("client_credentials");

    private final String value;

    AuthGrantTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

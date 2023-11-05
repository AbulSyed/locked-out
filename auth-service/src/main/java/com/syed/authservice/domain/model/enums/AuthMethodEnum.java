package com.syed.authservice.domain.model.enums;

public enum AuthMethodEnum {
    CLIENT_SECRET_BASIC("client_secret_basic"),
    CLIENT_SECRET_POST("client_secret_post"),
    CLIENT_SECRET_JWT("client_secret_jwt"),
    PRIVATE_KEY_JWT("private_key_jwt");

    private final String value;

    AuthMethodEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

package com.syed.identityservice.domain.enums;

public enum ScopeEnum {
    OPENID("openid"),
    EMAIL("email"),
    PHONE("phone");

    private final String value;

    ScopeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

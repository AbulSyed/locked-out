package com.syed.identityservice.exception;

public enum ErrorConstant {

    FIELD_ALREADY_USED("%s field is already in use, please try another name");

    private final String value;

    ErrorConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String formatMessage(String name) {
        return String.format(value, name);
    }
}

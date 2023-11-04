package com.syed.identityservice.exception;

public enum ErrorConstant {

    FIELD_ALREADY_USED("%s field is already in use, please try another value"),
    RESOURCE_NOT_FOUND("%s not found"),
    ROLE_ALREADY_PRESENT("Role already present for %s"),
    AUTHORITY_ALREADY_PRESENT("Authority already present for %s"),
    INVALID_REQUEST("Missing parameters");

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

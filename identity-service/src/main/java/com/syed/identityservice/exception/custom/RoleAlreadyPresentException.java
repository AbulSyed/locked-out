package com.syed.identityservice.exception.custom;

public class RoleAlreadyPresentException extends RuntimeException {

    public RoleAlreadyPresentException(String message) {
        super(message);
    }
}

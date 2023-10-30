package com.syed.identityservice.exception.custom;

public class AuthorityAlreadyPresentException extends RuntimeException {

    public AuthorityAlreadyPresentException(String message) {
        super(message);
    }
}

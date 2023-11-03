package com.eticket.exceptions;

public class UniqueEmailValidationException extends Exception {
    public UniqueEmailValidationException(String errorMessage) {
        super(errorMessage);
    }   public UniqueEmailValidationException() {
        super();
    }
}

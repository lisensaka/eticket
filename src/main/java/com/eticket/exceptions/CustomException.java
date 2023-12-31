package com.eticket.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CustomException extends RuntimeException {

    private final String message;
    private final HttpStatus httpStatus;


    public CustomException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public CustomException(String message, HttpStatus httpStatus, Throwable cause) {
        super(cause);
        this.message = message;
        this.httpStatus = httpStatus;
    }

}

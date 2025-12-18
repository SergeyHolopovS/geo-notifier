package com.geomap.exceptions.basic;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BasicException extends RuntimeException {

    private final String message;
    private final HttpStatus status;

    public BasicException(String message, HttpStatus status) {
        super();
        this.message = message;
        this.status = status;
    }

}

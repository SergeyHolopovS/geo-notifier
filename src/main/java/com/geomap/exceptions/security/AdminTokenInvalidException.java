package com.geomap.exceptions.security;

import com.geomap.exceptions.basic.BasicException;
import org.springframework.http.HttpStatus;

public class AdminTokenInvalidException extends BasicException {

    private final static String DEFAULT_MESSAGE = "Ключ администратора невалиден";

    public AdminTokenInvalidException() {
        super(DEFAULT_MESSAGE, HttpStatus.FORBIDDEN);
    }

    public AdminTokenInvalidException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }

}

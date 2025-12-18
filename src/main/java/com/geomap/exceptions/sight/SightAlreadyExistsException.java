package com.geomap.exceptions.sight;

import com.geomap.exceptions.basic.BasicException;
import org.springframework.http.HttpStatus;

public class SightAlreadyExistsException extends BasicException {

    private final static String DEFAULT_MESSAGE = "Такая достопримечательность уже существует";

    public SightAlreadyExistsException() {
        super(DEFAULT_MESSAGE, HttpStatus.BAD_REQUEST);
    }

    public SightAlreadyExistsException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

}

package com.geomap.exceptions;

import com.geomap.exceptions.basic.BasicException;
import com.geomap.exceptions.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BasicException.class)
    public ResponseEntity<ErrorDto> handleBasic(BasicException exception) {
        return new ResponseEntity<>(
                ErrorDto
                        .builder()
                        .status(exception.getStatus())
                        .message(exception.getMessage())
                        .build(),
                exception.getStatus()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorDto validationHandlerEnums(MethodArgumentNotValidException exception) {
        return ErrorDto
                .builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Запрос невалиден")
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDto validationHandler(MethodArgumentNotValidException exception) {
        return ErrorDto
                .builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Запрос невалиден")
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorDto exceptionHandler(Exception exception) {
        log.warn(
            "{}\n{}\n{}",
            exception.getMessage(),
            exception.getClass(),
            String.join(
                "\n",
                Arrays.stream(
                    exception.getStackTrace()
                )
                    .map(StackTraceElement::toString)
                    .toList()
            )
        );
        return ErrorDto
                .builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Internal server error.")
                .build();
    }

}

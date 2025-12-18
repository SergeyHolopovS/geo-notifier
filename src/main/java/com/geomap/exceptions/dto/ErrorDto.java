package com.geomap.exceptions.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Builder
@Data
public class ErrorDto {

    private HttpStatus status;

    private String message;

}

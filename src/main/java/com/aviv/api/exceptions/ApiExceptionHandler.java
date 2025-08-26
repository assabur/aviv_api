package com.aviv.api.exceptions;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;


@ControllerAdvice
public class ApiExceptionHandler {


    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler (value = {ApiRequestException.class})
    public @ResponseBody ErrorEntity handleApiRequestException (ApiRequestException exception){

        return new ErrorEntity(BAD_REQUEST,exception.getMessage(), ZonedDateTime.now(ZoneId.of("Z"))) ;

    }

   /* @ExceptionHandler (value = {DataIntegrityViolationException.class})
    public @ResponseBody ErrorEntity constraintExpection (ApiRequestException exception){

        return new ErrorEntity(BAD_REQUEST,exception.getMessage(), ZonedDateTime.now(ZoneId.of("Z"))) ;

    }*/
}

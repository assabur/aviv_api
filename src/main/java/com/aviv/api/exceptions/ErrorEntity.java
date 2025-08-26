package com.aviv.api.exceptions;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ErrorEntity(HttpStatus httpstatus ,
                          String message,
                          ZonedDateTime timestamp
)
{
}

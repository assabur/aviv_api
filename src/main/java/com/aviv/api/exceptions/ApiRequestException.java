package com.aviv.api.exceptions;

public class ApiRequestException extends RuntimeException {

    public ApiRequestException(String message) {
        super(message);
    }

    public ApiRequestException() {
        super();
    }
    public ApiRequestException(String message, Throwable cause) {

        super(message, cause);

    }

   public ApiRequestException(Throwable cause) {
        super(cause);
    }
}
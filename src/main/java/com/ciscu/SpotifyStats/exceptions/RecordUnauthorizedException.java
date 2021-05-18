package com.ciscu.SpotifyStats.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class RecordUnauthorizedException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    
    private String message;

    public RecordUnauthorizedException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}

package com.silksoftgroup.otpservice.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadRequestException extends ResponseStatusException {
    public BadRequestException() {
        super(HttpStatus.BAD_REQUEST);
    }

    public BadRequestException(String msg) {
        super(HttpStatus.BAD_REQUEST, msg);
    }
}

package com.gustavo.pollapi.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidPollOptionException extends ResponseStatusException {

    public InvalidPollOptionException() {
        super(HttpStatus.BAD_REQUEST, "Invalid option.");
    }
}

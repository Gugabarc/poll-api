package com.gustavo.pollapi.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidVoterException extends ResponseStatusException {

    public InvalidVoterException() {
        super(HttpStatus.UNAUTHORIZED, "Unauthorized CPF.");
    }
}

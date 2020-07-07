package com.gustavo.pollapi.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class VoterNotFoundException extends ResponseStatusException {

    public VoterNotFoundException() {
        super(HttpStatus.NOT_FOUND, "CPF not found.");
    }
}

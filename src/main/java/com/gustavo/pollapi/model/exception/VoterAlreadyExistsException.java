package com.gustavo.pollapi.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class VoterAlreadyExistsException extends ResponseStatusException {

    public VoterAlreadyExistsException() {
        super(HttpStatus.BAD_REQUEST, "Pessoa já cadastrada.");
    }
}

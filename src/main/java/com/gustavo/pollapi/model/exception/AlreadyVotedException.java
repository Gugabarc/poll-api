package com.gustavo.pollapi.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AlreadyVotedException extends ResponseStatusException {

    public AlreadyVotedException() {
        super(HttpStatus.UNAUTHORIZED, "CPF already voted.");
    }
}

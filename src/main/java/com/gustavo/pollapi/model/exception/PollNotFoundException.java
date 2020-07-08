package com.gustavo.pollapi.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PollNotFoundException extends ResponseStatusException {

    public PollNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Poll not found.");
    }
}

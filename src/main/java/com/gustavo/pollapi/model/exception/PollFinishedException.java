package com.gustavo.pollapi.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PollFinishedException extends ResponseStatusException {

    public PollFinishedException() {
        super(HttpStatus.FORBIDDEN, "Poll ended.");
    }
}

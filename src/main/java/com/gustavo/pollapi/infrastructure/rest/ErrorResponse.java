package com.gustavo.pollapi.infrastructure.rest;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.Collection;

public class ErrorResponse {

    private final Collection<ErrorDetail> errors;

    public ErrorResponse(Collection<ErrorDetail> errors) {
        this.errors = errors;
    }

    @JsonGetter
    public Collection<ErrorDetail> errors() {
        return errors;
    }
}


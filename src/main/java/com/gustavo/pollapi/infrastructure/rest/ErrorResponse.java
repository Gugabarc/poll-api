package com.gustavo.pollapi.infrastructure.rest;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.Collection;

public class ErrorResponse {

    private final Collection<ErrorDetail> errors;

    public ErrorResponse(Collection<ErrorDetail> errors) {
        this.errors = errors;
    }

    public Collection<ErrorDetail> getErrors() {
        return errors;
    }
}


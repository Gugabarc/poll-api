package com.gustavo.pollapi.infrastructure.rest;

import com.fasterxml.jackson.annotation.JsonGetter;

public class ErrorDetail {

    private final String field;
    private final String message;

    public ErrorDetail(String field, String message) {
        this.field = field;
        this.message = message;
    }

    @JsonGetter
    public String field() {
        return field;
    }

    @JsonGetter
    public String message() {
        return message;
    }
}


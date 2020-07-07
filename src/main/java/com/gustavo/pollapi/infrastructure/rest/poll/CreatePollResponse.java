package com.gustavo.pollapi.infrastructure.rest.poll;

import com.fasterxml.jackson.annotation.JsonGetter;

public class CreatePollResponse {

    private String id;

    public CreatePollResponse(String id) {
        this.id = id;
    }

    @JsonGetter
    public String id() {
        return id;
    }
}

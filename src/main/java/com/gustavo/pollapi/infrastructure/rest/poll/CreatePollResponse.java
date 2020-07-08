package com.gustavo.pollapi.infrastructure.rest.poll;

import com.fasterxml.jackson.annotation.JsonGetter;

public class CreatePollResponse {

    private String id;

    public CreatePollResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

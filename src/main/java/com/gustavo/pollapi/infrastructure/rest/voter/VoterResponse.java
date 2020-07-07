package com.gustavo.pollapi.infrastructure.rest.voter;

import com.fasterxml.jackson.annotation.JsonGetter;

public class VoterResponse {

    private final String cpf;

    public VoterResponse(String cpf) {
        this.cpf = cpf;
    }

    @JsonGetter
    public String cpf() {
        return cpf;
    }
}

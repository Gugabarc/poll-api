package com.gustavo.pollapi.infrastructure.rest.voter;

public class VoterResponse {

    private final String cpf;

    public VoterResponse(String cpf) {
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }
}

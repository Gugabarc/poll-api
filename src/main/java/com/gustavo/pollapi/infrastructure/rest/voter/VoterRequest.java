package com.gustavo.pollapi.infrastructure.rest.voter;

import org.hibernate.validator.constraints.br.CPF;

public class VoterRequest {

    @CPF
    private String cpf;

    public VoterRequest() {
    }

    public String cpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}

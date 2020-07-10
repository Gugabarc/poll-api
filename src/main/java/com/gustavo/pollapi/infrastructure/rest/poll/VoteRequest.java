package com.gustavo.pollapi.infrastructure.rest.poll;

import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;

public class VoteRequest {

    @CPF
    private String cpf;

    @NotBlank
    private String optionAlias;

    public VoteRequest() {
    }

    public String cpf() {
        return cpf;
    }

    public String optionAlias() {
        return optionAlias;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setOptionAlias(String optionAlias) {
        this.optionAlias = optionAlias;
    }
}
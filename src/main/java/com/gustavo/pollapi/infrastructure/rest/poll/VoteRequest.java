package com.gustavo.pollapi.infrastructure.rest.poll;

import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;

public class VoteRequest {

    @CPF
    private String cpf;

    @NotBlank
    private String option;

    public VoteRequest() {
    }

    public String cpf() {
        return cpf;
    }

    public String option() {
        return option;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
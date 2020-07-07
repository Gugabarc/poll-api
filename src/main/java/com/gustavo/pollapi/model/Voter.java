package com.gustavo.pollapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "voters")
public class Voter {

    @Id
    private String cpf;

    public Voter(String cpf) {
        this.cpf = cpf;
    }

    public String cpf() {
        return cpf;
    }
}

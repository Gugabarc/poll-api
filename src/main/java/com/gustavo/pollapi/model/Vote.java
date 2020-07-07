package com.gustavo.pollapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "vote")
public class Vote {

    @Id
    private String id;

    private String cpf;

    private String pollId;

    private LocalDateTime date;

    public Vote(String cpf, String pollId) {
        this.cpf = cpf;
        this.pollId = pollId;
        this.date = LocalDateTime.now();
    }
}

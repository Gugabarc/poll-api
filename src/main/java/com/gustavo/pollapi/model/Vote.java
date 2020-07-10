package com.gustavo.pollapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Document(collection = "vote")
public class Vote {

    @Id
    private String id;

    @NotBlank
    @DBRef
    private Voter voter;

    @NotBlank
    @DBRef
    private Poll poll;

    @FutureOrPresent
    private LocalDateTime date;

    public Vote(Voter voter, Poll poll) {
        this.voter = voter;
        this.poll = poll;
        this.date = LocalDateTime.now();
    }
}

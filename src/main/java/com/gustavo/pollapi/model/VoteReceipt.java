package com.gustavo.pollapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "vote_receipt")
public class VoteReceipt {

    @Id
    private String id;

    private String cpf;

    private LocalDateTime date;

    private String receiptCode;

}

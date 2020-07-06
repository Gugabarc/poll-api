package com.gustavo.pollapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Document(collection = "polls")
public class Poll {

    @Id
    private String id;

    private String name;
    private String description;
    private Integer expirationMinutes;
    private LocalDateTime openDate;
    private Set<PollOption> options;
}

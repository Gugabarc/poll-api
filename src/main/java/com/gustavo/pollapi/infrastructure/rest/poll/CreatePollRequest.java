package com.gustavo.pollapi.infrastructure.rest.poll;

import javax.validation.constraints.NotBlank;

public class CreatePollRequest {

    @NotBlank(message = "Poll question is required.")
    private String question;

    private Integer expirationInMinutes;
    private String description;

    public CreatePollRequest() {
    }

    public String question() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer expirationInMinutes() {
        return expirationInMinutes;
    }

    public void setExpirationInMinutes(Integer expirationInMinutes) {
        this.expirationInMinutes = expirationInMinutes;
    }

    public String description() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

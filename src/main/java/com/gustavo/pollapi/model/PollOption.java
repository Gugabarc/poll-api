package com.gustavo.pollapi.model;

public class PollOption {

    private String response;
    private String description;
    private Integer votes;

    public PollOption(String response, String description, Integer votes) {
        this.response = response;
        this.description = description;
        this.votes = votes;
    }

    public String response() {
        return response;
    }

    public String description() {
        return description;
    }

    public Integer votes() {
        return votes;
    }
}

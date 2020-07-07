package com.gustavo.pollapi.model;

public class PollOption {

    private String alias;
    private String response;
    private Integer voteCount;

    public PollOption(String alias, String response, Integer voteCount) {
        this.alias = alias;
        this.response = response;
        this.voteCount = voteCount;
    }

    public void addVote() {
        voteCount++;
    }

    public String alias() {
        return alias;
    }

    public String response() {
        return response;
    }

    public Integer voteCount() {
        return voteCount;
    }
}


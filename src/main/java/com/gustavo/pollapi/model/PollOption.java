package com.gustavo.pollapi.model;

public class PollOption {

    private String alias;
    private String response;
    private int voteCount;

    public PollOption(String alias, String response) {
        this.alias = alias;
        this.response = response;
        this.voteCount = 0;
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

    public int voteCount() {
        return voteCount;
    }
}


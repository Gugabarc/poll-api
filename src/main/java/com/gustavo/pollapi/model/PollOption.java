package com.gustavo.pollapi.model;

public class PollOption {

    private String alias;
    private String option;
    private int voteCount;

    public PollOption(String alias, String option) {
        this.alias = alias;
        this.option = option;
        this.voteCount = 0;
    }

    public void addVote() {
        voteCount++;
    }

    public String alias() {
        return alias;
    }

    public String option() {
        return option;
    }

    public int voteCount() {
        return voteCount;
    }
}


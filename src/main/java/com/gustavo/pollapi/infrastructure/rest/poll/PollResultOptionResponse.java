package com.gustavo.pollapi.infrastructure.rest.poll;

public class PollResultOptionResponse {

    private String option;
    private String alias;
    private int votes;

    public PollResultOptionResponse() {
    }

    public PollResultOptionResponse(String option, String alias, int votes) {
        this.option = option;
        this.alias = alias;
        this.votes = votes;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getOption() {
        return option;
    }

    public int getVotes() {
        return votes;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}

package com.gustavo.pollapi.infrastructure.rest.poll;

public class PollResultResponse {

    private String option;
    private int votes;

    public PollResultResponse() {
    }

    public PollResultResponse(String option, int votes) {
        this.option = option;
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
}

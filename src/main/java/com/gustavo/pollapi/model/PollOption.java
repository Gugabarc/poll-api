package com.gustavo.pollapi.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

public class PollOption {

    @NotBlank
    private String alias;

    @NotBlank
    private String option;

    @PositiveOrZero
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


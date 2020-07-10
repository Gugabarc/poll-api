package com.gustavo.pollapi.infrastructure.rest.poll;

import com.gustavo.pollapi.model.Poll;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

public class PollResultsResponse {

    private Collection<PollResultOptionResponse> options;
    private LocalDateTime expiresIn;
    private String question;

    public PollResultsResponse() {
    }

    public PollResultsResponse(Poll poll) {
        options = poll.options().stream()
                .map(p -> new PollResultOptionResponse(p.option(), p.alias(), p.voteCount()))
                .collect(Collectors.toList());
        this.expiresIn = poll.startedAt().plusMinutes(poll.expirationInMinutes());
        this.question = poll.question();
    }

    public Collection<PollResultOptionResponse> getOptions() {
        return options;
    }

    public LocalDateTime getExpiresIn() {
        return expiresIn;
    }

    public String getQuestion() {
        return question;
    }
}

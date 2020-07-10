package com.gustavo.pollapi.infrastructure.rest.poll;

import com.gustavo.pollapi.model.Poll;
import com.gustavo.pollapi.model.PollOption;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

public class PollResultsResponse {

    private Collection<PollResultResponse> responses;
    private LocalDateTime endIn;
    private String question;

    public PollResultsResponse() {
    }

    public PollResultsResponse(Poll poll) {
        responses = poll.options().stream()
                .map(p -> new PollResultResponse(p.option(), p.alias(), p.voteCount()))
                .collect(Collectors.toList());
        this.endIn = poll.startedAt().plusMinutes(poll.expirationInMinutes());
        this.question = poll.question();
    }

    public Collection<PollResultResponse> getResponses() {
        return responses;
    }


    public LocalDateTime getEndIn() {
        return endIn;
    }

    public String getQuestion() {
        return question;
    }
}

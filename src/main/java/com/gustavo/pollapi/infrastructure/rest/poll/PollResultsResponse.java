package com.gustavo.pollapi.infrastructure.rest.poll;

import com.gustavo.pollapi.model.PollOption;

import java.util.Collection;
import java.util.stream.Collectors;

public class PollResultsResponse {

    private Collection<PollResultResponse> responses;

    public PollResultsResponse() {
    }

    public PollResultsResponse(Collection<PollOption> pollOptions) {
        responses = pollOptions.stream()
                .map(p -> new PollResultResponse(p.option(), p.voteCount()))
                .collect(Collectors.toList());
    }

    public Collection<PollResultResponse> getResponses() {
        return responses;
    }

    public void setResponses(Collection<PollResultResponse> responses) {
        this.responses = responses;
    }
}

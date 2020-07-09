package com.gustavo.pollapi.infrastructure.rest.poll;

import com.gustavo.pollapi.application.PollService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/poll")
public class PollApi {

    private Logger log = LoggerFactory.getLogger(PollApi.class);

    private PollService pollService;

    public PollApi(PollService pollService) {
        this.pollService = pollService;
    }

    @PostMapping
    public Mono<CreatePollResponse> createPoll(@RequestBody @Valid CreatePollRequest request) {
        log.info("Creating poll, question[{}]", request.question());

        return pollService.create(request.question(), request.description(), request.expirationInMinutes())
                .map(id -> new CreatePollResponse(id))
                .doOnSuccess(pollResponse -> log.info("Poll created. Id[{}]", pollResponse.getId()));
    }

    @PostMapping("/{pollId}/vote")
    public Mono<Void> vote(@PathVariable String pollId, @RequestBody @Valid VoteRequest request) {
        log.info("Voting in poll id [{}] for cpf [{}]", pollId, request.cpf());

        return pollService.vote(pollId, request.cpf(), request.option())
                .doOnSuccess(v -> log.info("Vote finished for cpf [{}]", request.cpf()));
    }

    @GetMapping("/{pollId}/result")
    public Mono<PollResultsResponse> getResult(@PathVariable String pollId) {
        log.info("Requested poll result id [{}]", pollId);
        return pollService.resultVote(pollId)
                .map(p -> new PollResultsResponse(p.options()))
                .doOnSuccess(p -> log.info("Finished for poll [{}]", pollId));
    }
}

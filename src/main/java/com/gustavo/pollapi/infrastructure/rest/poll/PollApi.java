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
    public Mono<CreatePollResponse> createElection(@RequestBody @Valid CreatePollRequest request) {
        log.info("Creating poll, question[{}]", request.question());

        return pollService.create(request.question(), request.description(), request.expirationInMinutes())
                .map(id -> new CreatePollResponse(id))
                .doOnSuccess(pollResponse -> log.info("Poll created. Id[{}]", pollResponse.id()));
    }
}

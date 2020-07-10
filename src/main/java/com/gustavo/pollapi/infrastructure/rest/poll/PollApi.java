package com.gustavo.pollapi.infrastructure.rest.poll;

import com.gustavo.pollapi.application.PollService;
import com.gustavo.pollapi.model.Poll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<CreatePollResponse> createPoll(@RequestBody @Valid CreatePollRequest request) {
        log.info("Creating poll, question[{}]", request.question());
        String id = pollService.create(request.question(), request.description(), request.expirationInMinutes());
        log.info("Poll created. Id[{}]", id);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreatePollResponse(id));
    }

    @PostMapping("/{pollId}/vote")
    public ResponseEntity<Void> vote(@PathVariable String pollId, @RequestBody @Valid VoteRequest request) {
        log.info("Voting in poll id [{}] for cpf [{}]", pollId, request.cpf());
        pollService.vote(pollId, request.cpf(), request.optionAlias());
        log.info("Vote finished for cpf [{}]", request.cpf());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{pollId}/result")
    public ResponseEntity<PollResultsResponse> getResult(@PathVariable String pollId) {
        log.info("Requested poll result id [{}]", pollId);
        Poll poll = pollService.findById(pollId);
        return ResponseEntity.ok().body(new PollResultsResponse(poll));
    }
}

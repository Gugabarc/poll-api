package com.gustavo.pollapi.infrastructure.rest.voter;

import com.gustavo.pollapi.application.VoterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/voters")
public class VoterApi {

    private Logger log = LoggerFactory.getLogger(VoterApi.class);
    private VoterService voterService;

    public VoterApi(VoterService voterService) {
        this.voterService = voterService;
    }

    @PostMapping
    public Mono<Void> createVoter(@Valid @RequestBody VoterRequest request) {
        log.info("Creating voter with CPF {}", request.cpf());

        return voterService.create(request.cpf())
                .doOnSuccess(r -> log.info("Created voter CPF [{}]", request.cpf()));
    }


}

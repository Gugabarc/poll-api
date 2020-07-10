package com.gustavo.pollapi.infrastructure.rest.voter;

import com.gustavo.pollapi.application.VoterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Void> createVoter(@Valid @RequestBody VoterRequest request) {
        log.info("Creating voter with CPF {}", request.cpf());
        voterService.create(request.cpf());
        log.info("Created voter CPF [{}]", request.cpf());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}

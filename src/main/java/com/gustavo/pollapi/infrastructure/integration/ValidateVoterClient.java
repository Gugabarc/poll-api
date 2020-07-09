package com.gustavo.pollapi.infrastructure.integration;

import com.gustavo.pollapi.model.exception.InvalidVoterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static reactor.core.publisher.Mono.error;

@Component
public class ValidateVoterClient {

    private static final Logger log = LoggerFactory.getLogger(ValidateVoterClient.class);

    @Value("${validate.voter.url}")
    private String url;

    public Mono<ValidateVoterResponse> validate(String cpf) {
        Mono<ValidateVoterResponse> responseMono = WebClient.create().get()
                .uri(url + cpf)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, v -> error(new InvalidVoterException()))
                .bodyToMono(ValidateVoterResponse.class);

        log.info("Request to validate CPF returned {}", responseMono.block().getStatus());

        return responseMono;
    }

}
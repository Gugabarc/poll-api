package com.gustavo.pollapi.infrastructure.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static reactor.core.publisher.Mono.error;

@Component
public class ValidateVoterClient {

    public static final String ERROR_MESSAGE = "CPF INVALIDO";

    @Value("${validate.voter.url}")
    private String url;

    public Mono<ValidateVoterResponse> validate(String cpf) {
        return WebClient.create().get()
                .uri(url + cpf)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        x -> error(new ResponseStatusException(BAD_REQUEST, ERROR_MESSAGE)))
                .bodyToMono(ValidateVoterResponse.class);
    }

}
package com.gustavo.pollapi.infrastructure.integration;

import com.gustavo.pollapi.model.exception.InvalidVoterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ValidateVoterClient {

    private static final Logger log = LoggerFactory.getLogger(ValidateVoterClient.class);

    @Value("${validate.voter.url}")
    private String url;

    public ValidateVoterClient() {
    }

    public ValidateVoterResponse validate(String cpf) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ValidateVoterResponse> response = restTemplate.getForEntity(url + "/" + cpf, ValidateVoterResponse.class);

        if(!response.getStatusCode().is2xxSuccessful()){
            throw new InvalidVoterException();
        }

        ValidateVoterResponse validateVoterResponse = response.getBody();

        log.info("Request to validate CPF returned {}", validateVoterResponse.getStatus());

        return validateVoterResponse;
    }

}
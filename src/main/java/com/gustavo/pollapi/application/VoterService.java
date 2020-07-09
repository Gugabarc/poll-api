package com.gustavo.pollapi.application;

import com.gustavo.pollapi.infrastructure.integration.ValidateVoterClient;
import com.gustavo.pollapi.infrastructure.repository.VoterRepository;
import com.gustavo.pollapi.model.Voter;
import com.gustavo.pollapi.model.exception.InvalidVoterException;
import com.gustavo.pollapi.model.exception.VoterAlreadyExistsException;
import com.gustavo.pollapi.model.exception.VoterNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.gustavo.pollapi.model.VoterStatus.ABLE_TO_VOTE;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static reactor.core.publisher.Mono.error;

@Service
public class VoterService {

    private VoterRepository voterRepository;
    private ValidateVoterClient validateVoterClient;

    public VoterService(VoterRepository voterRepository, ValidateVoterClient validateVoterClient) {
        this.voterRepository = voterRepository;
        this.validateVoterClient = validateVoterClient;
    }

    public Mono<Void> create(String cpf) {
        return voterRepository.findById(cpf)
                .hasElement()
                .filter(v -> !v)
                .switchIfEmpty(error(new VoterAlreadyExistsException()))
                .flatMap(v -> voterRepository.save(new Voter(cpf)))
                .then();
    }

    public Mono<Voter> findEnabledVoter(String cpf) {
        return validateVoterClient.validate(cpf)
                .filter(r -> equalsIgnoreCase(ABLE_TO_VOTE.name(), r.getStatus()))
                .switchIfEmpty(error(new InvalidVoterException()))
                .flatMap(v -> voterRepository.findById(cpf))
                .switchIfEmpty(error(new VoterNotFoundException()));
    }

}

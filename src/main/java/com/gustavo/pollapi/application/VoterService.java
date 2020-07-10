package com.gustavo.pollapi.application;

import com.gustavo.pollapi.infrastructure.integration.ValidateVoterClient;
import com.gustavo.pollapi.infrastructure.integration.ValidateVoterResponse;
import com.gustavo.pollapi.infrastructure.repository.VoterRepository;
import com.gustavo.pollapi.model.Voter;
import com.gustavo.pollapi.model.exception.InvalidVoterException;
import com.gustavo.pollapi.model.exception.VoterAlreadyExistsException;
import com.gustavo.pollapi.model.exception.VoterNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.gustavo.pollapi.model.VoterStatus.UNABLE_TO_VOTE;

@Service
public class VoterService {

    private VoterRepository voterRepository;
    private ValidateVoterClient validateVoterClient;

    public VoterService(VoterRepository voterRepository, ValidateVoterClient validateVoterClient) {
        this.voterRepository = voterRepository;
        this.validateVoterClient = validateVoterClient;
    }

    public void create(String cpf) {
        if(voterRepository.findById(cpf).isPresent()){
            throw new VoterAlreadyExistsException();
        }

        voterRepository.save(new Voter(cpf));
    }

    public Voter findEnabledVoter(String cpf) {
        checkVoterStatus(cpf);
        return findById(cpf);
    }

    private Voter findById(String cpf) {
        Optional<Voter> voter = voterRepository.findById(cpf);
        if(!voter.isPresent()){
            throw new VoterNotFoundException();
        }
        return voter.get();
    }

    private void checkVoterStatus(String cpf) {
        ValidateVoterResponse validateVoterResponse = validateVoterClient.validate(cpf);
        if(validateVoterResponse.getStatus() == UNABLE_TO_VOTE){
            throw new InvalidVoterException();
        }
    }

}

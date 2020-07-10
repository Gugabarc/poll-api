package com.gustavo.pollapi.application;

import com.gustavo.pollapi.infrastructure.integration.ValidateVoterClient;
import com.gustavo.pollapi.infrastructure.repository.VoterRepository;
import com.gustavo.pollapi.model.Voter;
import com.gustavo.pollapi.model.exception.VoterAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class VoterServiceTest {

    private VoterService voterService;
    private VoterRepository voterRepository;
    private ValidateVoterClient validateVoterClient;
    private ArgumentCaptor<Voter> voterArgumentCaptor;

    @BeforeEach
    private void setup(){
        voterRepository = mock(VoterRepository.class);
        validateVoterClient = mock(ValidateVoterClient.class);
        voterService = new VoterService(voterRepository, validateVoterClient);
        voterArgumentCaptor = ArgumentCaptor.forClass(Voter.class);
    }

    @Test
    void whenVoterAlreadyExistsThenThrowsVoterAlreadyExistsException() {
        Voter voter = new Voter("1111111111");
        when(voterRepository.findById(voter.cpf())).thenReturn(Optional.of(voter));

        assertThatThrownBy(() -> voterService.create(voter.cpf())).isInstanceOf(VoterAlreadyExistsException.class);
    }

    @Test
    void whenVoterIsNewThenSaveIt() {
        Voter voter = new Voter("1111111111");
        when(voterRepository.findById(voter.cpf())).thenReturn(Optional.empty());

        voterService.create(voter.cpf());

        verify(voterRepository).save(voterArgumentCaptor.capture());
        assertThat(voterArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(voter);
    }
}
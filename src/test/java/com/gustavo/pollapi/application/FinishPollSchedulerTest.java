package com.gustavo.pollapi.application;

import com.gustavo.pollapi.PollStub;
import com.gustavo.pollapi.infrastructure.repository.PollRepository;
import com.gustavo.pollapi.model.Poll;
import com.gustavo.pollapi.model.PollMessageSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class FinishPollSchedulerTest {

    private FinishPollScheduler finishPollScheduler;
    private PollRepository pollRepository;
    private PollMessageSender pollMessageSender;
    private ArgumentCaptor<Poll> pollArgumentCaptor;

    @BeforeEach
    public void setup(){
        pollRepository = mock(PollRepository.class);
        pollMessageSender = mock(PollMessageSender.class);
        finishPollScheduler = new FinishPollScheduler(pollRepository, pollMessageSender);
        pollArgumentCaptor = ArgumentCaptor.forClass(Poll.class);
    }

    @Test
    public void whenThereIsPollToBeFinishedThenFinishAndSaveIt() {
        Poll poll = PollStub.aPoll()
                .startedAt(LocalDateTime.now().minusMinutes(3L))
                .isClosed(false)
                .build();
        when(pollRepository.findAllByIsClosed(false)).thenReturn(Collections.singleton(poll));

        finishPollScheduler.finishPoll();

        verify(pollRepository).save(pollArgumentCaptor.capture());
        Poll savedPoll = pollArgumentCaptor.getValue();
        assertThat(savedPoll.isClosed()).isTrue();
        assertThat(savedPoll).usingRecursiveComparison().ignoringFields("isClosed").isEqualTo(poll);

        verify(pollMessageSender).send(pollArgumentCaptor.capture());
        savedPoll = pollArgumentCaptor.getValue();
        assertThat(savedPoll.isClosed()).isTrue();
        assertThat(savedPoll).usingRecursiveComparison().ignoringFields("isClosed").isEqualTo(poll);
    }

    @Test
    public void whenThereIsNoPollToBeFinishedThenDoNothing() {
        when(pollRepository.findAllByIsClosed(false)).thenReturn(Collections.emptyList());

        finishPollScheduler.finishPoll();

        verify(pollRepository, never()).save(any());
        verify(pollMessageSender, never()).send(any());
    }
}
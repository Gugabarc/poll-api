package com.gustavo.pollapi.application;

import com.gustavo.pollapi.PollStub;
import com.gustavo.pollapi.infrastructure.repository.PollRepository;
import com.gustavo.pollapi.infrastructure.repository.VoteRepository;
import com.gustavo.pollapi.model.Poll;
import com.gustavo.pollapi.model.Vote;
import com.gustavo.pollapi.model.Voter;
import com.gustavo.pollapi.model.exception.PollFinishedException;
import com.gustavo.pollapi.model.exception.PollNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PollServiceTest {

    private PollRepository pollRepository;
    private VoteRepository voteRepository;
    private VoterService voterService;
    private PollService pollService;
    private ArgumentCaptor<Poll> pollArgumentCaptor;

    @BeforeEach
    public void setup(){
        pollRepository = mock(PollRepository.class);
        voteRepository = mock(VoteRepository.class);
        voterService = mock(VoterService.class);
        pollService = new PollService(pollRepository, voterService, voteRepository);
        pollArgumentCaptor = ArgumentCaptor.forClass(Poll.class);
    }

    @Test
    void whenCreatingPollThenReturnsSavedId() {
        Poll poll = PollStub.aPoll().build();
        when(pollRepository.save(any(Poll.class))).thenReturn(poll);

        String id = pollService.create(poll.question(), poll.description(), poll.expirationInMinutes());

        assertThat(id).isEqualTo(poll.id());
    }

    @Test
    void whenCreatingPollWithNullExpirationInMinutesThenSetDefaultExpirationInMinutes() {
        Poll poll = PollStub.aPoll().build();
        when(pollRepository.save(any(Poll.class))).thenReturn(poll);

        pollService.create(poll.question(), poll.description(), null);

        verify(pollRepository).save(pollArgumentCaptor.capture());

        assertThat(pollArgumentCaptor.getValue().expirationInMinutes()).isEqualTo(1L);
    }

    @Test
    void whenThereIsVoteThenHasVotedReturnsTrue() {
        Voter voter = new Voter("11111111111");
        Poll poll = PollStub.aPoll().build();
        when(voteRepository.findByVoterCpfAndPollId(voter.cpf(), poll.id())).thenReturn(Optional.of(new Vote(voter, poll)));

        boolean hasVoted = pollService.hasVoted(voter.cpf(), poll.id());

        assertThat(hasVoted).isTrue();
    }

    @Test
    void whenThereIsNoVoteThenHasVotedReturnsFalse() {
        Voter voter = new Voter("11111111111");
        Poll poll = PollStub.aPoll().build();
        when(voteRepository.findByVoterCpfAndPollId(voter.cpf(), poll.id())).thenReturn(Optional.empty());

        boolean hasVoted = pollService.hasVoted(voter.cpf(), poll.id());

        assertThat(hasVoted).isFalse();
    }

    @Test
    void whenTryToFindOpenPollByExistentIdAndItsOpenThenThrowsPollFinishedException() {
        Poll poll = PollStub.aPoll().isClosed(false).build();
        when(pollRepository.findById(poll.id())).thenReturn(Optional.of(poll));

        Poll openPoll = pollService.findOpenPoll(poll.id());

        assertThat(openPoll).usingRecursiveComparison().isEqualTo(poll);
    }

    @Test
    void whenTryToFindOpenPollByExistentIdButItsClosedThenThrowsPollFinishedException() {
        Poll closedPoll = PollStub.aPoll().isClosed(true).build();
        when(pollRepository.findById(closedPoll.id())).thenReturn(Optional.of(closedPoll));

        assertThatThrownBy(() -> pollService.findOpenPoll(closedPoll.id())).isInstanceOf(PollFinishedException.class);
    }

    @Test
    void whenTryToFindOpenPollByNonExistentIdThenThrowsPollNotFoundException() {
        String pollId = "1122";
        when(pollRepository.findById(pollId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pollService.findOpenPoll(pollId)).isInstanceOf(PollNotFoundException.class);
    }

    @Test
    void whenFindByExistentIdThenReturnsPoll() {
        Poll poll = PollStub.aPoll().build();
        when(pollRepository.findById(poll.id())).thenReturn(Optional.of(poll));

        Poll foundPoll = pollService.findById(poll.id());

        assertThat(foundPoll).usingRecursiveComparison().isEqualTo(poll);
    }

    @Test
    void whenFindByNonExistentIdThenThrowPollNotFoundException() {
        String pollId = "12";
        when(pollRepository.findById(pollId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pollService.findById(pollId)).isInstanceOf(PollNotFoundException.class);
    }
}
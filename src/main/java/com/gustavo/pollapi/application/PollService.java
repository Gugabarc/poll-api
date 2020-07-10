package com.gustavo.pollapi.application;

import com.gustavo.pollapi.infrastructure.repository.PollRepository;
import com.gustavo.pollapi.infrastructure.repository.VoteRepository;
import com.gustavo.pollapi.model.Poll;
import com.gustavo.pollapi.model.PollOption;
import com.gustavo.pollapi.model.Vote;
import com.gustavo.pollapi.model.Voter;
import com.gustavo.pollapi.model.exception.AlreadyVotedException;
import com.gustavo.pollapi.model.exception.PollFinishedException;
import com.gustavo.pollapi.model.exception.PollNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;


@Service
public class PollService {

    private PollRepository pollRepository;
    private VoteRepository voteRepository;
    private VoterService voterService;

    public PollService(PollRepository pollRepository, VoterService voterService,
                       VoteRepository voteRepository) {
        this.pollRepository = pollRepository;
        this.voterService = voterService;
        this.voteRepository = voteRepository;
    }

    public String create(String question, String description, Integer expirationInMinutes) {
        return Stream.of(pollRepository.save(pollFrom(question, description, expirationInMinutes)))
                .map(p -> p.id())
                .findFirst()
                .get();
    }

    private Poll pollFrom(String question, String description, Integer expirationInMinutes) {
        Set<PollOption> options = new HashSet<>();
        options.add(new PollOption("NO", "Não"));
        options.add(new PollOption("YES", "Sim"));
        return Poll.aPoll()
                .question(question)
                .description(description)
                .expirationMinutes(expirationInMinutes == null ? 1 : expirationInMinutes)
                .startedAt(LocalDateTime.now())
                .options(options)
                .isClosed(false)
                .build();
    }

    public void vote(String pollId, String cpf, String option) {
        Voter voter = voterService.findEnabledVoter(cpf);
        if(hasVoted(cpf, pollId)) {
            throw new AlreadyVotedException();
        }
        Poll poll = findOpenPoll(pollId);
        poll.vote(option);
        pollRepository.save(poll);
        voteRepository.save(new Vote(voter, poll));
    }

    protected boolean hasVoted(String cpf, String pollId) {
        return voteRepository.findByVoterCpfAndPollId(cpf, pollId).isPresent();
    }

    public Poll findOpenPoll(String pollId) {
        Optional<Poll> poll = pollRepository.findById(pollId);
        if(!poll.isPresent()){
           throw new PollNotFoundException();
        }

        if (!poll.get().isOpen()){
            throw new PollFinishedException();
        }
        return poll.get();
    }

    public Poll findById(String pollId) {
        Optional<Poll> poll = pollRepository.findById(pollId);
        if(!poll.isPresent()){
            throw new PollNotFoundException();
        }
        return poll.get();
    }
}

package com.gustavo.pollapi.application;

import com.gustavo.pollapi.infrastructure.repository.PollRepository;
import com.gustavo.pollapi.infrastructure.repository.VoteRepository;
import com.gustavo.pollapi.model.Poll;
import com.gustavo.pollapi.model.PollOption;
import com.gustavo.pollapi.model.Vote;
import com.gustavo.pollapi.model.exception.AlreadyVotedException;
import com.gustavo.pollapi.model.exception.PollFinishedException;
import com.gustavo.pollapi.model.exception.PollNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static reactor.core.publisher.Mono.error;

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

    public Mono<String> create(String question, String description, Integer expirationInMinutes) {
        return pollRepository.save(pollFrom(question, description, expirationInMinutes))
                .map(p -> p.id());
    }

    private Poll pollFrom(String question, String description, Integer expirationInMinutes) {
        Set<PollOption> options = new HashSet<>();
        options.add(new PollOption("NO", "Não"));
        options.add(new PollOption("YES", "Sim"));
        return Poll.aPoll()
                .question(question)
                .description(description)
                .expirationMinutes(expirationInMinutes)
                .startedAt(LocalDateTime.now())
                .options(options)
                .isFinished(false)
                .build();
    }

    public Mono<Void> vote(String pollId, String cpf, String option) {
        return Mono.zip(
                voterService.findEnabledVoter(cpf),
                findOpenPoll(pollId),
                canVote(cpf, pollId))
                .map(o -> {
                    o.getT2().vote(option);
                    pollRepository.save(o.getT2()).subscribe();
                    voteRepository.save(new Vote(o.getT1().cpf(), o.getT2().id())).subscribe();
                    return o;
                }).then();
    }

    protected Mono<Boolean> canVote(String cpf, String pollId) {
        return voteRepository.findByCpfAndPollId(cpf, pollId)
                .hasElement()
                .filter(v -> !v)
                .switchIfEmpty(error(new AlreadyVotedException()));
    }

    public Mono<Poll> findOpenPoll(String pollId) {
        return pollRepository.findById(pollId)
                .switchIfEmpty(error(new PollNotFoundException()))
                .filter(Poll::isOpen)
                .switchIfEmpty(error(new PollFinishedException()));
    }

    public Mono<Poll> resultVote(String pollId) {
        return pollRepository.findById(pollId)
                .switchIfEmpty(error(new PollNotFoundException()));
    }
}

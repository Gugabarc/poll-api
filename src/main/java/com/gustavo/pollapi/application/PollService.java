package com.gustavo.pollapi.application;

import com.gustavo.pollapi.infrastructure.repository.PollRepository;
import com.gustavo.pollapi.infrastructure.repository.VoteReceiptRepository;
import com.gustavo.pollapi.model.Poll;
import com.gustavo.pollapi.model.PollOption;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

@Service
public class PollService {

    private PollRepository pollRepository;
    private VoteReceiptRepository voteReceiptRepository;
    private VoterService voterService;

    public PollService(PollRepository pollRepository, VoterService voterService,
                       VoteReceiptRepository voteReceiptRepository) {
        this.pollRepository = pollRepository;
        this.voterService = voterService;
        this.voteReceiptRepository = voteReceiptRepository;
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
                .options(options)
                .isFinished(false)
                .build();
    }
}

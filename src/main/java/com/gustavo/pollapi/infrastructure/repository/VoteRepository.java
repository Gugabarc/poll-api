package com.gustavo.pollapi.infrastructure.repository;

import com.gustavo.pollapi.model.Vote;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface VoteRepository extends ReactiveMongoRepository<Vote, String> {

    @Query("{ 'cpf': ?0, 'pollId': ?1}")
    Mono<Vote> findByCpfAndPollId(String cpf, String pollId);
}

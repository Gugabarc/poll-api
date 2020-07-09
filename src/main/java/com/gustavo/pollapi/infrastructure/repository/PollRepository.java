package com.gustavo.pollapi.infrastructure.repository;

import com.gustavo.pollapi.model.Poll;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PollRepository extends ReactiveMongoRepository<Poll, String> {

    @Query("{'isFinished' : ?0}")
    Flux<Poll> findAllByIsFinished(boolean isFinished);
}

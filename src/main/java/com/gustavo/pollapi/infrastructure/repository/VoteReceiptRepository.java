package com.gustavo.pollapi.infrastructure.repository;

import com.gustavo.pollapi.model.Vote;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface VoteReceiptRepository extends ReactiveMongoRepository<Vote, String> {

    @Query("{ 'cpf': ?0, 'receiptCode': ?1}")
    Mono<Vote> findByCpfAndReceiptCode(String cpf, String receiptCode);
}

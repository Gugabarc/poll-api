package com.gustavo.pollapi.infrastructure.repository;

import com.gustavo.pollapi.model.Voter;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoterRepository extends ReactiveMongoRepository<Voter, String> {

}
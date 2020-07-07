package com.gustavo.pollapi.infrastructure.repository;

import com.gustavo.pollapi.model.Voter;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VoterRepository extends ReactiveMongoRepository<Voter, String> {

}
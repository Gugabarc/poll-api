package com.gustavo.pollapi.infrastructure.repository;

import com.gustavo.pollapi.model.Voter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoterRepository extends CrudRepository<Voter, String> {

}
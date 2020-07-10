package com.gustavo.pollapi.infrastructure.repository;

import com.gustavo.pollapi.model.Vote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends CrudRepository<Vote, String> {

    Optional<Vote> findByVoterCpfAndPollId(String cpf, String pollId);
}

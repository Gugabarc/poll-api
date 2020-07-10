package com.gustavo.pollapi.infrastructure.repository;

import com.gustavo.pollapi.model.Poll;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PollRepository extends CrudRepository<Poll, String> {

    Collection<Poll> findAllByIsClosed(boolean isClosed);
}

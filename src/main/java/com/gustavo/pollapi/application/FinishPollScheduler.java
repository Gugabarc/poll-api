package com.gustavo.pollapi.application;

import com.gustavo.pollapi.infrastructure.repository.PollRepository;
import com.gustavo.pollapi.model.Poll;
import com.gustavo.pollapi.model.PollMessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class FinishPollScheduler {

    private static final Logger log = LoggerFactory.getLogger(FinishPollScheduler.class);

    private PollRepository pollRepository;
    private PollMessageSender pollMessageSender;

    public FinishPollScheduler(PollRepository pollRepository, PollMessageSender pollMessageSender) {
        this.pollRepository = pollRepository;
        this.pollMessageSender = pollMessageSender;
    }

    @Scheduled(fixedRate = 5000)
    public void finishPoll(){
        Poll poll = pollRepository.findAllByIsFinished(false).blockFirst();
        if(poll == null){
            return;
        }
        log.info("Finishing open poll id {}", poll.id());

        poll.setFinished(true);
        pollRepository.save(poll).subscribe();

        pollMessageSender.send(poll);
    }

}

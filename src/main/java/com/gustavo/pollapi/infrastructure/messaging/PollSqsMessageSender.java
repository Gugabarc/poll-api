package com.gustavo.pollapi.infrastructure.messaging;

import com.google.gson.Gson;
import com.gustavo.pollapi.model.Poll;
import com.gustavo.pollapi.model.PollMessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Component
public class PollSqsMessageSender implements PollMessageSender {

    private static final Logger log = LoggerFactory.getLogger(PollSqsMessageSender.class);

    @Value("${poll.results.queue.name}")
    private String queue;

    private SqsClient sqsClient;

    public PollSqsMessageSender() {
        sqsClient = SqsClient.builder()
                .region(Region.US_EAST_1)
                .build();
    }

    public void send(Poll poll){
        log.info("Sending poll id {} to queue", poll.id());
        sqsClient.sendMessage(SendMessageRequest.builder()
                .queueUrl(queue)
                .messageBody(new Gson().toJson(poll))
                .build());
    }

}

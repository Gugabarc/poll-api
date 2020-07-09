package com.gustavo.pollapi.model;

import com.gustavo.pollapi.model.exception.InvalidPollOptionException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

import static java.time.Duration.between;
import static java.time.LocalDateTime.now;

@Document(collection = "polls")
public class Poll {

    @Id
    private String id;

    private String question;
    private String description;
    private Integer expirationInMinutes;
    private LocalDateTime startedAt;
    private Set<PollOption> options;
    private boolean isFinished;

    public String id() {
        return id;
    }

    public boolean isOpen(){
        return isFinished == false
                && (expirationInMinutes == null || between(startedAt, now()).toMinutes() <= expirationInMinutes);
    }

    public void vote(String optionAlias){
        options.stream()
                .filter(o -> StringUtils.equalsIgnoreCase(o.alias(), optionAlias))
                .findFirst()
                .orElseThrow(() -> new InvalidPollOptionException())
                .addVote();
    }

    public static Builder aPoll() {
        return new Builder();
    }

    public Set<PollOption> options() {
        return options;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public static final class Builder {
        private String id;
        private String question;
        private String description;
        private Integer expirationInMinutes;
        private LocalDateTime startedAt;
        private Set<PollOption> options;
        private boolean isFinished;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder question(String question) {
            this.question = question;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder startedAt(LocalDateTime startedAt) {
            this.startedAt = startedAt;
            return this;
        }

        public Builder expirationMinutes(Integer expirationInMinutes) {
            this.expirationInMinutes = expirationInMinutes;
            return this;
        }

        public Builder options(Set<PollOption> options) {
            this.options = options;
            return this;
        }

        public Builder isFinished(boolean isFinished) {
            this.isFinished = isFinished;
            return this;
        }

        public Poll build() {
            Poll poll = new Poll();
            poll.question = this.question;
            poll.expirationInMinutes = this.expirationInMinutes;
            poll.startedAt = this.startedAt;
            poll.isFinished = this.isFinished;
            poll.description = this.description;
            poll.options = this.options;
            poll.id = this.id;
            return poll;
        }
    }
}

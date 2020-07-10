package com.gustavo.pollapi.model;

import com.gustavo.pollapi.model.exception.InvalidPollOptionException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.Set;

import static java.time.Duration.between;
import static java.time.LocalDateTime.now;

@Document(collection = "polls")
public class Poll {

    @Id
    private String id;

    @NotBlank
    private String question;

    @NotEmpty
    private Set<PollOption> options;

    @PositiveOrZero
    private int expirationInMinutes;

    @FutureOrPresent
    private LocalDateTime startedAt;

    private String description;
    private boolean isClosed;

    public String id() {
        return id;
    }

    public boolean isOpen(){
        return isClosed == false && isNotExpired();
    }

    public boolean isExpired() {
        return between(startedAt, now()).toMinutes() >= expirationInMinutes;
    }

    private boolean isNotExpired() {
        return !isExpired();
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

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public String question() {
        return question;
    }

    public String description() {
        return description;
    }

    public int expirationInMinutes() {
        return expirationInMinutes;
    }

    public LocalDateTime startedAt() {
        return startedAt;
    }

    public static final class Builder {
        private String id;
        private String question;
        private String description;
        private int expirationInMinutes;
        private LocalDateTime startedAt;
        private Set<PollOption> options;
        private boolean isClosed;

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

        public Builder expirationMinutes(int expirationInMinutes) {
            this.expirationInMinutes = expirationInMinutes;
            return this;
        }

        public Builder options(Set<PollOption> options) {
            this.options = options;
            return this;
        }

        public Builder isClosed(boolean isClosed) {
            this.isClosed = isClosed;
            return this;
        }

        public Poll build() {
            Poll poll = new Poll();
            poll.question = this.question;
            poll.expirationInMinutes = this.expirationInMinutes;
            poll.startedAt = this.startedAt;
            poll.isClosed = this.isClosed;
            poll.description = this.description;
            poll.options = this.options;
            poll.id = this.id;
            return poll;
        }
    }
}

package com.gustavo.pollapi;

import com.gustavo.pollapi.model.Poll;
import com.gustavo.pollapi.model.PollOption;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class PollStub {

    public static Builder aPoll() {
        return new Builder();
    }

    public static final class Builder {
        private String id = "1";
        private String question = "Is superman an alien?";
        private String description = "DC Comics question";
        private int expirationInMinutes = 2;
        private LocalDateTime startedAt = LocalDateTime.now();
        private Set<PollOption> options = new HashSet<>();
        private boolean isClosed = false;

        private Builder() {
            options.add(new PollOption("Y", "YES"));
            options.add(new PollOption("N", "NO"));
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
            return Poll.aPoll()
                    .question(question)
                    .expirationMinutes(expirationInMinutes)
                    .startedAt(startedAt)
                    .isClosed(isClosed)
                    .description(description)
                    .options(options)
                    .id(id)
                    .build();
        }
    }
}

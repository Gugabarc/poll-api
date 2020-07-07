package com.gustavo.pollapi.model;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Document(collection = "polls")
public class Poll {

    @Id
    private String id;

    private String name;
    private String description;
    private Integer expirationMinutes;
    private LocalDateTime openDate;
    private Set<PollOption> options;
    private boolean isOpen;

    public String id() {
        return id;
    }

    public void vote(String optionAlias){
        options.stream()
                .filter(o -> StringUtils.equalsIgnoreCase(o.alias(), optionAlias))
                .forEach(PollOption::addVote);
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public static Builder aPoll() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String name;
        private String description;
        private Integer expirationMinutes;
        private LocalDateTime openDate;
        private Set<PollOption> options;
        private boolean isOpen;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder expirationMinutes(Integer expirationMinutes) {
            this.expirationMinutes = expirationMinutes;
            return this;
        }

        public Builder openDate(LocalDateTime openDate) {
            this.openDate = openDate;
            return this;
        }

        public Builder options(Set<PollOption> options) {
            this.options = options;
            return this;
        }

        public Builder isOpen(boolean isOpen) {
            this.isOpen = isOpen;
            return this;
        }

        public Poll build() {
            Poll poll = new Poll();
            poll.name = this.name;
            poll.expirationMinutes = this.expirationMinutes;
            poll.isOpen = this.isOpen;
            poll.openDate = this.openDate;
            poll.description = this.description;
            poll.options = this.options;
            poll.id = this.id;
            return poll;
        }
    }
}

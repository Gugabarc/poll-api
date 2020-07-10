package com.gustavo.pollapi.infrastructure.integration;

import com.gustavo.pollapi.model.VoterStatus;

public class ValidateVoterResponse {

    private VoterStatus status;

    public ValidateVoterResponse(){
    }

    public VoterStatus getStatus() {
        return status;
    }

    public void setStatus(VoterStatus status) {
        this.status = status;
    }
}

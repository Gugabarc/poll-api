package com.gustavo.pollapi.infrastructure.integration;

public class ValidateVoterResponse {

    private String status;

    public ValidateVoterResponse(String status){
        this.status = status;
    }

    public String status() {
        return status;
    }
}

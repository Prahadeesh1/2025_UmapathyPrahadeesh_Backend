package org.example.api;

import com.fasterxml.jackson.annotation.JsonProperty;


// Error Response DTO
public class ErrorResponse {
    @JsonProperty
    private String error;

    @JsonProperty
    private String message;

    public ErrorResponse() {}

    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

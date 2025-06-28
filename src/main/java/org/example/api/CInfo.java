package org.example.api;

import com.fasterxml.jackson.annotation.JsonProperty;



// Response DTO for individual coin information
public class CInfo {
    @JsonProperty
    private Double denomination;

    @JsonProperty
    private Integer count;

    public CInfo() {}

    public CInfo(Double denomination, Integer count) {
        this.denomination = denomination;
        this.count = count;
    }

    public Double getDenomination() {
        return denomination;
    }

    public void setDenomination(Double denomination) {
        this.denomination = denomination;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}

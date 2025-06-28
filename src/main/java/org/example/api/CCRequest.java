package org.example.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

// Request DTO
public class CCRequest {

    @NotNull
    @DecimalMin(value = "0.0", message = "Amount must be greater than or equal to 0")
    @DecimalMax(value = "10000.0", message = "Amount must be less than or equal to 10000")
    @JsonProperty
    private Double amount;

    @NotNull
    @Size(min = 1, message = "At least one denomination must be provided")
    @JsonProperty
    private List<Double> denominations;

    public CCRequest() {}

    public CCRequest(Double amount, List<Double> denominations) {
        this.amount = amount;
        this.denominations = denominations;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public List<Double> getDenominations() {
        return denominations;
    }

    public void setDenominations(List<Double> denominations) {
        this.denominations = denominations;
    }
}

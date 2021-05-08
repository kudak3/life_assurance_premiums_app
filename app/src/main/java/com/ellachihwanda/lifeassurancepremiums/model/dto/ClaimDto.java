package com.ellachihwanda.lifeassurancepremiums.model.dto;

import com.ellachihwanda.lifeassurancepremiums.model.Client;
import com.ellachihwanda.lifeassurancepremiums.model.Policy;

public class ClaimDto {

    private String policyNumber;
    private String description;
    private Long amount;

    public ClaimDto() {
    }

    public ClaimDto(String policyNumber, String description, Long amount) {
        this.policyNumber = policyNumber;
        this.description = description;
        this.amount = amount;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ClaimDto{" +
                ", policyNumber='" + policyNumber + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                '}';
    }
}

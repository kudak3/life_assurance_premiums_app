package com.ellachihwanda.lifeassurancepremiums.model;

import com.ellachihwanda.lifeassurancepremiums.model.enums.ClaimStatus;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class InsuranceClaim implements Serializable {
    private Long id;
    private Date date;
    private Client client;

    private Policy policy;
    private String description;

    private ClaimStatus claimStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ClaimStatus getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(ClaimStatus claimStatus) {
        this.claimStatus = claimStatus;
    }
}

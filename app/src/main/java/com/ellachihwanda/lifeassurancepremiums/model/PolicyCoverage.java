package com.ellachihwanda.lifeassurancepremiums.model;

import com.ellachihwanda.lifeassurancepremiums.model.enums.CoverageStatus;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class PolicyCoverage implements Serializable {
    private PolicyCoverageKey id;

      private String policyNumber;


    private Client client;


    private Policy policy;


    private Date date;

    private CoverageStatus status;

    public PolicyCoverage(Client client, Policy policy) {
        this.client = client;
        this.policy = policy;
    }

    public PolicyCoverage() {

    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public PolicyCoverageKey getId() {
        return id;
    }

    public void setId(PolicyCoverageKey id) {
        this.id = id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public CoverageStatus getStatus() {
        return status;
    }

    public void setStatus(CoverageStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return policy.getName() + " " + policyNumber;
    }
}

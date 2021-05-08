package com.ellachihwanda.lifeassurancepremiums.model;

import com.ellachihwanda.lifeassurancepremiums.model.enums.Plan;
import com.ellachihwanda.lifeassurancepremiums.model.enums.Status;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Policy implements Serializable {
    private Long id;
    private String name;
    private String description;

    private Long premium;

    private Plan plan;
    List<PolicyCoverage> policyCoverageList;

    private Status status;
    private Long totalPayment;
    private Long duration;
    private Long coverageAmount;

    private List<InsuranceClaim> claims = new ArrayList<>();

    public Policy() {
    }

    public Policy(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPremium() {
        return premium;
    }

    public void setPremium(Long premium) {
        this.premium = premium;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public List<PolicyCoverage> getPolicyCoverageList() {
        return policyCoverageList;
    }

    public void setPolicyCoverageList(List<PolicyCoverage> policyCoverageList) {
        this.policyCoverageList = policyCoverageList;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(Long totalPayment) {
        this.totalPayment = totalPayment;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public List<InsuranceClaim> getClaims() {
        return claims;
    }

    public void setClaims(List<InsuranceClaim> claims) {
        this.claims = claims;
    }

    public Long getCoverageAmount() {
        return coverageAmount;
    }

    public void setCoverageAmount(Long coverageAmount) {
        this.coverageAmount = coverageAmount;
    }

    @Override
    public String toString() {
        return "Policy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", premium=" + premium +
                ", plan=" + plan +
                ", policyCoverageList=" + policyCoverageList +
                ", status=" + status +
                ", totalPayment=" + totalPayment +
                ", duration=" + duration +
                ", coverageAmount=" + coverageAmount +
                ", claims=" + claims +
                '}';
    }
}

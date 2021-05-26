package com.ellachihwanda.lifeassurancepremiums.model;

import com.ellachihwanda.lifeassurancepremiums.model.enums.ClaimStatus;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class InsuranceClaim implements Serializable {
    private Long id;


    private Date date;



    private PolicyCoverage policyCoverage;

    private String description;
    private Long amount;

    private ClaimStatus claimStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PolicyCoverage getPolicyCoverage() {
        return policyCoverage;
    }

    public void setPolicyCoverage(PolicyCoverage policyCoverage) {
        this.policyCoverage = policyCoverage;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

package com.ellachihwanda.lifeassurancepremiums.model;

import java.io.Serializable;

public class ClaimKey implements Serializable {

       private Long clientId;

    private Long policyId;

    public ClaimKey(Long clientId, Long policyId) {
        this.clientId = clientId;
        this.policyId = policyId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    @Override
    public String toString() {
        return "ClaimKey{" +
                "clientId=" + clientId +
                ", policyId=" + policyId +
                '}';
    }
}

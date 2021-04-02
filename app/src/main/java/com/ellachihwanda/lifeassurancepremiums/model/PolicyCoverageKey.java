package com.ellachihwanda.lifeassurancepremiums.model;

import java.io.Serializable;

public class PolicyCoverageKey implements Serializable {
    private Long clientId;

    private Long policyId;

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
        return "PolicyCoverageKey{" +
                "clientId=" + clientId +
                ", policyId=" + policyId +
                '}';
    }
}

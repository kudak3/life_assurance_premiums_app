package com.ellachihwanda.lifeassurancepremiums.model.dto;

import com.ellachihwanda.lifeassurancepremiums.model.Client;
import com.ellachihwanda.lifeassurancepremiums.model.Policy;

public class CoverDto {
    Client client;
    Policy policy;

    public CoverDto(Client client, Policy policy) {
        this.client = client;
        this.policy = policy;
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

    @Override
    public String toString() {
        return "CoverDto{" +
                "client=" + client +
                ", policy=" + policy +
                '}';
    }
}

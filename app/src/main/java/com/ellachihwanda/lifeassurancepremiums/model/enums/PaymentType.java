package com.ellachihwanda.lifeassurancepremiums.model.enums;

import java.io.Serializable;

public enum PaymentType implements Serializable {
        ECOCASH("Ecocash"),
    TELECASH("Telecash"),
    ZIPIT("Zipit"),
    ONEMONEY("OneMoney");

    private final String name;

    PaymentType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

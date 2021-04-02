package com.ellachihwanda.lifeassurancepremiums.model.enums;

public enum Status {
    ACTIVE("Active"),
    PAID_UP("Paid up"),
    REINSTATEMENT("Reinstatement"),
    LAPSE("Lapse");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

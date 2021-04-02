package com.ellachihwanda.lifeassurancepremiums.model.enums;

public enum Plan {
    FLEXI_FUNERAL("Flexi Funeral"),
    LIFE_PLAN("Life PLan"),
    SAVINGS("Savings"),
    TERM_SURE("Term sure");

    private final String name;

    Plan(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

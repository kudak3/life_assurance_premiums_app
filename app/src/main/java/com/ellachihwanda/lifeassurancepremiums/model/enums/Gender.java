package com.ellachihwanda.lifeassurancepremiums.model.enums;

public enum Gender {
    MALE("Male"),
    FEMALE("Female");

    private final String name;

    Gender(String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }
}

package com.ellachihwanda.lifeassurancepremiums.model;

import java.io.Serializable;

public class PaymentType implements Serializable {
       private Long id;
   private String name;
    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

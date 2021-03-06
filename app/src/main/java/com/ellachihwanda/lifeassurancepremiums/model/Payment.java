package com.ellachihwanda.lifeassurancepremiums.model;

import java.io.Serializable;
import java.util.Date;

public class Payment implements Serializable {
     private Long id;
    private Client client;
    private String accountNumber;

    private PaymentType paymentType;
    private Long amount;
    private String description;
    private Date date;

    public Payment() {
    }

    public Payment(Client client, String accountNumber, PaymentType paymentType, Long amount, String description) {
        this.client = client;
        this.accountNumber = accountNumber;
        this.paymentType = paymentType;
        this.amount = amount;
        this.description = description;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

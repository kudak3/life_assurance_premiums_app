package com.ellachihwanda.lifeassurancepremiums.model;

import com.ellachihwanda.lifeassurancepremiums.model.enums.Gender;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Client implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String title;
    private String phoneNumber;
    private String email;
    private String idNumber;
    private String homeAddress;
    private String deviceToken;



    private Date dateOfBirth;

    private Gender gender;
      private User user;


    private List<InsuranceClaim> claims = new ArrayList<>();

    private List<Payment> payments = new ArrayList<>();

    private List<PolicyCoverage> policyCoverageList;
     private boolean newEntry ;

    public boolean isNewEntry() {
        return newEntry;
    }

    public void setNewEntry(boolean newEntry) {
        this.newEntry = newEntry;
    }

    public Client() {
    }

    public Client(User user, Date dateOfBirth, Gender gender) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
         this.user = user;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<InsuranceClaim> getClaims() {
        return claims;
    }

    public void setClaims(List<InsuranceClaim> claims) {
        this.claims = claims;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<PolicyCoverage> getPolicyCoverageList() {
        return policyCoverageList;
    }

    public void setPolicyCoverageList(List<PolicyCoverage> policyCoverageList) {
        this.policyCoverageList = policyCoverageList;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", title='" + title + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                ", user=" + user +
                ", claims=" + claims +
                ", payments=" + payments +
                ", policyCoverageList=" + policyCoverageList +
                ", newEntry=" + newEntry +
                '}';
    }
}

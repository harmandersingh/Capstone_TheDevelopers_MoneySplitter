package com.example.capstone_thedevelopers_moneysplitter;

import java.io.Serializable;

public class UserData implements Serializable {
    private String userId;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private String currentTripId;
    private String currentTripTotalExpanse;


    public String getCurrentTripTotalExpanse() {
        return currentTripTotalExpanse;
    }

    public void setCurrentTripTotalExpanse(String currentTripTotalExpanse) {
        this.currentTripTotalExpanse = currentTripTotalExpanse;
    }

    public String getCurrentTripId() {

        return currentTripId;
    }

    public void setCurrentTripId(String currentTripId) {
        this.currentTripId = currentTripId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

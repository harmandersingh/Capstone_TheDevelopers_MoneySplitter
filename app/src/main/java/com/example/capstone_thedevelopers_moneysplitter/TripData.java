package com.example.capstone_thedevelopers_moneysplitter;

import java.util.ArrayList;

public class TripData {

    String tripId;
    String totalSpend;
    String destination;
    String Budget;
    String startDate;
    String endDate;
    ArrayList<UserData> members = new ArrayList<>();
    ArrayList<ExpanseData> expanseData = new ArrayList<>();


    public String getTotalSpend() {
        return totalSpend;
    }

    public void setTotalSpend(String totalSpend) {
        this.totalSpend = totalSpend;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getBudget() {
        return Budget;
    }

    public void setBudget(String budget) {
        Budget = budget;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public ArrayList<UserData> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<UserData> members) {
        this.members = members;
    }

    public ArrayList<ExpanseData> getExpanseData() {
        return expanseData;
    }

    public void setExpanseData(ArrayList<ExpanseData> expanseData) {
        this.expanseData = expanseData;
    }
}

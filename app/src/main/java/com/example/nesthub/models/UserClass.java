package com.example.nesthub.models;

public class UserClass {
    String fullName, number;

    public UserClass(String fullName, String number) {
        this.fullName = fullName;
        this.number = number;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

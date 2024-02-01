package com.example.nesthub.models;

public class UserClass {
    String fullName, number, email;  // Add the email field

    public UserClass(String fullName, String number, String email) {
        this.fullName = fullName;
        this.number = number;
        this.email = email;
    }

    // Getter and setter methods for all fields

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

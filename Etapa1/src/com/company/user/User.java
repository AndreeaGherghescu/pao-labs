package com.company.user;

import java.util.Scanner;

public class User {
    protected String name;
    protected String email;
    protected String phoneNumber;
    private String password;

    public User(){}

    public User(String name, String email, String phoneNumber, String password) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    @Override
    public String toString(){
        return "~~ User ~~\nName: " + this.name + "\nEmail: " + this.email + "\nPhone: " + this.phoneNumber + "\n";
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail(){
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}

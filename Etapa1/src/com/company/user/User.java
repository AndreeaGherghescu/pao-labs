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

    public void read(){
        Scanner var = new Scanner(System.in);
        System.out.print("Name: ");
        String name = var.nextLine();
        System.out.print("Email: ");
        String email = var.nextLine();
        String phoneNumber;

        while(true) {
            System.out.print("Phone number: ");
            phoneNumber = var.nextLine();
            boolean ok = phoneNumber.matches("0[0-9]{9}");
            if (ok)
                break;
            else
                System.out.println("Please give a valid phone number.");
        }
        System.out.print("Password: ");
        String password = var.nextLine();

        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password=password;
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

package com.company.product;

import java.util.Scanner;

public class Manual extends Book{
    private String subject;
    private int grade;

    public Manual() {}

    public Manual(String title, String author, double price, String subject, int grade) {
        super(title, author, price);
        this.subject = subject;
        this.grade = grade;
    }

    public String getSubject() {
        return subject;
    }

    public int getGrade() {
        return grade;
    }

    @Override
    public String toString () {
        return "~~ Manual ~~\nTitle: " + this.title + "\nAuthor: " + this.author + "\nPrice: " + this.price + "\nSubject: " + this.subject + "\nSuited for grade " + this.grade + "\n";
    }


}

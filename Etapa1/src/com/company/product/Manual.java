package com.company.product;

import java.util.Scanner;

public class Manual extends Book{
    private String subject;
    private int grade;

    public Manual() {}

    public Manual(String author, String title, double price, String subject, int grade) {
        super(author, title, price);
        this.subject = subject;
        this.grade = grade;
    }

    @Override
    public void read() {
        Scanner var = new Scanner(System.in);

        System.out.print("Title of the book is: ");
        String title = var.nextLine();

        System.out.print("Author of the book is: ");
        String author = var.nextLine();

        System.out.print("Price of the book is: ");
        double price = var.nextDouble();
        var.nextLine();

        System.out.print("Subject of the book is: ");
        String subject = var.nextLine();

        System.out.print("The book is suited for class: ");
        int grade = var.nextInt();

        this.title = title;
        this.author = author;
        this.price = price;
        this.subject = subject;
        this.grade = grade;
        System.out.println();
    }

    @Override
    public String toString () {
        return "~~ Manual ~~\nTitle: " + this.title + "\nAuthor: " + this.author + "\nPrice: " + this.price + "\nSubject: " + this.subject + "\nSuited for grade " + this.grade + "\n";
    }

    //getteri
}

package com.company.product;

import java.util.Scanner;

public class ChildBook extends Book{

    private int minAge;

    public ChildBook(){}

    public ChildBook(String title, String author, double price, int minAge){
        super(title, author, price);
        this.minAge = minAge;
    }

    @Override
    public void read(){
        Scanner var = new Scanner(System.in);

        System.out.print("Title of the book is: ");
        String title = var.nextLine();

        System.out.print("Author of the book is: ");
        String author = var.nextLine();

        System.out.print("Price of the book is: ");
        double price = var.nextDouble();

        System.out.print("Minimum age for this book is: ");
        int age = var.nextInt();
        var.nextLine();

        this.title = title;
        this.author = author;
        this.price = price;
        this.minAge = age;
        System.out.println();
    }

    @Override
    public String toString () {
        return "~~ Child book ~~\nTitle: " + this.title + "\nAuthor: " + this.author + "\nPrice: " + this.price + "\nMinimum age: " + this.minAge + "\n";
    }

}

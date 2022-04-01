package com.company.product;

import java.util.Scanner;

public class Novel extends Book{
    private int edition;
    private String genre;

    public Novel(){}

    public Novel(String titile, String author, double price, int edition, String genre){
        super(author, titile, price);
        this.edition = edition;
        this.genre = genre;
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

        System.out.print("Edition of the book is: ");
        int edition = var.nextInt();
        var.nextLine();

        System.out.print("Genre of the book is: ");
        String genre = var.nextLine();

        this.title = title;
        this.author = author;
        this.price = price;
        this.edition = edition;
        this.genre = genre;
        System.out.println();
    }

    @Override
    public String toString () {
        return "~~ Novel ~~\nTitle: " + this.title + "\nAuthor: " + this.author + "\nPrice: " + this.price + "\nEdition: " + this.edition + "\nGenre: " + this.genre + "\n";
    }

}

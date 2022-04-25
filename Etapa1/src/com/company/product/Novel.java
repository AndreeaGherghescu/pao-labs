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
    public String toString () {
        return "~~ Novel ~~\nTitle: " + this.title + "\nAuthor: " + this.author + "\nPrice: " + this.price + "\nEdition: " + this.edition + "\nGenre: " + this.genre + "\n";
    }

}

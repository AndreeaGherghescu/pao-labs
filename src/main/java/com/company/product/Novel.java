package com.company.product;

import java.util.Scanner;

public class Novel extends Book{
    private int edition;
    private String genre;

    public Novel(){}

    public Novel(String title, String author, double price, int edition, String genre){
        super(title, author, price);
        this.edition = edition;
        this.genre = genre;
    }

    public int getEdition() {
        return edition;
    }
    public String getGenre() {
        return genre;
    }

    @Override
    public String toString () {
        return "~~ Novel ~~\nTitle: " + this.title + "\nAuthor: " + this.author + "\nPrice: " + this.price + "\nEdition: " + this.edition + "\nGenre: " + this.genre + "\n";
    }

}

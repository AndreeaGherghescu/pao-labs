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
    public String toString () {
        return "~~ Child book ~~\nTitle: " + this.title + "\nAuthor: " + this.author + "\nPrice: " + this.price + "\nMinimum age: " + this.minAge + "\n";
    }

}

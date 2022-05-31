package com.company.product;

public abstract class Book {
    protected String author;
    protected String title;
    protected double price;

    public Book(){}

    public Book(String title, String author, double price) {
        this.author = author;
        this.title = title;
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public abstract String toString();
}

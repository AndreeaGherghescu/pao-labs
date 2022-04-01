package com.company.product;

public abstract class Book {
    protected String author; // poate o sa fac lista
    protected String title;
    protected double price;

    public Book(){}

    public Book(String author, String title, double price) {
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

    public abstract void read();

    @Override
    public abstract String toString();
}

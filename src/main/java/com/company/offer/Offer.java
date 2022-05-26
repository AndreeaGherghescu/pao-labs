package com.company.offer;

import com.company.product.Manual;
import com.company.service.BookService;

import java.util.ArrayList;
import java.util.List;

public abstract class Offer {
    protected String name;
    protected List<Integer> manuals;
    protected double price;
    protected BookService bookService;

    public Offer(){
        this.manuals = new ArrayList<Integer>();
        bookService = BookService.getInstance();
    }

    public Offer(String name, List<Integer> manuals) {
        this.name = name;
        this.manuals = manuals;
        bookService = BookService.getInstance();
    }

    @Override
    public abstract String toString();

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getManuals() {
        return manuals;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

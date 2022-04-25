package com.company.offer;

import com.company.product.Manual;

import java.util.ArrayList;
import java.util.List;

public abstract class Offer {
    protected String name;
    protected List<Manual> manuals;
    protected double price;

    public Offer(){
        this.manuals = new ArrayList<Manual>();
    }

    public Offer(String name, List<Manual> manuals) {
        this.name = name;
        this.manuals = manuals;
    }

    @Override
    public abstract String toString();

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public List<Manual> getManuals() {
        return manuals;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

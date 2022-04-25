package com.company.library;

import com.company.offer.BigOffer;
import com.company.offer.KidsOffer;
import com.company.offer.Offer;
import com.company.product.Book;
import com.company.product.ChildBook;
import com.company.product.Manual;
import com.company.product.Novel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class BigLibrary extends Library{
    private List<Novel> novels;
    private List<BigOffer> offers;
    private List<Manual> manuals;

    public BigLibrary() {
        this.novels = new ArrayList<Novel>();
        this.offers = new ArrayList<BigOffer>();
        this.manuals = new ArrayList<Manual>();
    }

    public BigLibrary(String name, double rating, HashMap<String, Integer> stock, List<Novel> novels, List<BigOffer> offers, List<Manual> manuals) {
        super(name, rating, stock);
        this.novels = novels;
        this.offers = offers;
        this.manuals = manuals;
    }


    @Override
    public String toString() {
        String output = "~~ Big library ~~\n" + "Name: " + this.name + "\nRating: " + this.rating + "\nList of novels:\n";

        for (Novel it : novels) {
            output += it + "Stock: " + this.stock.get(it.getTitle()) + "\n";
        }

        output += "List of manuals:\n";
        for (Manual it : manuals) {
            output += it + "Stock: " + this.stock.get(it.getTitle()) + "\n";
        }

        output += "List of offers:\n";
        for (BigOffer it : offers) {
            output += it + "Stock: " + this.stock.get(it.getName()) + "\n";
        }

        return output;
    }

    @Override
    public List<Offer> getOffers() {
        List<Offer> o = new ArrayList<Offer>();
        for (BigOffer it: offers){
            o.add(it);
        }
        return o;
    }

    @Override
    public List<Book> getBooks() {
        List<Book> b = new ArrayList<Book>();
        for (Novel it: novels){
            b.add(it);
        }
        for (Manual it: manuals){
            b.add(it);
        }
        return b;
    }

    public List<Manual> getManuals() {
        return this.manuals;
    }

    public List<Novel> getNovels() {
        return novels;
    }

    public void setNovels(List<Novel> novels) {
        this.novels = novels;
    }

    public void setOffers(List<BigOffer> offers) {
        this.offers = offers;
    }

    public void setManuals(List<Manual> manuals) {
        this.manuals = manuals;
    }

}

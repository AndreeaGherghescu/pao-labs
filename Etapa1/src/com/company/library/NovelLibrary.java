package com.company.library;

import com.company.offer.Offer;
import com.company.product.Book;
import com.company.product.Novel;

import java.util.*;

public class NovelLibrary extends Library{
    private List<Novel> novels;

    public NovelLibrary(){
        this.novels = new ArrayList<Novel>();
    }

    public NovelLibrary(String name, double rating, HashMap<String, Integer> stock, List<Novel> novels) {
        super(name, rating, stock);
        this.novels = novels;
    }

    @Override
    public String toString(){
        String output = "~~ Novel library ~~\n" + "Name: " + this.name + "\nRating: " + this.rating + "\nList of novels:\n";

        for (Novel it : novels) {
            output += it + "Stock: " + this.stock.get(it.getTitle()) + "\n";
        }

        return output;
    }

    @Override
    public List<Offer> getOffers(){
        return null;
    }

    @Override
    public List<Book> getBooks(){
        List<Book> b = new ArrayList<Book>();

        for (Novel it: novels){
            b.add(it);
        }
        return b;
    }

    public void setNovels(List<Novel> novels) {
        this.novels = novels;
    }

    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        if (!(obj instanceof NovelLibrary)) {
            return false;
        }
        NovelLibrary novelLibrary = (NovelLibrary) obj;
        return Objects.equals(novels, novelLibrary.novels);
    }

    public int hashCode(){
        return Objects.hash(novels);
    }

    public List<Novel> getNovels(){
        return novels;
    }
}



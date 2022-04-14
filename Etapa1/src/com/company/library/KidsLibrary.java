package com.company.library;

import com.company.offer.KidsOffer;
import com.company.offer.Offer;
import com.company.product.Book;
import com.company.product.ChildBook;
import com.company.product.Manual;

import java.util.*;

public class KidsLibrary extends Library{
    private List<KidsOffer> offers;
    private List<ChildBook> books;


    public KidsLibrary() {
        this.offers = new ArrayList<KidsOffer>();
        this.books = new ArrayList<ChildBook>();
    }

    public KidsLibrary(String name, double rating, HashMap<String, Integer> stock, List<KidsOffer> offers, List<ChildBook> books){
        super(name, rating, stock);
        this.books = books;
        this.offers = offers;
    }

    @Override
    public String toString(){
        String output = "~~ Kid's library ~~\n" + "Name: " + this.name + "\nRating: " + this.rating + "\nList of books:\n";

        for (ChildBook it : books) {
            output += it + "Stock: " + this.stock.get(it.getTitle()) + "\n";
        }

        output += "List of offers:\n";
        for (KidsOffer it : offers) {
            output += it + "Stock: " + this.stock.get(it.getName()) + "\n";
        }
        return output;
    }

    @Override
    public List<Book> getBooks(){
        List<Book> b = new ArrayList<Book>();
        for (ChildBook it: books){
            b.add(it);
        }
        return b;
    }

    public List<ChildBook> getChildBooks(){
        return books;
    }

    public void setBooks(List<ChildBook> books) {
        this.books = books;
    }

    public List<Offer> getOffers(){
        List<Offer> o = new ArrayList<Offer>();
        for (KidsOffer it: offers){
            o.add(it);
        }
        return o;
    }

    public void setOffers(List<KidsOffer> offers) {
        this.offers = offers;
    }

    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        if (!(obj instanceof KidsLibrary)) {
            return false;
        }
        KidsLibrary kidsLibrary = (KidsLibrary) obj;
        return Objects.equals(books, kidsLibrary.books);
    }

    public int hashCode(){
        return Objects.hash(books);
    }

}

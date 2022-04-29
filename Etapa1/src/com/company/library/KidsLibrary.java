package com.company.library;

import java.util.*;

public class KidsLibrary extends Library{
    private List<Integer> offers;
    private List<Integer> books;


    public KidsLibrary() {
        this.offers = new ArrayList<Integer>();
        this.books = new ArrayList<Integer>();
    }

    public KidsLibrary(String name, double rating, HashMap<Integer, Integer> stock, List<Integer> offers, List<Integer> books){
        super(name, rating, stock);
        this.books = books;
        this.offers = offers;
    }

    @Override
    public String toString(){
        StringBuilder output = new StringBuilder("~~ Kid's library ~~\n" + "Name: " + this.name + "\nRating: " + this.rating + "\nList of books:\n");

        for (Integer it : books) {
            output.append(bookService.getBookById(it)).append("Stock: ").append(this.stock.get(it)).append("\n");
        }

        output.append("List of offers:\n");
        for (Integer it : offers) {
            output.append(offerService.getOfferById(it)).append("\n");
        }
        return output.toString();
    }

    @Override
    public List<Integer> getBooks(){
        List<Integer> b = new ArrayList<Integer>();
        for (Integer it: books){
            b.add(it);
        }
        return b;
    }

    public List<Integer> getChildBooks(){
        return books;
    }

    public void setBooks(List<Integer> books) {
        this.books = books;
    }

    public List<Integer> getOffers(){
        List<Integer> o = new ArrayList<Integer>();
        for (Integer it: offers){
            o.add(it);
        }
        return o;
    }

    public void setOffers(List<Integer> offers) {
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

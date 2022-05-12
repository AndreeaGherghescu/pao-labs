package com.company.library;

import java.util.*;

public class BigLibrary extends Library{
    private List<Integer> novels;
    private List<Integer> offers;
    private List<Integer> manuals;


    public BigLibrary() {
        this.novels = new ArrayList<Integer>();
        this.offers = new ArrayList<Integer>();
        this.manuals = new ArrayList<Integer>();
    }

    public BigLibrary(String name, double rating, Map<Integer, Integer> stock, List<Integer> novels, List<Integer> offers, List<Integer> manuals) {
        super(name, rating, stock);
        this.novels = novels;
        this.offers = offers;
        this.manuals = manuals;
    }

    @Override
    public String toString() {

        StringBuilder output = new StringBuilder("~~ Big library ~~\n" + "Name: " + this.name + "\nRating: " + this.rating + "\nList of novels:\n");

        for (Integer it : novels) {
            output.append(bookService.getBookById(it)).append("Stock: ").append(this.stock.get(it)).append("\n");
        }

        output.append("List of manuals:\n");
        for (Integer it : manuals) {
            output.append(bookService.getBookById(it)).append("Stock: ").append(this.stock.get(it)).append("\n");
        }

        output.append("List of offers:\n");
        for (Integer it : offers) {
            output.append(offerService.getOfferById(it)).append("\n");
        }

        return output.toString();
    }

    @Override
    public List<Integer> getOffers() {
        List<Integer> o = new ArrayList<Integer>();
        for (Integer it: offers){
            o.add(it);
        }
        return o;
    }

    @Override
    public List<Integer> getBooks() {
        List<Integer> b = new ArrayList<Integer>();
        for (Integer it: novels){
            b.add(it);
        }
        for (Integer it: manuals){
            b.add(it);
        }
        return b;
    }

    public List<Integer> getManuals() {
        return this.manuals;
    }

    public List<Integer> getNovels() {
        return novels;
    }

    public void setNovels(List<Integer> novels) {
        this.novels = novels;
    }

    public void setOffers(List<Integer> offers) {
        this.offers = offers;
    }

    public void setManuals(List<Integer> manuals) {
        this.manuals = manuals;
    }

}

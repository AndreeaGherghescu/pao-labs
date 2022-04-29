package com.company.library;

import com.company.offer.Offer;
import com.company.product.Book;
import com.company.product.Novel;

import java.util.*;

public class NovelLibrary extends Library{
    private List<Integer> novels;

    public NovelLibrary(){
        this.novels = new ArrayList<Integer>();
    }

    public NovelLibrary(String name, double rating, HashMap<Integer, Integer> stock, List<Integer> novels) {
        super(name, rating, stock);
        this.novels = novels;
    }

    @Override
    public String toString(){
        StringBuilder output = new StringBuilder("~~ Novel library ~~\n" + "Name: " + this.name + "\nRating: " + this.rating + "\nList of novels:\n");

        for (Integer it : novels) {
            output.append(bookService.getBookById(it)).append("Stock: ").append(this.stock.get(it)).append("\n");
        }

        return output.toString();
    }

    @Override
    public List<Integer> getOffers(){
        return null;
    }

    @Override
    public List<Integer> getBooks(){
        List<Integer> b = new ArrayList<Integer>();

        for (Integer it: novels){
            b.add(it);
        }
        return b;
    }

    public void setNovels(List<Integer> novels) {
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

    public List<Integer> getNovels(){
        return novels;
    }
}



package com.company.library;

import com.company.offer.Offer;
import com.company.product.Book;

import java.util.*;

public abstract class Library {
    protected String name;
    protected double rating;
    HashMap<String, Integer> stock;

    public Library(){
        this.stock = new HashMap<String, Integer>();
    }

    public Library(String name, double rating, HashMap<String, Integer> stock) {
        this.name = name;
        this.rating = rating;
        this.stock = stock;
    }

    public void addRating(){
        Scanner var = new Scanner(System.in);

        System.out.print("Do you want to rate " + this.name + "? (yes/no): ");
        String response = var.nextLine();
        if(response.equalsIgnoreCase("yes"))
        {
            System.out.print("Give us x points out of 5: ");
            int points = var.nextInt();
            this.rating = (this.rating + points)/2;
            System.out.println("Thank you :)\n");
        }
    }

    public String getName() {
        return this.name;
    }

    public abstract void read();

    @Override
    public abstract String toString();
    public abstract List<Offer> getOffers();
    public abstract List<Book> getBooks();
    public double getRating() {
        return rating;
    }

    public void updateStock(String bookName, int stock) {
        this.stock.put(bookName, stock);
    }

    public void removeFromStock(String bookName){
        for (Map.Entry<String, Integer> stringIntegerEntry : this.stock.entrySet()) {
            if (((String) ((Map.Entry) stringIntegerEntry).getKey()).equalsIgnoreCase(bookName)) {
                this.stock.remove(bookName);
                break;
            }
        }
    }

    public void lowerStock(String name) {
        stock.replace(name, stock.get(name) - 1);
    }

    protected void setRating(int points){
            this.rating = (this.rating + points) / 2;
    }
}

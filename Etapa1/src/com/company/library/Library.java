package com.company.library;

import com.company.offer.Offer;
import com.company.product.Book;
import com.company.service.BookService;
import com.company.service.OfferService;

import java.util.*;

public abstract class Library {
    protected String name;
    protected double rating;
    HashMap<Integer, Integer> stock;
    protected OfferService offerService;
    protected BookService bookService;

    public Library(){
        this.stock = new HashMap<Integer, Integer>();
    }

    public Library(String name, double rating, HashMap<Integer, Integer> stock) {
        this.name = name;
        this.rating = rating;
        this.stock = stock;

        offerService = OfferService.getInstance();
        bookService = BookService.getInstance();
    }

    public void addRating(){
        Scanner var = new Scanner(System.in);

        System.out.print("Do you want to rate " + this.name + "? (yes/no): ");
        String response;
        while (true) {
            try {
                response = var.nextLine();
                if (!response.equalsIgnoreCase("no") && !response.equalsIgnoreCase("yes")) {
                    throw new Exception("Invalid answer.");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.print("This is not a valid answer. Please try again: ");
            }
        }

        if (response.equalsIgnoreCase("yes")) {
            System.out.print("Give us x points out of 5: ");
            int points;
            while (true) {
                try {
                    points = var.nextInt();
                    if (points >= 0 && points <= 5) {
                        break;
                    } else {
                        throw new Exception("Invalid number.");
                    }
                } catch (InputMismatchException e) {
                    var.next();
                    System.out.print("Please insert a valid number: ");
                } catch (Exception e) {
                    System.out.print("Please insert a valid number: ");
                }
            }
            this.rating = (this.rating + points) / 2;
            System.out.println("Thank you :)\n");
        }

    }

    public String getName() {
        return this.name;
    }

    @Override
    public abstract String toString();
    public abstract List<Integer> getOffers();
    public abstract List<Integer> getBooks();
    public double getRating() {
        return rating;
    }
    public void setRating(float points){
        this.rating = (this.rating + points) / 2;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStock(HashMap<Integer, Integer> stock) {
        this.stock = stock;
    }

    public void updateStock(Integer id, int stock) {
        this.stock.put(id, stock);
    }

    public void removeFromStock(Integer id){
        for (Map.Entry<Integer, Integer> integerIntegerEntry : this.stock.entrySet()) {
            if (((Integer) ((Map.Entry) integerIntegerEntry).getKey()) == id) {
                this.stock.remove(id);
                break;
            }
        }
    }

    public void lowerStock(Integer id) {
        stock.replace(id, stock.get(id) - 1);
    }

}

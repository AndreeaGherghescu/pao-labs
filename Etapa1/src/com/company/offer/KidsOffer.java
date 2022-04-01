package com.company.offer;

import com.company.product.ChildBook;
import com.company.product.Manual;

import java.util.List;
import java.util.Scanner;

public class KidsOffer extends Offer {
    private ChildBook book;
    private String toy; // gratis

    public KidsOffer() {}

    public KidsOffer(String name, List<Manual> manuals, ChildBook book, String toy) {
        super(name, manuals);
        this.book = book;
        this.toy = toy;

        // calculez pretul
        double totalPrice = 0;
        for (Manual it: manuals){
            totalPrice += it.getPrice();
        }

        totalPrice += book.getPrice();
        this.price = totalPrice;
    }

    public String getToy(){
        return toy;
    }

    public ChildBook getChildBook() {
        return book;
    }

    @Override
    public void read(){
        Scanner var = new Scanner(System.in);

        System.out.print("Offer's name: ");
        this.name = var.nextLine();

        System.out.print("Offer's toy (puppet/car/ball): ");

        while(true) {
            this.toy = var.nextLine();
            if (!(toy.equalsIgnoreCase("puppet") || toy.equalsIgnoreCase("car") || toy.equalsIgnoreCase("ball"))) {
                System.out.println("This toy doesn't exist. Please try again: ");
            } else {
                break;
            }
        }

        //System.out.println("-> offer's list of manuals: ");
        System.out.print("How many manuals does the offer have? ");

        int n = var.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.println("Introduce manual number " + (i + 1) + ": ");
            Manual manual = new Manual();
            manual.read();
            this.manuals.add(manual);
        }

        System.out.println("Book: ");
        ChildBook book = new ChildBook();
        book.read();
        this.book = book;

        double totalPrice = 0;
        for (Manual it: manuals) {
            totalPrice += it.getPrice();
        }

        totalPrice += book.getPrice();
        this.price = totalPrice;
    }

    @Override
    public String toString(){
        String output =  "~~ Kid's offer ~~\nName: " + this.getName() + "\nBook:\n" + this.book + "\nToy: " + this.toy
                + "\nManuals options:\n";

        for (Manual it : manuals) {
            output += it;
        }

        output += "Price: " + this.price + "\n";
        return output;
    }

}

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

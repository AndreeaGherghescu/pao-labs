package com.company.offer;

import com.company.product.ChildBook;
import com.company.product.Manual;
import com.company.service.BookService;

import java.util.List;
import java.util.Scanner;

public class KidsOffer extends Offer {
    private Integer book;

    private String toy; // gratis

    public KidsOffer() {}

    public KidsOffer(String name, List<Integer> manuals, Integer book, String toy) {
        super(name, manuals);
        this.book = book;
        this.toy = toy;

        // calculez pretul
        double totalPrice = 0;
        for (Integer it: manuals){
            totalPrice += bookService.getPriceById(it);
        }

        totalPrice += bookService.getPriceById(book);
        this.price = totalPrice;
    }

    public String getToy(){
        return toy;
    }

    public Integer getChildBook() {
        return book;
    }

    @Override
    public String toString(){
        StringBuilder output = new StringBuilder("~~ Kid's offer ~~\nName: " + this.getName() + "\nBook:\n" + bookService.getBookById(this.book) + "\nToy: " + this.toy
                + "\nManuals options:\n");

        for (Integer it : manuals) {
            output.append(bookService.getBookById(it));
        }

        output.append("Price: ").append(this.price).append("\n");
        return output.toString();
    }

}

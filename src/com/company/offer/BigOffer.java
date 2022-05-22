package com.company.offer;

import com.company.product.Manual;
import com.company.product.Novel;
import com.company.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BigOffer extends Offer{
    private List<Integer> novels;

    public BigOffer() {
        this.novels = new ArrayList<Integer>();
        this.manuals = new ArrayList<Integer>();
    }

    public BigOffer(String name, List<Integer> manuals, List<Integer> novels) {

        super(name, manuals);
        this.novels = novels;

        this.bookService = BookService.getInstance();
        //calcul pret
        double totalPrice = 0;
        for (Integer it: manuals){
            totalPrice += bookService.getPriceById(it);
        }
        for (Integer it: novels){
            totalPrice += bookService.getPriceById(it);
        }

        this.price = totalPrice;
    }

    public List<Integer> getNovels() {
        return novels;
    }

    @Override
    public String toString(){
        StringBuilder output = new StringBuilder("~~ Big offer ~~\nName: " + this.getName() + "\nManuals options:\n");

        for (Integer it : manuals) {
            output.append(bookService.getBookById(it));
        }

        output.append("\nNovels options:\n");
        for (Integer it : novels) {
            output.append(bookService.getBookById(it));
        }

        output.append("Price: ").append(this.price).append("\n");
        return output.toString();
    }



}

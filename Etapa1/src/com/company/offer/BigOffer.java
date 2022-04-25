package com.company.offer;

import com.company.product.Manual;
import com.company.product.Novel;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BigOffer extends Offer{
    private List<Novel> novels;

    public BigOffer() {
        this.novels = new ArrayList<Novel>();
    }

    public BigOffer(String name, List<Manual> manuals, List<Novel> novels) {
        super(name, manuals);
        this.novels = novels;

        //calcul pret
        double totalPrice = 0;
        for (Manual it: manuals){
            totalPrice += it.getPrice();
        }
        for (Novel it: novels){
            totalPrice += it.getPrice();
        }

        this.price = totalPrice;
    }

    public List<Novel> getNovels() {
        return novels;
    }

    @Override
    public String toString(){
        String output =  "~~ Big offer ~~\nName: " + this.getName() + "\nManuals options:\n";

        for (Manual it : manuals) {
            output += it;
        }

        output += "\nNovels options:\n";
        for (Novel it : novels) {
            output += it;
        }

        output += "Price: " + this.price + "\n";
        return output;
    }



}

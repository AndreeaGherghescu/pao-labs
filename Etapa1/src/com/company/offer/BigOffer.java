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
    public void read(){
        Scanner var = new Scanner(System.in);

        System.out.print("Offer's name: ");
        this.name = var.nextLine();

        //System.out.println("-> offer's list of manuals: ");
        System.out.print("How many manuals does the offer have? ");

        int n = var.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.println("Introduce manual number " + (i + 1) + ": ");
            Manual manual = new Manual();
            manual.read();
            this.manuals.add(manual);
        }

        //System.out.println("-> offer's list of novels: ");
        System.out.print("How many novels does the offer have? ");

        n = var.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.println("Introduce novel number " + (i + 1) + ": ");
            Novel novel = new Novel();
            novel.read();
            this.novels.add(novel);
        }

        double totalPrice = 0;
        for (Manual it: manuals) {
            totalPrice += it.getPrice();
        }
        for (Novel it: novels) {
            totalPrice += it.getPrice();
        }

        this.price = totalPrice;
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

package com.company.library;

import com.company.offer.BigOffer;
import com.company.offer.KidsOffer;
import com.company.offer.Offer;
import com.company.product.Book;
import com.company.product.ChildBook;
import com.company.product.Manual;
import com.company.product.Novel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class BigLibrary extends Library{
    private List<Novel> novels;
    private List<BigOffer> offers;
    private List<Manual> manuals;

    public BigLibrary() {
        this.novels = new ArrayList<Novel>();
        this.offers = new ArrayList<BigOffer>();
        this.manuals = new ArrayList<Manual>();
    }

    public BigLibrary(String name, double rating, HashMap<String, Integer> stock, List<Novel> novels, List<BigOffer> offers, List<Manual> manuals) {
        super(name, rating, stock);
        this.novels = novels;
        this.offers = offers;
        this.manuals = manuals;
    }

    @Override
    public void read(){
        Scanner var = new Scanner(System.in);

        System.out.print("Library's name: ");
        this.name = var.nextLine();

        //System.out.println("Library's list of novels: ");
        System.out.print("How many novels does the library have? ");
        int n = var.nextInt();

        for (int i = 0; i < n; i++) {
            Novel novel = new Novel();
            novel.read();
            novels.add(novel);

            System.out.print("Introduce the stock of <<" + novel.getTitle() + ">> : ");
            int quantity = var.nextInt();
            stock.put(novel.getTitle(), quantity);
        }

        //System.out.println("-> Library's list of offers: ");
        System.out.print("How many offers does the library have? ");
        n = var.nextInt();

        for (int i = 0; i < n; i++) {
            BigOffer offer = new BigOffer();
            offer.read();
            offers.add(offer);

            System.out.print("Introduce the stock of <<" + offer.getName() + ">> : ");
            int quantity = var.nextInt();
            stock.put(offer.getName(), quantity);
        }

        //System.out.println("->Library's list of manuals: ");
        System.out.print("How many manuals does the library have? ");
        n = var.nextInt();
        for (int i = 0; i < n; i++){
            Manual manual = new Manual();
            manual.read();
            manuals.add(manual);

            System.out.print("Introduce the stock of <<" + manual.getTitle() + ">> : ");
            int quantity = var.nextInt();
            stock.put(manual.getTitle(), quantity);
        }
    }

    @Override
    public String toString() {
        String output = "~~ Big library ~~\n" + "Name: " + this.name + "\nRating: " + this.rating + "\nList of novels:\n";

        for (Novel it : novels) {
            output += it + "Stock: " + this.stock.get(it.getTitle()) + "\n";
        }

        output += "List of manuals:\n";
        for (Manual it : manuals) {
            output += it + "Stock: " + this.stock.get(it.getTitle()) + "\n";
        }

        output += "List of offers:\n";
        for (BigOffer it : offers) {
            output += it + "Stock: " + this.stock.get(it.getName()) + "\n";
        }

        return output;
    }

    @Override
    public List<Offer> getOffers() {
        List<Offer> o = new ArrayList<Offer>();
        for (BigOffer it: offers){
            o.add(it);
        }
        return o;
    }

    @Override
    public List<Book> getBooks() {
        List<Book> b = new ArrayList<Book>();
        for (Novel it: novels){
            b.add(it);
        }
        for (Manual it: manuals){
            b.add(it);
        }
        return b;
    }

    public BigOffer orderBigOffer(BigOffer choice) {
        // BigOffer are romane si manuale

        Scanner var = new Scanner(System.in);
        System.out.println("List of novels to choose from: ");

        int cnt = 1;
        for (Novel it: choice.getNovels()){
            System.out.println("Novel number " + cnt + ":");
            cnt ++;
            System.out.println(it);
        }
        System.out.print("Choose a novel number: ");
        int option = var.nextInt() - 1;
        List<Novel> n = new ArrayList<Novel>();
        n.add(choice.getNovels().get(option));

        System.out.println("List of manuals to choose from: ");

        cnt = 1;
        for (Manual it: choice.getManuals()){
            System.out.println("Manual number " + cnt + ":");
            cnt ++;
            System.out.println(it);
        }
        System.out.print("Choose a manual number: ");
        option = var.nextInt() - 1;
        var.nextLine();
        List<Manual> m = new ArrayList<Manual>();
        m.add(choice.getManuals().get(option));

        return new BigOffer(choice.getName(), m, n);
    }

    public void addNovel(Novel novel) {
        novels.add(novel);
    }

    public void addManual(Manual manual){
        manuals.add(manual);
    }


    public void removeNovel(Novel novel) {
        for (Novel it: novels){
            if(it.equals(novel)){
                novels.remove(novel);
                break;
            }
        }
    }

    public void removeManual(Manual manual) {
        for (Manual it: manuals){
            if(it.equals(manual)){
                manuals.remove(manual);
                break;
            }
        }
    }

    public void addOffer(BigOffer offer) {
        offers.add(offer);
    }
}

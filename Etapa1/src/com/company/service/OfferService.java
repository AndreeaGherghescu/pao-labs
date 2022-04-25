package com.company.service;

import com.company.offer.BigOffer;
import com.company.offer.KidsOffer;
import com.company.product.ChildBook;
import com.company.product.Manual;
import com.company.product.Novel;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OfferService {
    private static OfferService single_instance = null;
    private BookService bookService;

    public static OfferService getInstance()
    {
        if (single_instance == null)
            single_instance = new OfferService();

        return single_instance;
    }

    public BigOffer readBigOffer() {
        Scanner var = new Scanner(System.in);
        bookService = BookService.getInstance();

        System.out.print("Offer's name: ");
        String name = var.nextLine();

        //System.out.println("-> offer's list of manuals: ");
        System.out.print("How many manuals does the offer have? ");

        int n = var.nextInt();
        List<Manual> manuals = new ArrayList<Manual>();

        for (int i = 0; i < n; i++) {
            System.out.println("Introduce manual number " + (i + 1) + ": ");
            Manual manual = bookService.readManual();
            manuals.add(manual);
        }

        //System.out.println("-> offer's list of novels: ");
        System.out.print("How many novels does the offer have? ");

        n = var.nextInt();
        List<Novel> novels = new ArrayList<Novel>();

        for (int i = 0; i < n; i++) {
            System.out.println("Introduce novel number " + (i + 1) + ": ");
            Novel novel = bookService.readNovel();
            novels.add(novel);
        }

        double totalPrice = 0;
        for (Manual man: manuals) {
            totalPrice += man.getPrice();
        }
        for (Novel nov: novels) {
            totalPrice += nov.getPrice();
        }

        BigOffer offer = new BigOffer(name, manuals, novels);
        offer.setPrice(totalPrice);

        return offer;
    }

    public KidsOffer readKidsOffer() {
        Scanner var = new Scanner(System.in);
        bookService = BookService.getInstance();

        System.out.print("Offer's name: ");
        String name = var.nextLine();

        System.out.print("Offer's toy (puppet/car/ball): ");

        String toy;
        while(true) {
            toy = var.nextLine();
            if (!(toy.equalsIgnoreCase("puppet") || toy.equalsIgnoreCase("car") || toy.equalsIgnoreCase("ball"))) {
                System.out.println("This toy doesn't exist. Please try again: ");
            } else {
                break;
            }
        }

        //System.out.println("-> offer's list of manuals: ");
        System.out.print("How many manuals does the offer have? ");

        int n = var.nextInt();
        List<Manual> manuals = new ArrayList<Manual>();

        for (int i = 0; i < n; i++) {
            System.out.println("Introduce manual number " + (i + 1) + ": ");
            Manual manual = bookService.readManual();
            manuals.add(manual);
        }

        System.out.println("Book: ");
        ChildBook book = bookService.readChildBook();

        double totalPrice = 0;
        for (Manual man: manuals) {
            totalPrice += man.getPrice();
        }

        totalPrice += book.getPrice();

        KidsOffer offer = new KidsOffer(name, manuals, book, toy);
        offer.setPrice(totalPrice);

        return offer;

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

    public KidsOffer orderKidsOffer (KidsOffer choice){
        Scanner var = new Scanner(System.in);

        System.out.println("List of manuals to choose from: ");

        int cnt = 1;
        for (Manual it: choice.getManuals()){
            System.out.println("Manual number " + cnt + ":");
            cnt ++;
            System.out.println(it);
        }
        System.out.println("Choose a manual number: ");
        int option = var.nextInt() - 1;
        var.nextLine();
        List<Manual> m = new ArrayList<Manual>();
        m.add(choice.getManuals().get(option));

        return new KidsOffer(choice.getName(), m, choice.getChildBook(), choice.getToy());
    }
}

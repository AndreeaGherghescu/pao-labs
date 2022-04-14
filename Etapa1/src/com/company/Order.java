package com.company;

import com.company.library.BigLibrary;
import com.company.library.KidsLibrary;
import com.company.library.Library;
import com.company.library.NovelLibrary;
import com.company.offer.BigOffer;
import com.company.offer.KidsOffer;
import com.company.offer.Offer;
import com.company.product.Book;
import com.company.service.OfferService;
import com.company.user.User;

import java.util.*;

public class Order {
    private User customer;
    private Library shop;
    private double price;
    private List<Offer> offers;
    private List<Book> books;
    private String address;
    private OfferService offerService;

    public Order() {
        this.offers = new ArrayList<Offer>();
        this.books = new ArrayList<Book>();
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public void setShop(Library shop) {
        this.shop = shop;
    }

    public void setPrice() {
        double totalPrice = 0;

        for (Offer it: offers) {
            totalPrice += it.getPrice();
        }
        for (Book it: books){
            totalPrice += it.getPrice();
        }
        this.price = totalPrice;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        String output = "" + this.customer + "\nPrice: " + this.getPrice() + "\nDelivery address: " +
                this.address + "\nLibrary: " + this.shop.getName() + "\nOffers you chose:\n";

        for (Offer it : offers) {
            output += it;
        }
        output += "\nBooks you chose:\n";
        for (Book it : books){
            output += it;
        }
        return output;
    }

    public void addOffer(Offer offer){
        this.offers.add(offer);
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public void reader(HashMap<Integer, Library> shops) {
        Scanner var = new Scanner(System.in);
        offerService = OfferService.getInstance();

        System.out.print("Delivery address: ");
        this.address = var.nextLine();

        System.out.println("List of libraries: ");
        if (shops.isEmpty()) {
            System.out.println("No libraries found.");
        } else {
            Set set = shops.entrySet();
            Iterator it = set.iterator();

            while (it.hasNext()){
                Map.Entry entry = (Map.Entry)it.next();
                System.out.println(((Library)entry.getValue()).getName());
            }
            System.out.print("Choose one library: ");
            String name = var.nextLine();

            int libraryType = 0;
            it = set.iterator();
            while(it.hasNext()){
                Map.Entry entry = (Map.Entry)it.next();
                if (((Library)entry.getValue()).getName().equalsIgnoreCase(name)){
                    if (entry.getValue() instanceof BigLibrary){
                        this.shop = (BigLibrary) entry.getValue();
                        libraryType = 1;
                    } else if (entry.getValue() instanceof KidsLibrary){
                        this.shop = (KidsLibrary) entry.getValue();
                        libraryType = 2;
                    } else if (entry.getValue() instanceof NovelLibrary){
                        this.shop = (NovelLibrary) entry.getValue();
                        libraryType = 3;
                    }
                    break;
                }
            }

            System.out.println("The books are: ");
            List<Book> books = this.shop.getBooks();
            for (int i = 0; i < books.size(); i++){
                System.out.println("Book number " + (i + 1) + " is: ");
                System.out.println(books.get(i));
            }

            String option;

            if (libraryType == 3) {
                System.out.println("This library doesn't have offers.");
            } else {
                System.out.println("The offers are: ");
                List<Offer> offers = this.shop.getOffers();
                for (int i = 0; i < offers.size(); i++) {
                    System.out.println("Offer number " + (i + 1) + " is: ");
                    System.out.println(offers.get(i));
                }


                System.out.print("Do you want to add an offer to your cart? (yes/no): ");
                option = var.nextLine();

                if (option.equalsIgnoreCase("yes")) {

                    System.out.print("Choose offer number: ");
                    int choice = var.nextInt() - 1;

                    Offer offer;

                    if (libraryType == 1) {
                        offer = offerService.orderBigOffer((BigOffer) offers.get(choice));
                        this.addOffer(offer);
                        this.shop.lowerStock(offer.getName());
                    } else if (libraryType == 2) {
                        offer = offerService.orderKidsOffer((KidsOffer) offers.get(choice));
                        this.addOffer(offer);
                        this.shop.lowerStock(offer.getName());
                    } else {
                        System.out.println("This library doesn't have offers.");
                    }
                }
            }

            var.nextLine();
            System.out.print("Do you want to add a book? (yes/no): ");
            option = var.nextLine();
            if (option.equalsIgnoreCase("yes")) {
                while (true){
                    System.out.print("Choose book number: ");
                    int choice = var.nextInt() - 1;
                    this.addBook(books.get(choice));

                    this.shop.lowerStock((books.get(choice)).getTitle());

                    var.nextLine();
                    System.out.print("Do you want to add another? (yes/no): ");
                    option = var.nextLine();
                    if (option.equalsIgnoreCase("no")){
                        break;
                    }
                }
            }
        }
    }
}

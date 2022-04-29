package com.company;

import com.company.library.BigLibrary;
import com.company.library.KidsLibrary;
import com.company.library.Library;
import com.company.library.NovelLibrary;
import com.company.offer.BigOffer;
import com.company.offer.KidsOffer;
import com.company.offer.Offer;
import com.company.product.Book;
import com.company.service.BookService;
import com.company.service.OfferService;
import com.company.user.User;

import java.sql.SQLOutput;
import java.util.*;
import java.util.stream.Collectors;

public class Order {
    private User customer;
    private Library shop;
    private double price;
    private List<Offer> offers;
    private List<Integer> books;
    private String address;
    private OfferService offerService;
    private BookService bookService;

    public Order() {
        this.offers = new ArrayList<Offer>();
        this.books = new ArrayList<Integer>();
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public void setShop(Library shop) {
        this.shop = shop;
    }

    public void setPrice() {
        double totalPrice = 0;
        offerService = OfferService.getInstance();
        bookService = BookService.getInstance();

//        for (Integer it: offers) {
//            totalPrice += offerService.getPriceById(it);
//        }

        // stream + lambda
        List<Double> allOffers = offers.stream().
                map(o -> o.getPrice()).
                collect(Collectors.toList());

        // TODO sa calculez pretul unei selectii de oferta

        totalPrice += allOffers.stream().reduce(0., Double::sum);

        List<Double> allBooks = books.stream().
                map(b -> bookService.getPriceById(b)).
                collect(Collectors.toList());

        totalPrice += allBooks.stream().reduce(0., Double::sum);

//        for (Integer it: books){
//            totalPrice += bookService.getPriceById(it);
//        }
        this.price = totalPrice;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("" + this.customer + "\nPrice: " + this.getPrice() + "\nDelivery address: " +
                this.address + "\nLibrary: " + this.shop.getName() + "\nOffers you chose:\n");

        for (Offer it : offers) {
            output.append(it);
        }
        output.append("\nBooks you chose:\n");
        for (Integer it : books){
            output.append(bookService.getBookById(it));
        }
        return output.toString();
    }

    public void addOffer(Offer offer){
        this.offers.add(offer);
    }

    public void addBook(Integer book) {
        this.books.add(book);
    }

    public void reader(HashMap<Integer, Library> shops) {
        Scanner var = new Scanner(System.in);
        offerService = OfferService.getInstance();
        bookService = BookService.getInstance();

        System.out.print("Delivery address: ");
        this.address = var.nextLine();

        System.out.println("List of libraries: ");
        if (shops.isEmpty()) {
            System.out.println("No libraries found.");
        } else {
            Set set = shops.entrySet();
            Iterator it = set.iterator();
//
//            while (it.hasNext()){
//                Map.Entry entry = (Map.Entry)it.next();
//                System.out.println(((Library)entry.getValue()).getName());
//            }

            //streams + lambda
            set.stream().forEach(e -> System.out.println(((Library)((Map.Entry)e).getValue()).getName()));

            System.out.print("Choose one library: ");
            String name;

            int libraryType = 0;
            boolean flag = false;
            while (true) {
                try {
                    flag = false;
                    name = var.nextLine();
                    it = set.iterator();
                    while (it.hasNext()) {
                        Map.Entry entry = (Map.Entry) it.next();
                        if (((Library) entry.getValue()).getName().equalsIgnoreCase(name)) {
                            flag = true;
                            if (entry.getValue() instanceof BigLibrary) {
                                this.shop = (BigLibrary) entry.getValue();
                                libraryType = 1;
                            } else if (entry.getValue() instanceof KidsLibrary) {
                                this.shop = (KidsLibrary) entry.getValue();
                                libraryType = 2;
                            } else if (entry.getValue() instanceof NovelLibrary) {
                                this.shop = (NovelLibrary) entry.getValue();
                                libraryType = 3;
                            }
                            break;
                        }
                    }
                    if (!flag) {
                        throw new Exception("Invalid name");
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    System.out.print("This library does not exist. Please try again: ");
                }
            }

            System.out.println("The books are: ");
            List<Integer> books = this.shop.getBooks();
            for (int i = 0; i < books.size(); i++){
                System.out.println("Book number " + (i + 1) + " is: ");
                System.out.println(books.get(i));
                Book book = bookService.getBookById(books.get(i));
                System.out.println(bookService.getBookById(books.get(i)));
            }

            String option;

            if (libraryType == 3) {
                System.out.println("This library doesn't have offers.");
            } else {
                System.out.println("The offers are: ");
                List<Integer> allOffers = this.shop.getOffers();
                for (int i = 0; i < allOffers.size(); i++) {
                    System.out.println("Offer number " + (i + 1) + " is: ");
                    System.out.println(offerService.getOfferById(allOffers.get(i)));
                }

                System.out.print("Do you want to add an offer to your cart? (yes/no): ");
                option = var.nextLine();

                if (option.equalsIgnoreCase("yes")) {

                    System.out.print("Choose offer number: ");

                    int choice;
                    while (true) {
                        try {
                            choice = var.nextInt() - 1;
                            int id = allOffers.get(choice);
                            if (allOffers.contains(id)) {
                                //offers.add(id);
                                Offer offer;
                                if (libraryType == 1) {
                                    offer = offerService.orderBigOffer((BigOffer) offerService.getOfferById(id));
                                    this.addOffer(offer);
                                    break;
                                    //this.shop.lowerStock(offers.get(choice - 1));
                                } else {
                                    offer = offerService.orderKidsOffer((KidsOffer) offerService.getOfferById(id));
                                    this.addOffer(offer);
                                    break;
                                    //this.shop.lowerStock(choice);
                                }
                            } else {
                                throw new ArithmeticException("ID not in list");
                            }
                        } catch (InputMismatchException e) {
                            var.next();
                            System.out.print("Please insert a number: ");
                        } catch (Exception e) {
                            System.out.print("The offer you want to add is not a child offer. Please try again: ");
                        }

                    }

                    // o oferta are mai multe optiuni de carti
                    // in offer retin toate optiunile de carti
                    // in this.offers trebuie sa retin ce carti a ales userul
                    // deci nu mai am id uri, ci doar obiecte unice

                    var.nextLine();
                } else {
                    System.out.println("Couldn't add offer.");
                }
            }

            List<Integer> allBooks = this.shop.getBooks();
            System.out.print("Do you want to add a book? (yes/no): ");
            option = var.nextLine();
            if (option.equalsIgnoreCase("yes")) {
                while (true){
                    System.out.print("Choose book number: ");
                    int choice, id;
                    while (true) {
                        try {
                            choice = var.nextInt() - 1;
                            id = allBooks.get(choice);
                            if (allBooks.contains(id)) {
                                this.addBook(choice);
                                break;
                            } else {
                                throw new ArithmeticException("ID not in list");
                            }
                        } catch (InputMismatchException e) {
                            var.next();
                            System.out.print("Please insert a number: ");
                        } catch (Exception e) {
                            System.out.print("The offer you want to add is not a child offer. Please try again: ");
                        }
                    }

                    this.shop.lowerStock(id);

                    var.nextLine();
                    System.out.print("Do you want to add another? (yes/no): ");
                    option = var.nextLine();
                    if (option.equalsIgnoreCase("no")){
                        break;
                    }
                }
            } else {
                System.out.println("Book wasn't added.");
            }
        }
    }
}

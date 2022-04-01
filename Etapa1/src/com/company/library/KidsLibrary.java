package com.company.library;

import com.company.offer.KidsOffer;
import com.company.offer.Offer;
import com.company.product.Book;
import com.company.product.ChildBook;
import com.company.product.Manual;

import java.util.*;

public class KidsLibrary extends Library{
    private List<KidsOffer> offers;
    private List<ChildBook> books;


    public KidsLibrary() {
        this.offers = new ArrayList<KidsOffer>();
        this.books = new ArrayList<ChildBook>();
    }

    public KidsLibrary(String name, double rating, HashMap<String, Integer> stock, List<KidsOffer> offers, List<ChildBook> books){
        super(name, rating, stock);
        this.books = books;
        this.offers = offers;
    }

    public void read(){
        Scanner var = new Scanner(System.in);

        System.out.print("Kids Library's name: ");
        this.name = var.nextLine();

        //System.out.println("-> Kids Library's list of kids books: ");
        System.out.print("How many books do you want: ");
        int n = var.nextInt();

        for (int i = 0; i < n; i++) {
            ChildBook book = new ChildBook();
            book.read();
            books.add(book);

            System.out.print("Introduce the stock of <<" + book.getTitle() + ">> : ");
            int quantity = var.nextInt();
            stock.put(book.getTitle(), quantity);
        }

        //System.out.println("->Kids library's list of offers: ");
        System.out.print("How many offers: ");
        n = var.nextInt();

        for (int i = 0; i < n; i++) {
            KidsOffer offer = new KidsOffer();
            offer.read();
            offers.add(offer);

            System.out.print("Introduce the stock of <<" + offer.getName() + ">> : ");
            int quantity = var.nextInt();
            stock.put(offer.getName(), quantity);
        }
    }

    @Override
    public String toString(){
        String output = "~~ Kid's library ~~\n" + "Name: " + this.name + "\nRating: " + this.rating + "\nList of books:\n";

        for (ChildBook it : books) {
            output += it + "Stock: " + this.stock.get(it.getTitle()) + "\n";
        }

        output += "List of offers:\n";
        for (KidsOffer it : offers) {
            output += it + "Stock: " + this.stock.get(it.getName()) + "\n";
        }
        return output;
    }

    @Override
    public List<Book> getBooks(){
        List<Book> b = new ArrayList<Book>();
        for (ChildBook it: books){
            b.add(it);
        }
        return b;
    }

    @Override
    public List<Offer> getOffers(){
        List<Offer> o = new ArrayList<Offer>();
        for (KidsOffer it: offers){
            o.add(it);
        }
        return o;
    }

    public void addBook(ChildBook book){
        books.add(book);
    }

    public void removeBook(ChildBook book) {
        for (ChildBook it: books){
            if(it.equals(book)){
                books.remove(book);
                break;
            }
        }
    }

    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        if (!(obj instanceof KidsLibrary)) {
            return false;
        }
        KidsLibrary kidsLibrary = (KidsLibrary) obj;
        return Objects.equals(books, kidsLibrary.books);
    }

    public int hashCode(){
        return Objects.hash(books);
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

    public void addOffer(KidsOffer offer) {
        offers.add(offer);
    }
}

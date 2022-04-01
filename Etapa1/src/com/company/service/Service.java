package com.company.service;

import com.company.Order;
import com.company.library.BigLibrary;
import com.company.library.KidsLibrary;
import com.company.library.Library;
import com.company.library.NovelLibrary;
import com.company.offer.BigOffer;
import com.company.offer.KidsOffer;
import com.company.product.Book;
import com.company.product.ChildBook;
import com.company.product.Manual;
import com.company.product.Novel;
import com.company.user.Login;
import com.company.user.User;

import java.util.*;

public class Service { // singleton
    private static Service single_instance = null;
    private Login login;
    private HashMap<Integer, Library> shops;
    private HashMap<Integer, Order> orders;
    private User currentUser;
    private int shopId;
    private int orderId;

    private Service(){
        this.shops = new HashMap<Integer, Library>();
        this.orders = new HashMap<Integer, Order>();
    }

    public static synchronized Service getInstance() {
        if (single_instance == null)
            single_instance = new Service();
        return single_instance;
    }

    public String getCurrentUserEmail() {
        return currentUser.getEmail();
    }

    public int singIn(){
        login = Login.getInstance();

        Scanner var = new Scanner(System.in);

        int type = 0;
        while (true) {
            System.out.print("Type 1 for sign in or 2 for sign up: ");
            int option = var.nextInt();
            var.nextLine();

            if (option == 1){
                // sign in
                System.out.print("Email: ");
                String email = var.nextLine();
                System.out.print("Password: ");
                String password = var.nextLine();

                if (login.signIn(email, password)) {
                    System.out.println("You have logged in successfully!\n");
                    currentUser = login.getCurentUser();
                    if (email.equals("admin@gmail.com")) {
                        type = 1;
                    }
                    break;
                } else {
                    System.out.println("Couldn't log in, password or email incorrect. Please try again");
                }
            } else if (option == 2) {
                // sign up
                User customer = new User();
                customer.read();
                if(login.signUp(customer)) {
                    System.out.println("You have registered successfully!\n");
                    currentUser = customer;
                } else {
                    System.out.println("You are already signed up with this email.");
                }
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }

        return type;
    }

    public void signOut(){
        login.setCurentUser(null);
        System.out.println("Hope to see you back soon!\n");
    }

    public void listLibrary() {
        Scanner var = new Scanner(System.in);

        System.out.print("What library do you want to list? Please introduce the name: ");
        String name = var.nextLine();
        System.out.println("");

        boolean flag = false;
        Set set = shops.entrySet();
        for (Object o : set) {
            Map.Entry entry = (Map.Entry) o;
            if (((Library) entry.getValue()).getName().equalsIgnoreCase(name)) {
                System.out.println((Library) entry.getValue());
                flag = true;
                break;
            }
        }

        if (!flag) {
            System.out.println("Library does not exist.\n");
        }
    }

    public void listLibraries(){
        System.out.println("Libraries:");
        Set set = shops.entrySet();
        for (Object o : set) {
            Map.Entry entry = (Map.Entry) o;
            System.out.println(((Library) entry.getValue()));
            System.out.println();
        }
    }

    public void addLibrary() {
        Scanner var = new Scanner(System.in);

        Library library;

        System.out.println("What kind of library do you want to add?");
        System.out.println("1. Novel library");
        System.out.println("2. Kids library");
        System.out.println("3. Big library");

        while (true) {
            System.out.print("Please insert the number of your option: ");
            int option = var.nextInt();
            var.nextLine();

            if (option == 1) {
                library = new NovelLibrary();
                break;
            } else if (option == 2) {
                library = new KidsLibrary();
                break;
            } else if (option == 3) {
                library = new BigLibrary();
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }

        shopId ++;
        library.read();
        shops.put(shopId, library);

        System.out.println("Library added successfully!\n");
    }

    public void removeLibrary() {
        Scanner var = new Scanner(System.in);

        System.out.println("List of libraries:");

        Set set = shops.entrySet();
        for (Object o : set) {
            Map.Entry entry = (Map.Entry) o;
            System.out.println(((Library) entry.getValue()).getName());
        }

        while(true) {
            System.out.print("Please insert the name of the library you want to remove: ");
            String name = var.nextLine();

            boolean flag = false;
            for (Object o : set) {
                Map.Entry entry = (Map.Entry) o;
                if (((Library) entry.getValue()).getName().equalsIgnoreCase(name)) {
                    shops.remove(entry.getKey());
                    System.out.println("The library has been successfully removed!\n");
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                System.out.println("Invalid library name. Please try again.");
            } else {
                break;
            }
        }
    }

    public void addBook() {
        Scanner var  = new Scanner(System.in);

        Set set = shops.entrySet();
        boolean flag = false;

        while(true) {
            System.out.print("Add product to library: ");
            String name = var.nextLine();

            for (Object o : set) {
                Map.Entry entry = (Map.Entry) o;
                if (((Library) entry.getValue()).getName().equalsIgnoreCase(name)) {

                    Book book = null;
                    if (entry.getValue() instanceof BigLibrary){
                        System.out.println("Big Library");

                        while (true) {
                            System.out.print("Do you want to add a novel or a manual? (novel/manual): ");
                            String option = var.nextLine();

                            if (option.equalsIgnoreCase("novel")) {
                                book = new Novel();
                                book.read();
                                ((BigLibrary) entry.getValue()).addNovel((Novel) book);
                                break;
                            } else if (option.equalsIgnoreCase("manual")) {
                                book = new Manual();
                                book.read();
                                ((BigLibrary) entry.getValue()).addManual((Manual) book);
                                break;
                            } else {
                                System.out.println("Invalid option. Please try again.");
                            }
                        }
                        System.out.print("Stock of the product: ");
                        int stock = var.nextInt();
                        ((BigLibrary)entry.getValue()).updateStock(book.getTitle(), stock);

                    } else if (entry.getValue() instanceof NovelLibrary){
                        System.out.println("Novel library");
                        Novel novel = new Novel();
                        novel.read();
                        ((NovelLibrary)entry.getValue()).addNovel((Novel)novel);

                        System.out.print("Stock of the product: ");
                        int stock = var.nextInt();
                        ((NovelLibrary)entry.getValue()).updateStock(novel.getTitle(), stock);

                    } else {
                        System.out.println("Kids library");
                        ChildBook childBook = new ChildBook();
                        childBook.read();
                        ((KidsLibrary)entry.getValue()).addBook((ChildBook) book);

                        System.out.print("Stock of the product: ");
                        int stock = var.nextInt();
                        ((KidsLibrary)entry.getValue()).updateStock(childBook.getTitle(), stock);
                    }
                    flag = true;
                    System.out.println("Book added successfully!\n");
                    break;
                }
            }
            if (!flag) {
                System.out.println("Invalid library name. Please try again.");
            } else {
                break;
            }
        }
    }

    public void removeBook(){
        Scanner var = new Scanner(System.in);

        Set set = shops.entrySet();
        boolean flagl = false;

        while(true) {
            System.out.print("Remove product from library: ");
            String name = var.nextLine();

            for (Object o : set) {
                Map.Entry entry = (Map.Entry) o;
                if (((Library) entry.getValue()).getName().equalsIgnoreCase(name)) {
                    Book book = null;
                    if (entry.getValue() instanceof BigLibrary){
                        System.out.println("Big Library");

                        List<Book> books = ((BigLibrary)entry.getValue()).getBooks();
                        boolean flagb = false;
                        while (true) {
                            System.out.print("Introduce the name of the book you want to remove: ");
                            String option = var.nextLine();

                            for (Book it: books) {
                                if (it.getTitle().equalsIgnoreCase(option)) {
                                    if (it instanceof Novel){
                                        ((BigLibrary)entry.getValue()).removeNovel((Novel)it);
                                    } else {
                                        ((BigLibrary)entry.getValue()).removeManual((Manual)it);
                                    }

                                    ((BigLibrary)entry.getValue()).removeFromStock(it.getTitle());
                                    flagb = true;
                                    break;
                                }
                            }
                            if (flagb) {
                                break;
                            } else {
                                System.out.println("Invalid book name. Please try again.");
                            }
                        }

                    } else if (entry.getValue() instanceof NovelLibrary){
                        System.out.println("Novel library");
                        List<Book> books = ((NovelLibrary)entry.getValue()).getBooks();
                        boolean flagb = false;
                        while (true) {
                            System.out.print("Introduce the name of the book you want to remove: ");
                            String option = var.nextLine();

                            for (Book it: books) {
                                if (it.getTitle().equalsIgnoreCase(option)) {
                                    ((NovelLibrary)entry.getValue()).removeNovel((Novel)it);

                                    ((NovelLibrary)entry.getValue()).removeFromStock(it.getTitle());
                                    flagb = true;
                                    break;
                                }
                            }
                            if (flagb) {
                                break;
                            } else {
                                System.out.println("Invalid book name. Please try again.");
                            }
                        }

                    } else {
                        System.out.println("Kids library");
                        List<Book> books = ((KidsLibrary)entry.getValue()).getBooks();
                        boolean flagb = false;
                        while (true) {
                            System.out.print("Introduce the name of the book you want to remove: ");
                            String option = var.nextLine();

                            for (Book it: books) {
                                if (it.getTitle().equalsIgnoreCase(option)) {
                                    ((KidsLibrary)entry.getValue()).removeBook((ChildBook) it);

                                    ((KidsLibrary)entry.getValue()).removeFromStock(it.getTitle());
                                    flagb = true;
                                    break;
                                }
                            }
                            if (flagb) {
                                break;
                            } else {
                                System.out.println("Invalid book name. Please try again.");
                            }
                        }
                    }
                    flagl = true;
                    System.out.println("Book removed successfully!\n");
                    break;
                }
            }
            if (!flagl) {
                System.out.println("Invalid library name. Please try again.");
            } else {
                break;
            }
        }
    }

    public void addOffer() {
        Scanner var = new Scanner(System.in);

        boolean flag = false;
        while (true) {
            System.out.print("Add offer to library: ");
            String name = var.nextLine();

            Set set = shops.entrySet();
            Iterator it = set.iterator();
            while(it.hasNext()) {
                Map.Entry entry = (Map.Entry)it.next();
                if(((Library)entry.getValue()).getName().equalsIgnoreCase(name)){
                    flag = true;

                    if (entry.getValue() instanceof BigLibrary) {
                        System.out.println("Big library");
                        BigOffer offer = new BigOffer();
                        offer.read();
                        ((BigLibrary)entry.getValue()).addOffer(offer);

                        System.out.print("Introduce the stock of the offer: ");
                        int stock = var.nextInt();
                        ((BigLibrary)entry.getValue()).updateStock(offer.getName(), stock);

                    } else if (entry.getValue() instanceof KidsLibrary) {
                        System.out.println("Kids library");
                        KidsOffer offer = new KidsOffer();
                        offer.read();
                        ((KidsLibrary)entry.getValue()).addOffer(offer);

                        System.out.print("Introduce the stock of the offer: ");
                        int stock = var.nextInt();
                        ((KidsLibrary)entry.getValue()).updateStock(offer.getName(), stock);

                    } else {
                        System.out.println("This library doesn't accept offers.");
                    }

                    System.out.println("Offer added successfully!\n");
                    break;
                }
            }

            if (flag) {
                break;
            } else {
                System.out.println("Invalid library name. Please try again.");
            }
        }
    }

    public void addOrder() {
        Scanner var = new Scanner(System.in);

        Order order = new Order();
        order.setCustomer(currentUser);
        order.reader(shops);
        orderId++;
        order.setPrice();

        if (order.getPrice() > 0.0) {
            System.out.println("Order's price is " + order.getPrice());
            System.out.println("Order ID: " + orderId);
            System.out.print("Do you want to place the order? (yes/no): ");

            while(true) {
                String option = var.nextLine();
                if (option.equalsIgnoreCase("yes")){
                    orders.put(orderId, order);
                    System.out.println("Order placed successfully!\n");
                    break;
                } else if (option.equalsIgnoreCase("no")){
                    System.out.println("Order aborted.\n");
                    break;
                } else {
                    System.out.print("Invalid option. please try again (yes/no): ");
                }
            }
        }
    }

    public void cancelOrder() {
        Scanner var = new Scanner(System.in);
        System.out.print("Please insert the ID of the order you want to cancel: ");
        int option = var.nextInt();

        if (this.orders.containsKey(option)){
            this.orders.remove(option);
            System.out.println("Order removed successfully!\n");
        } else {
            System.out.println("Order ID " + option + " doesn't exist.");
        }
    }

    public void sortLibraries(){
        Set<Map.Entry<Integer, Library>> set = new TreeSet<>(new Sort());
        set.addAll(this.shops.entrySet());

        for (Map.Entry<Integer, Library> entry : set) {
            System.out.println(entry.getValue().getName() + " - rating: " + entry.getValue().getRating());
        }
    }

    public void rateLibrary(){
        Scanner var = new Scanner(System.in);

        if (shops.isEmpty()) {
            System.out.println("Libraries not found.");
        } else {
            System.out.println("List of libraries:");
            Set set = shops.entrySet();
            for (Object o : set) {
                Map.Entry entry = (Map.Entry) o;
                System.out.println(((Library) entry.getValue()).getName());
            }

            boolean flag = false;
            while(true) {
                System.out.print("Choose one library: ");
                String name = var.nextLine();
                Library library;

                for (Object o : set) {
                    Map.Entry entry = (Map.Entry) o;

                    if(((Library)entry.getValue()).getName().equalsIgnoreCase(name)){
                        library = (Library) entry.getValue();
                        flag = true;
                        library.addRating();
                        break;
                    }
                }
                if (flag) {
                    break;
                } else {
                    System.out.println("Invalid library name. Please try again.");
                }
            }
        }

    }
}
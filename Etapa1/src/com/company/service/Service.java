package com.company.service;

import com.company.Order;
import com.company.library.BigLibrary;
import com.company.library.KidsLibrary;
import com.company.library.Library;
import com.company.library.NovelLibrary;
import com.company.offer.BigOffer;
import com.company.offer.KidsOffer;
import com.company.offer.Offer;
import com.company.product.Book;
import com.company.product.ChildBook;
import com.company.product.Manual;
import com.company.product.Novel;
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
    private OfferService offerService;
    private BookService bookService;

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

                // 1
                System.out.print("Name: ");
                String name = var.nextLine();
                System.out.print("Email: ");
                String email = var.nextLine();
                String phoneNumber;

                while(true) {
                    System.out.print("Phone number: ");
                    phoneNumber = var.nextLine();
                    boolean ok = phoneNumber.matches("0[0-9]{9}");
                    if (ok)
                        break;
                    else
                        System.out.println("Please give a valid phone number.");
                }
                System.out.print("Password: ");
                String password = var.nextLine();

                User customer = new User(name, email, phoneNumber, password);

                // 2
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
        bookService = BookService.getInstance();
        offerService = OfferService.getInstance();

        Library library;

        System.out.println("What kind of library do you want to add?");
        System.out.println("1. Novel library");
        System.out.println("2. Kids library");
        System.out.println("3. Big library");

        while (true) {
            System.out.print("Please insert the number of your option: ");
            int option = var.nextInt();
            var.nextLine();

            HashMap<String, Integer> stock = new HashMap<String, Integer>();

            System.out.print("Library's name: ");
            String name = var.nextLine();

            if (option == 1) {

                // name, rating, stock, novels
                List<Novel> novels = new ArrayList<Novel>();
                System.out.print("How many novels does the library have? ");
                int n = var.nextInt();

                for (int i = 0; i < n; i++) {
                    Novel novel = bookService.readNovel();
                    novels.add(novel);

                    System.out.print("Introduce the stock of <<" + novel.getTitle() + ">> : ");
                    int quantity = var.nextInt();
                    stock.put(novel.getTitle(), quantity);
                }

                library = new NovelLibrary(name, 0, stock, novels);
                break;
            } else if (option == 2) {

                // name, rating, stock, offers, books

                List<ChildBook> books = new ArrayList<ChildBook>();
                List<KidsOffer> offers = new ArrayList<KidsOffer>();

                //System.out.println("-> Kids Library's list of kids books: ");
                System.out.print("How many books do you want: ");
                int n = var.nextInt();

                for (int i = 0; i < n; i++) {
                    ChildBook book = bookService.readChildBook();
                    books.add(book);

                    System.out.print("Introduce the stock of <<" + book.getTitle() + ">> : ");
                    int quantity = var.nextInt();
                    stock.put(book.getTitle(), quantity);
                }

                //System.out.println("->Kids library's list of offers: ");
                System.out.print("How many offers: ");
                n = var.nextInt();



                for (int i = 0; i < n; i++) {
                    KidsOffer offer = offerService.readKidsOffer();

                    offers.add(offer);

                    System.out.print("Introduce the stock of <<" + offer.getName() + ">> : ");
                    int quantity = var.nextInt();
                    stock.put(offer.getName(), quantity);
                }

                library = new KidsLibrary(name, 0, stock, offers, books);
                break;
            } else if (option == 3) {

                // reading big library
                // name, rating, stock, novels, offers, manuals

                List<Novel> novels = new ArrayList<Novel>();
                List<Manual> manuals = new ArrayList<Manual>();
                List<BigOffer> offers = new ArrayList<BigOffer>();

                //System.out.println("Library's list of novels: ");
                System.out.print("How many novels does the library have? ");
                int n = var.nextInt();

                for (int i = 0; i < n; i++) {
                    Novel novel = bookService.readNovel();
                    novels.add(novel);

                    System.out.print("Introduce the stock of <<" + novel.getTitle() + ">> : ");
                    int quantity = var.nextInt();
                    stock.put(novel.getTitle(), quantity);
                }

                //System.out.println("-> Library's list of offers: ");
                System.out.print("How many offers does the library have? ");
                n = var.nextInt();

                for (int i = 0; i < n; i++) {
                    BigOffer offer = offerService.readBigOffer();

                    offers.add(offer);

                    System.out.print("Introduce the stock of <<" + offer.getName() + ">> : ");
                    int quantity = var.nextInt();
                    stock.put(offer.getName(), quantity);
                }

                //System.out.println("->Library's list of manuals: ");
                System.out.print("How many manuals does the library have? ");
                n = var.nextInt();
                for (int i = 0; i < n; i++){
                    Manual manual = bookService.readManual();
                    manuals.add(manual);

                    System.out.print("Introduce the stock of <<" + manual.getTitle() + ">> : ");
                    int quantity = var.nextInt();
                    stock.put(manual.getTitle(), quantity);
                }

                library = new BigLibrary(name, 0, stock, novels, offers, manuals);

                // stop
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }

        shopId ++;
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
        bookService = BookService.getInstance();

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
                                book = bookService.readNovel();

                                List<Novel> novels = ((BigLibrary) entry.getValue()).getNovels();
                                novels.add((Novel)book);
                                ((BigLibrary) entry.getValue()).setNovels(novels);
                                break;
                            } else if (option.equalsIgnoreCase("manual")) {
                                book = bookService.readManual();

                                List<Manual> manuals = ((BigLibrary) entry.getValue()).getManuals();
                                manuals.add((Manual)book);
                                ((BigLibrary) entry.getValue()).setManuals(manuals);
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
                        Novel novel = bookService.readNovel();

                        List<Novel> novels = ((NovelLibrary) entry.getValue()).getNovels();
                        novels.add(novel);
                        ((NovelLibrary) entry.getValue()).setNovels(novels);

                        System.out.print("Stock of the product: ");
                        int stock = var.nextInt();
                        ((NovelLibrary)entry.getValue()).updateStock(novel.getTitle(), stock);

                    } else {
                        System.out.println("Kids library");
                        ChildBook childBook = bookService.readChildBook();

                        List<ChildBook> books = ((KidsLibrary) entry.getValue()).getChildBooks();
                        books.add((ChildBook) book);
                        ((KidsLibrary) entry.getValue()).setBooks(books);

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

                                        List<Novel> novels = ((BigLibrary)entry.getValue()).getNovels();
                                        for (Novel nov: novels){
                                            if(nov.equals(it)){
                                                novels.remove(it);
                                                break;
                                            }
                                        }
                                        ((BigLibrary)entry.getValue()).setNovels(novels);

                                    } else {
                                        List<Manual> manuals = ((BigLibrary)entry.getValue()).getManuals();
                                        for (Manual man: manuals){
                                            if(man.equals(it)){
                                                manuals.remove(it);
                                                break;
                                            }
                                        }
                                        ((BigLibrary)entry.getValue()).setManuals(manuals);
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
                                    List<Novel> novels = ((NovelLibrary)entry.getValue()).getNovels();
                                    for (Novel nov: novels){
                                        if(nov.equals(it)){
                                            novels.remove(it);
                                            break;
                                        }
                                    }
                                    ((NovelLibrary)entry.getValue()).setNovels(novels);

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

                                    List<ChildBook> cbooks = ((KidsLibrary)entry.getValue()).getChildBooks();
                                    for (ChildBook cb: cbooks){
                                        if(cb.equals(it)){
                                            cbooks.remove(it);
                                            break;
                                        }
                                    }
                                    ((KidsLibrary)entry.getValue()).setBooks(cbooks);

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
        offerService = OfferService.getInstance();

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

                        BigOffer offer = offerService.readBigOffer();

                        List<Offer> offers = ((BigLibrary)entry.getValue()).getOffers();
                        offers.add(offer);
                        List<BigOffer> aux = (List<BigOffer>)(List<?>) offers;
                        ((BigLibrary)entry.getValue()).setOffers(aux);

                        System.out.print("Introduce the stock of the offer: ");
                        int stock = var.nextInt();
                        ((BigLibrary)entry.getValue()).updateStock(offer.getName(), stock);

                    } else if (entry.getValue() instanceof KidsLibrary) {

                        // name, manuals, price, book, toy

                        System.out.println("Kids library");

                        KidsOffer offer = offerService.readKidsOffer();

                        List<Offer> offers = ((KidsLibrary)entry.getValue()).getOffers();
                        offers.add(offer);
                        List<KidsOffer> aux = (List<KidsOffer>)(List<?>) offers;
                        ((KidsLibrary)entry.getValue()).setOffers(aux);

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
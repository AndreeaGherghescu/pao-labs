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
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Service { // singleton
    private static Service single_instance = null;
    private Login login;
    private Map<Integer, Library> shops;
    private Map<Integer, Order> orders;
    private User currentUser;
    private int shopId;
    private int orderId;
    private OfferService offerService;
    private BookService bookService;
    private AuditService audit;

    private Service(){
        this.shops = new HashMap<Integer, Library>();
        this.orders = new HashMap<Integer, Order>();

        this.audit = AuditService.getInstance();
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
        bookService = BookService.getInstance();

        login.setUsersReg(new ReaderCSV<User>().getUsersReg());
        bookService.readBooks();

        Scanner var = new Scanner(System.in);

        int type = 0;
        while (true) {
            System.out.print("Type 1 for sign in or 2 for sign up: ");

            int option = 0;

            boolean flag = true;
            while (flag) {
                try {
                    flag = false;
                    option = var.nextInt();
                } catch (InputMismatchException e) {
                    flag = true;
                    var.next();
                    System.out.print("Please insert a number: ");
                }
            }

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
                    audit.WriteTimestamp("Sign in as " + email);
                    break;
                } else {
                    System.out.println("Couldn't log in, password or email incorrect. Please try again");
                }
            } else if (option == 2) {
                // sign up

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
                        System.out.print("Please give a valid phone number: ");
                }
                System.out.print("Password: ");
                String password = var.nextLine();

                User customer = new User(name, email, phoneNumber, password);

                if(login.signUp(customer)) {
                    new WriterCSV<User>().writeUser(customer);
                    audit.WriteTimestamp("Sign up as " + email);
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
        audit.WriteTimestamp("Sign out");
        login.setCurentUser(null);
        System.out.println("Hope to see you back soon!\n");
    }

    public void listLibrary() {
        audit.WriteTimestamp("List one library");
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
        audit.WriteTimestamp("List libraries");
        System.out.println("Libraries:");
//        Set set = shops.entrySet();
//        for (Object o : set) {
//            Map.Entry entry = (Map.Entry) o;
//            System.out.println(((Library) entry.getValue()));
//            System.out.println();
//        }

        // stream si lambda
        shops.entrySet().stream().forEach(e -> System.out.println(((Map.Entry)e).getValue()));

    }

    public void addLibrary() {
        audit.WriteTimestamp("Add a library");
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

            int option;
            while (true) {
                try {
                    option = var.nextInt();
                    var.nextLine();
                    if (option == 1 || option == 2 || option == 3) {
                        break;
                    }
                } catch (InputMismatchException e) {
                    var.next();
                    System.out.print("Please insert a number: ");
                }
            }

            Map<Integer, Integer> stock = new HashMap<Integer, Integer>();

            System.out.print("Library's name: ");
            String name = var.nextLine();

            if (option == 1) { // novel library

                // name, rating, stock, novels
                List<Integer> novels = new ArrayList<Integer>();
                System.out.print("How many novels does the library have? ");
                int n;
                while (true) {
                    try {
                        n = var.nextInt();
                        break;
                    } catch (InputMismatchException e) {
                        var.next();
                        System.out.print("Please insert a number: ");
                    }
                }

                System.out.println("List of novels: ");
                List<Integer> allNovels = bookService.getNovels();
                for (int j = 0; j < allNovels.size(); j++) {
                    System.out.println((j + 1) + ". " + bookService.getTitleById(allNovels.get(j)) + ", " + bookService.getAuthorById(allNovels.get(j)));
                }
                for (int i = 0; i < n; i++) {
//                    Novel novel = bookService.readNovel();
//                    novels.add(novel);

                    System.out.print("Please choose the id of the novel you want to add: ");
                    while (true) {
                        try {
                            int alege = var.nextInt() - 1;
                            int id = allNovels.get(alege);
                            if (allNovels.contains(id) && !novels.contains(id)) {
                                novels.add(id);
                                System.out.print("Introduce the stock of <<" + bookService.getTitleById(id) + ">> : ");
                                int quantity = var.nextInt();
                                stock.put(id, quantity);
                                break;
                            } else if (novels.contains(id)) {
                                System.out.println("The novel is already in the library");
                            } else if (!allNovels.contains(id)) {
                                throw new ArithmeticException("ID not in list");
                            }
                        } catch (InputMismatchException e) {
                            var.next();
                            System.out.print("Please insert a number: ");
                        } catch (Exception e) {
                            System.out.print("The book you want to add is not a novel. Please try again: ");
                        }
                    }

                }

                library = new NovelLibrary(name, 0, stock, novels);
                break;
            } else if (option == 2) { // kid s library

                // name, rating, stock, offers, books

                List<Integer> childBooks = new ArrayList<Integer>();
                List<Integer> offers = new ArrayList<Integer>();

                System.out.print("How many books does the library have? ");
                int n;
                while (true) {
                    try {
                        n = var.nextInt();
                        break;
                    } catch (InputMismatchException e) {
                        var.next();
                        System.out.print("Please insert a number: ");
                    }
                }

                System.out.println("List of books: ");
                List<Integer> allChild = bookService.getChildBooks();
                for (int j = 0; j < allChild.size(); j++) {
                    System.out.println((j + 1) + ". " + bookService.getTitleById(allChild.get(j)) + ", " + bookService.getAuthorById(allChild.get(j)));
                }
                for (int i = 0; i < n; i++) {
//                  ChildBook book = bookService.readChildBook();
//                  books.add(book);

                    System.out.print("Please choose the id of the book you want to add: ");
                    while (true) {
                        try {
                            int alege = var.nextInt() - 1;
                            int id = allChild.get(alege);
                            if (allChild.contains(id) && !childBooks.contains(id)) {
                                childBooks.add(id);
                                System.out.print("Introduce the stock of <<" + bookService.getTitleById(id) + ">> : ");
                                int quantity = var.nextInt();
                                stock.put(id, quantity);
                                break;
                            } else if (childBooks.contains(id)) {
                                System.out.println("The book is already in the library");
                            } else if (!allChild.contains(id)) {
                                throw new ArithmeticException("ID not in list");
                            }
                        } catch (InputMismatchException e) {
                            var.next();
                            System.out.print("Please insert a number: ");
                        } catch (Exception e) {
                            System.out.print("The book you want to add is not a child book. Please try again: ");
                        }
                    }

                }

                System.out.print("How many offers does the library have? ");
                while (true) {
                    try {
                        n = var.nextInt();
                        break;
                    } catch (InputMismatchException e) {
                        var.next();
                        System.out.print("Please insert a number: ");
                    }
                }

                for (int i = 0; i < n; i++) {
                    Integer offer = offerService.readKidsOffer();
                    offers.add(offer);

                    // 1
//                    System.out.print("Please choose the id of the offer you want to add: ");
//                    while (true) {
//                        try {
//                            int id = var.nextInt();
//                            List<Integer> allOffers = offerService.getKidsOffers();
//                            if (allOffers.contains(id) && !offers.contains(id)) {
//                                offers.add(id);
//                                break;
////                            System.out.print("Introduce the stock of <<" + offerService.getNameById(id) + ">> : ");
////                            int quantity = var.nextInt();
////                            stock.put(id, quantity);
//                            } else if (offers.contains(id)) {
//                                System.out.println("The offer is already in the library");
//                            } else if (!allOffers.contains(id)) {
//                                throw new ArithmeticException("ID not in list");
//                            }
//                        } catch (InputMismatchException e) {
//                            var.next();
//                            System.out.print("Please insert a number: ");
//                        } catch (Exception e) {
//                            System.out.print("The offer you want to add is not a child offer. Please try again: ");
//                        }
//                    }
                    // 2

                }

                library = new KidsLibrary(name, 0, stock, offers, childBooks);
                break;
            } else if (option == 3) { //big library

                // reading big library
                // name, rating, stock, novels, offers, manuals

                List<Integer> novels = new ArrayList<Integer>();
                List<Integer> manuals = new ArrayList<Integer>();
                List<Integer> offers = new ArrayList<Integer>();

                System.out.print("How many novels does the library have? ");
                int n;
                while (true) {
                    try {
                        n = var.nextInt();
                        break;
                    } catch (InputMismatchException e) {
                        var.next();
                        System.out.print("Please insert a number: ");
                    }
                }

                System.out.println("List of novels: ");
                List<Integer> allNovels = bookService.getNovels();
                for (int j = 0; j < allNovels.size(); j++) {
                    System.out.println((j + 1) + ". " + bookService.getTitleById(allNovels.get(j)) + ", " + bookService.getAuthorById(allNovels.get(j)));
                }
                for (int i = 0; i < n; i++) {
//                    Novel novel = bookService.readNovel();
//                    novels.add(novel);
                    System.out.print("Please choose the id of the novel you want to add: ");
                    while (true) {
                        try {
                            int alege = var.nextInt() - 1;
                            int id = allNovels.get(alege);
                            if (allNovels.contains(id) && !novels.contains(id)) {
                                novels.add(id);
                                System.out.print("Introduce the stock of <<" + bookService.getTitleById(id) + ">> : ");
                                int quantity = var.nextInt();
                                stock.put(id, quantity);
                                break;
                            } else if (novels.contains(id)) {
                                System.out.println("The novel is already in the library");
                            } else if (!allNovels.contains(id)) {
                                throw new ArithmeticException("ID not in list");
                            }
                        } catch (InputMismatchException e) {
                            var.next();
                            System.out.print("Please insert a number: ");
                        } catch (Exception e) {
                            System.out.print("The book you want to add is not a novel. Please try again: " );
                        }
                    }

                }

                System.out.print("How many offers does the library have? ");
                while (true) {
                    try {
                        n = var.nextInt();
                        break;
                    } catch (InputMismatchException e) {
                        var.next();
                        System.out.print("Please insert a number: ");
                    }
                }

                for (int i = 0; i < n; i++) {

                    Integer offer = offerService.readBigOffer();
                    offers.add(offer);
                    // 1
//                    System.out.print("Please choose the id of the offer you want to add: ");
//                    while (true) {
//                        try {
//                            int id = var.nextInt();
//                            List<Integer> allOffers = offerService.getBigOffers();
//                            if (allOffers.contains(id) && !offers.contains(id)) {
//                                offers.add(id);
//                                break;
////                            System.out.print("Introduce the stock of <<" + offerService.getNameById(id) + ">> : ");
////                            int quantity = var.nextInt();
////                            stock.put(id, quantity);
//                            } else if (novels.contains(id)) {
//                                System.out.println("The novel is already in the library");
//                            } else if (!allOffers.contains(id)) {
//                                throw new ArithmeticException("ID not in list");
//                            }
//                        } catch (InputMismatchException e) {
//                            var.next();
//                            System.out.print("Please insert a number: ");
//                        } catch (Exception e) {
//                            System.out.print("The book you want to add is not a novel. Please try again: ");
//                        }
//                    }

                }

                System.out.print("How many manuals does the library have? ");
                while (true) {
                    try {
                        n = var.nextInt();
                        break;
                    } catch (InputMismatchException e) {
                        var.next();
                        System.out.print("Please insert a number: ");
                    }
                }

                System.out.println("List of manuals: ");
                List<Integer> allManuals= bookService.getManuals();
                for (int j = 0; j < allManuals.size(); j++) {
                    System.out.println((j + 1) + ". " + bookService.getTitleById(allManuals.get(j)) + ", " + bookService.getAuthorById(allManuals.get(j)));
                }
                for (int i = 0; i < n; i++){

                    System.out.print("Please choose the id of the manual you want to add: ");
                    while (true) {
                        try {
                            int alege = var.nextInt() - 1;
                            int id = allManuals.get(alege);
                            if (allManuals.contains(id) && !manuals.contains(id)) {
                                manuals.add(id);
                                System.out.print("Introduce the stock of <<" + bookService.getTitleById(id) + ">> : ");
                                int quantity = var.nextInt();
                                stock.put(id, quantity);
                                break;
                            } else if (manuals.contains(id)) {
                                System.out.println("The manual is already in the library");
                            } else if (!allManuals.contains(id)) {
                                throw new ArithmeticException("ID not in list");
                            }
                        } catch (InputMismatchException e) {
                            var.next();
                            System.out.print("Please insert a number: ");
                        } catch (Exception e) {
                            System.out.print("The book you want to add is not a child book. Please try again:");
                        }
                    }

                }

                library = new BigLibrary(name, 0, stock, novels, offers, manuals);

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
        audit.WriteTimestamp("Remove library");
        Scanner var = new Scanner(System.in);

        if (shops.isEmpty()) {
            System.out.println("There are no libraries.");
            return;
        }

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
        audit.WriteTimestamp("Add book");
        Scanner var  = new Scanner(System.in);
        bookService = BookService.getInstance();

        System.out.println("What type of book do you want to add?");
        System.out.println("1. Novel");
        System.out.println("2. Manual");
        System.out.println("3. Child book");
        System.out.print("Please insert the number of your option: ");

        int option;
        while (true) {
            try {
                option = var.nextInt();
                if (option == 1 || option == 2 || option == 3) {
                    break;
                }
            } catch (Exception e) {
                System.out.print("Please insert a valid number: ");
            }
        }

        int book;
        if (option == 2) { // manual
            book = bookService.readManual();
        } else if (option == 3) {
            book = bookService.readChildBook();
        } else {
            book = bookService.readNovel();
        }
    }

    public void addBookToLibrary () {
        audit.WriteTimestamp("Add book to library");
        Scanner var  = new Scanner(System.in);
        bookService = BookService.getInstance();

        System.out.print("Insert the name of the library: ");
        String name = var.nextLine();

        boolean flag = false;
        Set set = this.shops.entrySet();
        for (Object o: set) {
            Map.Entry entry = (Map.Entry) o;
            if (((Library)entry.getValue()).getName().equalsIgnoreCase(name)) {
                if (entry.getValue() instanceof BigLibrary) {
                    BigLibrary library = (BigLibrary) entry.getValue();
                    System.out.println("Big library");
                    System.out.print("Do you want to add a manual or a novel? (manual/novel): ");

                    String option;
                    while (true) {
                        try {
                            option = var.nextLine();
                            if (Objects.equals(option, "manual") || Objects.equals(option, "novel")) {
                                break;
                            }
                        } catch (Exception e) {
                            System.out.print("Please insert a valid option: ");
                        }
                    }

                    if (option.equalsIgnoreCase("manual")) {
                        System.out.println("List of manuals: ");
                        List<Integer> allManuals = bookService.getManuals();
                        for (int i = 0; i < allManuals.size(); i++) {
                            System.out.println((i + 1) + ". " + bookService.getTitleById(allManuals.get(i)) + ", " + bookService.getAuthorById(allManuals.get(i)));
                        }
                        System.out.print("Please insert the number of the book you want to add: ");
                        while (true) {
                            try {
                                int alegere = var.nextInt() - 1;
                                int id = allManuals.get(alegere);
                                List<Integer> manuals = library.getManuals();
                                if (allManuals.contains(id) && !manuals.contains(id)) {
                                    manuals.add(id);
                                    library.setManuals(manuals);
                                    System.out.print("Introduce the stock of <<" + bookService.getTitleById(id) + ">> : ");
                                    int quantity = var.nextInt();
                                    library.updateStock(id, quantity);
                                    break;
                                } else if (manuals.contains(id)) {
                                    System.out.println("The manual is already in the library");
                                } else if (!allManuals.contains(id)) {
                                    throw new ArithmeticException("ID not in list");
                                }
                            } catch (InputMismatchException e) {
                                var.next();
                                System.out.print("Please insert a number: ");
                            } catch (Exception e) {
                                System.out.print("The book you want to add is not a manual. Please try again: ");
                            }
                        }

                    } else {
                        System.out.println("List of novels: ");
                        List<Integer> allNovels = bookService.getNovels();
                        for (int i = 0; i < allNovels.size(); i++) {
                            System.out.println((i + 1) + ". " + bookService.getTitleById(allNovels.get(i)) + ", " + bookService.getAuthorById(allNovels.get(i)));
                        }
                        System.out.print("Please insert the number of the book you want to add: ");
                        while (true) {
                            try {
                                int alegere = var.nextInt() - 1;
                                int id = allNovels.get(alegere);
                                List<Integer> novels = library.getNovels();
                                if (allNovels.contains(id) && !novels.contains(id)) {
                                    novels.add(id);
                                    library.setNovels(novels);
                                    System.out.print("Introduce the stock of <<" + bookService.getTitleById(id) + ">> : ");
                                    int quantity = var.nextInt();
                                    library.updateStock(id, quantity);
                                    break;
                                } else if (novels.contains(id)) {
                                    System.out.println("The novel is already in the library");
                                } else if (!allNovels.contains(id)) {
                                    throw new ArithmeticException("ID not in list");
                                }
                            } catch (InputMismatchException e) {
                                var.next();
                                System.out.print("Please insert a number: ");
                            } catch (Exception e) {
                                System.out.print("The book you want to add is not a novel. Please try again: ");
                            }
                        }
                    }


                } else if (entry.getValue() instanceof NovelLibrary) {
                    NovelLibrary library = (NovelLibrary) entry.getValue();
                    System.out.println("Novel library");
                    System.out.println("List of novels: ");
                    List<Integer> allNovels = bookService.getNovels();
                    for (int i = 0; i < allNovels.size(); i++) {
                        System.out.println((i + 1) + ". " + bookService.getTitleById(allNovels.get(i)) + ", " + bookService.getAuthorById(allNovels.get(i)));
                    }
                    System.out.print("Please insert the number of the book you want to add: ");
                    while (true) {
                        try {
                            int alegere = var.nextInt() - 1;
                            int id = allNovels.get(alegere);
                            List<Integer> novels = library.getNovels();
                            if (allNovels.contains(id) && !novels.contains(id)) {
                                novels.add(id);
                                library.setNovels(novels);
                                System.out.print("Introduce the stock of <<" + bookService.getTitleById(id) + ">> : ");
                                int quantity = var.nextInt();
                                library.updateStock(id, quantity);
                                break;
                            } else if (novels.contains(id)) {
                                System.out.println("The novel is already in the library");
                            } else if (!allNovels.contains(id)) {
                                throw new ArithmeticException("ID not in list");
                            }
                        } catch (InputMismatchException e) {
                            var.next();
                            System.out.print("Please insert a number: ");
                        } catch (Exception e) {
                            System.out.print("The book you want to add is not a novel. Please try again: ");
                        }
                    }

                } else {
                    KidsLibrary library = (KidsLibrary) entry.getValue();
                    System.out.println("Kids library");
                    System.out.println("List of child books: ");
                    List<Integer> allBooks = bookService.getChildBooks();
                    for (int i = 0; i < allBooks.size(); i++) {
                        System.out.println((i + 1) + ". " + bookService.getTitleById(allBooks.get(i)) + ", " + bookService.getAuthorById(allBooks.get(i)));
                    }
                    System.out.print("Please insert the number of the book you want to add: ");
                    while (true) {
                        try {
                            int alegere = var.nextInt() - 1;
                            int id = allBooks.get(alegere);
                            List<Integer> books = library.getChildBooks();
                            if (allBooks.contains(id) && !books.contains(id)) {
                                books.add(id);
                                library.setBooks(books);
                                System.out.print("Introduce the stock of <<" + bookService.getTitleById(id) + ">> : ");
                                int quantity = var.nextInt();
                                library.updateStock(id, quantity);
                                break;
                            } else if (books.contains(id)) {
                                System.out.println("The book is already in the library");
                            } else if (!allBooks.contains(id)) {
                                throw new ArithmeticException("ID not in list");
                            }
                        } catch (InputMismatchException e) {
                            var.next();
                            System.out.print("Please insert a number: ");
                        } catch (Exception e) {
                            System.out.print("The book you want to add is not a novel. Please try again: ");
                        }
                    }
                }
                flag = true;
                System.out.println("Book added successfully!\n");
                break;
            }
        }
        if (!flag) {
            System.out.println("The library doesn't exist.");
        }
    }

    public void removeBook(){
        audit.WriteTimestamp("Remove book");
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

                        List<Integer> books = ((BigLibrary)entry.getValue()).getBooks();

                        List<Book> allBooks = books.stream().
                                map(e -> bookService.getBookById(e)).
                                collect(Collectors.toList());

                        boolean flagb = false;
                        while (true) {
                            System.out.print("Introduce the name of the book you want to remove: ");
                            String option = var.nextLine();

//                            for (Book b: allBooks) {
//                                if (b.getTitle().equalsIgnoreCase(option)) {
//                                    if (b instanceof Novel){
//
//                                        List<Integer> novels = ((BigLibrary)entry.getValue()).getNovels();
//
//                                        for (Integer nov: novels){
//                                            if(nov.equals()){
//                                                novels.remove(it);
//                                                break;
//                                            }
//                                        }
//                                        ((BigLibrary)entry.getValue()).setNovels(novels);
//
//                                    } else {
//                                        List<Integer> manuals = ((BigLibrary)entry.getValue()).getManuals();
//                                        for (Integer man: manuals){
//                                            if(man.equals(it)){
//                                                manuals.remove(it);
//                                                break;
//                                            }
//                                        }
//                                        ((BigLibrary)entry.getValue()).setManuals(manuals);
//                                    }
//
//                                    ((BigLibrary)entry.getValue()).removeFromStock(it);
//                                    flagb = true;
//                                    break;
//                                }
//                            }

                            for (Integer it: books) {
                                if (bookService.getTitleById(it).equalsIgnoreCase(option)) {
                                    Book carte = bookService.getBookById(it);
                                    if (carte instanceof Novel){

                                        List<Integer> novels = ((BigLibrary)entry.getValue()).getNovels();
                                        for (Integer nov: novels){
                                            if(nov.equals(it)){
                                                novels.remove(it);
                                                break;
                                            }
                                        }
                                        ((BigLibrary)entry.getValue()).setNovels(novels);

                                    } else {
                                        List<Integer> manuals = ((BigLibrary)entry.getValue()).getManuals();
                                        for (Integer man: manuals){
                                            if(man.equals(it)){
                                                manuals.remove(it);
                                                break;
                                            }
                                        }
                                        ((BigLibrary)entry.getValue()).setManuals(manuals);
                                    }

                                    ((BigLibrary)entry.getValue()).removeFromStock(it);
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
                        List<Integer> books = ((NovelLibrary)entry.getValue()).getBooks();
                        boolean flagb = false;
                        while (true) {
                            System.out.print("Introduce the name of the book you want to remove: ");
                            String option = var.nextLine();

                            for (Integer it: books) {
                                if (bookService.getTitleById(it).equalsIgnoreCase(option)) {
                                    List<Integer> novels = ((NovelLibrary)entry.getValue()).getNovels();
                                    for (Integer nov: novels){
                                        if(nov.equals(it)){
                                            novels.remove(it);
                                            break;
                                        }
                                    }
                                    ((NovelLibrary)entry.getValue()).setNovels(novels);

                                    ((NovelLibrary)entry.getValue()).removeFromStock(it);
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
                        List<Integer> books = ((KidsLibrary)entry.getValue()).getBooks();
                        boolean flagb = false;
                        while (true) {
                            System.out.print("Introduce the name of the book you want to remove: ");
                            String option = var.nextLine();

                            for (Integer it: books) {
                                if (bookService.getTitleById(it).equalsIgnoreCase(option)) {

                                    List<Integer> cbooks = ((KidsLibrary)entry.getValue()).getChildBooks();
                                    for (Integer cb: cbooks){
                                        if(cb.equals(it)){
                                            cbooks.remove(it);
                                            break;
                                        }
                                    }
                                    ((KidsLibrary)entry.getValue()).setBooks(cbooks);

                                    ((KidsLibrary)entry.getValue()).removeFromStock(it);
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
        audit.WriteTimestamp("Add offer");
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

                        Integer offer = offerService.readBigOffer();

                        List<Integer> offers = ((BigLibrary)entry.getValue()).getOffers();
                        offers.add(offer);
                        List<Integer> aux = (List<Integer>)(List<?>) offers;
                        ((BigLibrary)entry.getValue()).setOffers(offers);

                        System.out.println("Offer added successfully!\n");

//                        System.out.print("Introduce the stock of the offer: ");
//                        int stock = var.nextInt();
//                        ((BigLibrary)entry.getValue()).updateStock(offer, stock);

                    } else if (entry.getValue() instanceof KidsLibrary) {

                        // name, manuals, price, book, toy

                        System.out.println("Kids library");

                        Integer offer = offerService.readKidsOffer();

                        List<Integer> offers = ((KidsLibrary)entry.getValue()).getOffers();
                        offers.add(offer);
                        List<Integer> aux = (List<Integer>)(List<?>) offers;
                        ((KidsLibrary)entry.getValue()).setOffers(aux);

                        System.out.println("Offer added successfully!\n");


                    } else {
                        System.out.println("This library doesn't accept offers.");
                    }

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
        audit.WriteTimestamp("Add order");
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
        audit.WriteTimestamp("Cancel order");
        Scanner var = new Scanner(System.in);
        System.out.print("Please insert the ID of the order you want to cancel: ");

        int option = 0;
        while (true) {
            try {
                option = var.nextInt();
                if (this.orders.containsKey(option)){
                    this.orders.remove(option);
                    System.out.println("Order removed successfully!\n");
                    break;
                } else {
                    throw new IndexOutOfBoundsException("ID not in list.");
                }
            } catch (InputMismatchException e) {
                var.next();
                System.out.print("Please insert a number: ");
            } catch (Exception e) {
                System.out.print("Order ID " + option + " doesn't exist.");
            }
        }

    }

    public void sortLibraries(){
        audit.WriteTimestamp("Print sorted libraries");
        Set<Map.Entry<Integer, Library>> set = new TreeSet<>(new Sort());
        set.addAll(this.shops.entrySet());

        for (Map.Entry<Integer, Library> entry : set) {
            System.out.println(entry.getValue().getName() + " - rating: " + entry.getValue().getRating());
        }
    }

    public void rateLibrary(){
        audit.WriteTimestamp("Rate a library");
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
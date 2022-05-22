package com.company.service;

import com.company.offer.BigOffer;
import com.company.offer.KidsOffer;
import com.company.offer.Offer;

import java.util.*;

public class OfferService {
    private static OfferService single_instance = null;
    private BookService bookService;
    private Map<Integer, Offer> offers;
    private int offerId = 0;

    private OfferService() {
        this.offers = new HashMap<Integer, Offer>();
    }

    public static OfferService getInstance() {
        if (single_instance == null)
            single_instance = new OfferService();

        return single_instance;
    }

    public Integer readBigOffer() {
        Scanner var = new Scanner(System.in);
        bookService = BookService.getInstance();

        System.out.print("Offer's name: ");
        String name = var.nextLine();

        System.out.print("How many manuals does the offer have? ");

        int n = 0;
        while (true) {
            try {
                n = var.nextInt();
                if (n >= 0) {
                    break;
                }
            } catch (Exception e) {
                var.next();
                System.out.print("Please insert a valid number: ");
            }
        }

        System.out.println("List of manuals: ");
        List<Integer> allManuals = bookService.getManuals();
        for (int j = 0; j < allManuals.size(); j++) {
            System.out.println((j + 1) + ". " + bookService.getTitleById(allManuals.get(j)) + ", " + bookService.getAuthorById(allManuals.get(j)));
        }
        List<Integer> manuals = new ArrayList<Integer>();

        for (int i = 0; i < n; i++) {
            System.out.print("Introduce the id of the manual you want to add: ");
            while (true) {
                try {
                    int alege = var.nextInt() - 1;
                    int id = allManuals.get(alege);
                    if (allManuals.contains(id) && !manuals.contains(id)) {
                        manuals.add(id);
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
        }

        System.out.print("How many novels does the offer have? ");

        while (true) {
            try {
                n = var.nextInt();
                if (n >= 0) {
                    break;
                }
            } catch (Exception e) {
                var.next();
                System.out.print("Please insert a valid number: ");
            }
        }
        List<Integer> novels = new ArrayList<Integer>();
        System.out.println("List of novels: ");
        List<Integer> allNovels = bookService.getNovels();
        for (int j = 0; j < allNovels.size(); j++) {
            System.out.println((j + 1) + ". " + bookService.getTitleById(allNovels.get(j)) + ", " + bookService.getAuthorById(allNovels.get(j)));
        }

        for (int i = 0; i < n; i++) {
            System.out.print("Introduce the id of the novel you want to add: ");

            while (true) {
                try {
                    int id = var.nextInt();
                    if (allNovels.contains(id) && !novels.contains(id)) {
                        novels.add(id);
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

        double totalPrice = 0;
        for (Integer man: manuals) {
            totalPrice += bookService.getPriceById(man);
        }
        for (Integer nov: novels) {
            totalPrice += bookService.getPriceById(nov);
        }

        BigOffer offer = new BigOffer(name, manuals, novels);
        offers.put(offerId++, offer);
        offer.setPrice(totalPrice);

        return offerId - 1;
    }

    public Integer readKidsOffer() {
        Scanner var = new Scanner(System.in);
        bookService = BookService.getInstance();

        System.out.print("Offer's name: ");
        String name = var.nextLine();

        System.out.print("Offer's toy (puppet/car/ball): ");

        String toy;
        while(true) {
            toy = var.nextLine();
            if (!(toy.equalsIgnoreCase("puppet") || toy.equalsIgnoreCase("car") || toy.equalsIgnoreCase("ball"))) {
                System.out.print("This toy doesn't exist. Please try again: ");
            } else {
                break;
            }
        }

        System.out.print("How many manuals does the offer have? ");

        int n = 0;
        while (true) {
            try {
                n = var.nextInt();
                if (n >= 0) {
                    break;
                }
            } catch (Exception e) {
                var.next();
                System.out.print("Please insert a valid number: ");
            }
        }

        System.out.println("List of manuals: ");
        List<Integer> allManuals = bookService.getManuals();
        for (int j = 0; j < allManuals.size(); j++) {
            System.out.println((j + 1) + ". " + bookService.getTitleById(allManuals.get(j)) + ", " + bookService.getAuthorById(allManuals.get(j)));
        }
        List<Integer> manuals = new ArrayList<Integer>();

        for (int i = 0; i < n; i++) {
            System.out.print("Introduce the id of the manual you want to add: ");
//            Manual manual = bookService.readManual();
//            manuals.add(manual);
            while (true) {
                try {
                    int alege = var.nextInt() - 1;
                    int id = allManuals.get(alege);
                    if (allManuals.contains(id) && !manuals.contains(id)) {
                        manuals.add(id);
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
        }

        System.out.println("List of child books: ");
        List<Integer> allChild = bookService.getChildBooks();
        for (int j = 0; j < allChild.size(); j++) {
            System.out.println((j + 1) + ". " + bookService.getTitleById(allChild.get(j)) + ", " + bookService.getAuthorById(allChild.get(j)));
        }
        System.out.print("Introduce the id of the child book you want to add: ");

        int id;
        while (true) {
            try {
                int alege = var.nextInt() - 1;
                id = allChild.get(alege);
                if (allChild.contains(id)) {
                    break;
                } else {
                    throw new IndexOutOfBoundsException("ID not in list.");
                }
            } catch (InputMismatchException e) {
                var.next();
                System.out.print("Please insert a number: ");
            } catch(Exception e) {
                System.out.print("The book you wanted to add is not a child book. Please try again: ");
            }
        }

        double totalPrice = 0;
        for (Integer man: manuals) {
            totalPrice += bookService.getPriceById(man);
        }

        totalPrice += bookService.getPriceById(id);

        KidsOffer offer = new KidsOffer(name, manuals, id, toy);

        offers.put(offerId++, (Offer) offer);
        offer.setPrice(totalPrice);

        return offerId - 1;

    }

    public BigOffer orderBigOffer(BigOffer choice) {
        // BigOffer are romane si manuale

        Scanner var = new Scanner(System.in);
        System.out.println("List of novels to choose from: ");

        int cnt = 1;
        for (Integer it: choice.getNovels()){
            System.out.println("Novel number " + cnt + ":");
            cnt ++;
            System.out.println(bookService.getBookById(it));
        }
        System.out.print("Choose a novel number: ");

        List<Integer> n = new ArrayList<Integer>();
        while (true) {
            try {
                int option = var.nextInt() - 1;
                if (option >= 0 && option <= choice.getNovels().size()) {
                    n.add(option);
                    break;
                }
                else {
                    throw new IndexOutOfBoundsException("ID not in list.");
                }
            } catch (InputMismatchException e) {
                var.next();
                System.out.print("Please insert a number: ");
            } catch (Exception e) {
                System.out.print("This id is not in list. Please try again: ");
            }
        }

        System.out.println("List of manuals to choose from: ");

        cnt = 1;
        for (Integer it: choice.getManuals()){
            System.out.println("Manual number " + cnt + ":");
            cnt ++;
            System.out.println(bookService.getBookById(it));
        }
        System.out.print("Choose a manual number: ");

        List<Integer> m = new ArrayList<Integer>();
        while (true) {
            try {
                int option = var.nextInt() - 1;
                var.nextLine();

                if (option >= 0 && option <= choice.getManuals().size()) {
                    m.add(option);
                    break;
                } else {
                    throw new IndexOutOfBoundsException("ID not in list.");
                }
            } catch (InputMismatchException e) {
                var.next();
                System.out.print("Please insert a number: ");
            } catch (Exception e) {
                System.out.print("This id is not in list. Please try again: ");
            }
        }

        return new BigOffer(choice.getName(), m, n);
    }

    public KidsOffer orderKidsOffer (KidsOffer choice){
        Scanner var = new Scanner(System.in);

        System.out.println("List of manuals to choose from: ");

        int cnt = 1;
        for (Integer it: choice.getManuals()){
            System.out.println("Manual number " + cnt + ":");
            cnt ++;
            System.out.println(bookService.getBookById(it));
        }
        System.out.print("Choose a manual number: ");
        List<Integer> m = new ArrayList<Integer>();
        while (true) {
            try {
                int option = var.nextInt() - 1;
                var.nextLine();

                if (option >= 0 && option <= choice.getManuals().size()) {
                    m.add(option);
                    break;
                } else {
                    throw new IndexOutOfBoundsException("ID not in list.");
                }
            } catch (InputMismatchException e) {
                var.next();
                System.out.print("Please insert a number: ");
            } catch (Exception e) {
                System.out.print("This id is not in list. Please try again: ");
            }
        }

        return new KidsOffer(choice.getName(), m, choice.getChildBook(), choice.getToy());
    }

    public Map<Integer, Offer> getOffers() {
        return offers;
    }

    public List<Integer> getKidsOffers () {
        List <Integer> kids = new ArrayList<Integer>();
        Set set = offers.entrySet();

        for (Object o: set) {
            Map.Entry entry = (Map.Entry) o;
            if (entry.getValue() instanceof KidsOffer) {
                kids.add((Integer) entry.getKey());
            }
        }

        return kids;
    }

    public List<Integer> getBigOffers () {
        List <Integer> bigs = new ArrayList<Integer>();
        Set set = offers.entrySet();

        for (Object o: set) {
            Map.Entry entry = (Map.Entry) o;
            if (entry.getValue() instanceof BigOffer) {
                bigs.add((Integer) entry.getKey());
            }
        }

        return bigs;
    }

    public String getNameById (int id) {
        Set set = offers.entrySet();

        for (Object o: set) {
            Map.Entry entry = (Map.Entry) o;
            if ((Integer) entry.getKey() == id) {
                return ((Offer) entry.getValue()).getName();
            }
        }

        return null;
    }

    public double getPriceById(int id) {
        Set set = offers.entrySet();

        for (Object o : set) {
            Map.Entry entry = (Map.Entry) o;
            if ((Integer) entry.getKey() == id) {
                return ((Offer)entry.getValue()).getPrice();
            }
        }

        return 0;
    }

    public Offer getOfferById(int id) {
        Set set = offers.entrySet();

        for (Object o : set) {
            Map.Entry entry = (Map.Entry) o;
            if ((Integer) entry.getKey() == id) {
                return ((Offer)entry.getValue());
            }
        }

        return null;
    }


}

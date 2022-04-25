package com.company.service;

import java.util.Scanner;

public class Application { // singleton
    private static Application single_instance = null;

    private Application(){}

    public static synchronized Application getInstance(){
        if (single_instance == null) {
            single_instance = new Application();
        }
        return single_instance;
    }

    private void listOfActionsForUser(Service service) {
        System.out.println("-> Signed as " + service.getCurrentUserEmail());
        System.out.println("~~ List of options ~~");
        System.out.println("0. List all options"); //done
        System.out.println("1. List libraries"); //done
        System.out.println("2. List one library"); //done
        System.out.println("3. Sort libraries by rating "); //
        System.out.println("4. Place an order"); // done
        System.out.println("5. Cancel an order"); // done
        System.out.println("6. Sign out"); //done
        System.out.println("7. Rate one library"); //
        System.out.println("8. Exit"); //done
    }

    private void userActions(Service service){
        Scanner var = new Scanner(System.in);
        this.listOfActionsForUser(service);
        boolean out = false;

        while(true) {
            System.out.print("Please choose an option from 0 to 8: ");
            int choice = var.nextInt();
            var.nextLine();
            System.out.println();


            switch (choice) {
                case 0:
                    this.listOfActionsForUser(service);
                    break;
                case 1:
                    //afisare magazine
                    service.listLibraries();
                    break;
                case 2:
                    //afisare un singur magazin dupa nume
                    service.listLibrary();
                    break;
                case 3:
                    //sortare magazine descrescator dupa rating
                    service.sortLibraries();
                    break;
                case 4:
                    //plasare comanda
                    service.addOrder();
                    break;
                case 5:
                    //anulare comanda
                    service.cancelOrder();
                    break;
                case 6:
                    //delogare
                    service.signOut();
                    start();
                    out = true;
                    break;
                case 7:
                    //adaugare rating magazin
                    service.rateLibrary();
                    break;
                case 8:
                    //exit
                    out = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }

            if (out) {
                break;
            }
        }

    }

    private void listOfActionsForAdmin(Service service){
        System.out.println("-> Signed as admin");
        System.out.println("~~ List of options ~~");
        System.out.println("0. List all options"); //d one
        System.out.println("1. Add library"); // done
        System.out.println("2. Remove library"); // done
        System.out.println("3. Add a book"); // done
        System.out.println("4. Delete a book"); // done
        System.out.println("5. Add an offer"); // done
        System.out.println("6. Sign out"); // done
        System.out.println("7. List libraries"); // done
        System.out.println("8. List one library"); // done
        System.out.println("9. Exit"); //done
    }

    private void adminActions(Service service) {
        Scanner var = new Scanner(System.in);
        this.listOfActionsForAdmin(service);

        while(true) {
            System.out.print("Please choose an option from 0 to 9: ");
            int choice = var.nextInt();
            var.nextLine();
            System.out.println();
            boolean out = false;

            switch (choice) {
                case 0:
                    this.listOfActionsForAdmin(service);
                    break;
                case 1:
                    //adaugare magazin
                    service.addLibrary();
                    break;
                case 2:
                    //remove library
                    service.removeLibrary();
                    break;
                case 3:
                    //adauga o carte
                    service.addBook();
                    break;
                case 4:
                    //stergere carte
                    service.removeBook();
                    break;
                case 5:
                    //adauga oferta
                    service.addOffer();
                    break;
                case 6:
                    //delogare
                    service.signOut();
                    start();
                    out = true;
                    break;
                case 7:
                    //list libraries
                    service.listLibraries();
                    break;
                case 8:
                    //list one library
                    service.listLibrary();
                    break;
                case 9:
                    //exit
                    out = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }

            if (out) {
                break;
            }
        }
    }

    public void start() {
        Service service = Service.getInstance();

        if(service.singIn() == 1){
            // logged in as admin
            this.adminActions(service);
        }
        else {
            // logged in as user
            this.userActions(service);
        }

    }
}

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

        while(true) {
            System.out.print("Please choose an option from 0 to 8: ");
            int choice = var.nextInt();
            var.nextLine();
            System.out.println();

            if (choice == 0) {
                this.listOfActionsForUser(service);
            } else if (choice == 1) {
                //afisare magazine
                service.listLibraries();
            } else if (choice == 2) {
                //afisare un singur magazin dupa nume
                service.listLibrary();
            } else if (choice == 3) {
                //sortare magazine descrescator dupa rating
                service.sortLibraries();
            } else if (choice == 4) {
                //plasare comanda
                service.addOrder();
            } else if (choice == 5) {
                //anulare comanda
                service.cancelOrder();
            } else if (choice == 6) {
                //delogare
                service.signOut();
                start();
                break;
            } else if (choice == 7) {
                //adaugare rating magazin
                service.rateLibrary();
            } else if (choice == 8 ){
                //exit
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
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

            if (choice == 0) {
                this.listOfActionsForAdmin(service);
            } else if (choice == 1) {
                //adaugare magazin
                service.addLibrary();
            } else if (choice == 2) {
                //remove library
                service.removeLibrary();
            } else if (choice == 3) {
                //adauga o carte
                service.addBook();
            } else if (choice == 4) {
                //stergere carte
                service.removeBook();
            } else if (choice == 5) {
                //adauga oferta
                service.addOffer();
            } else if (choice == 6) {
                //delogare
                service.signOut();
                start();
                break;
            } else if (choice == 7) {
                //list libraries
                service.listLibraries();
            } else if (choice == 8) {
                //list one library
                service.listLibrary();
            } else if (choice == 9 ){
                //exit
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
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

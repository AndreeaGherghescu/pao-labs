package com.company.service;

import com.company.library.BigLibrary;
import com.company.library.KidsLibrary;
import com.company.library.Library;
import com.company.library.NovelLibrary;
import com.company.offer.BigOffer;
import com.company.product.Book;
import com.company.product.ChildBook;
import com.company.product.Manual;
import com.company.product.Novel;
import com.company.user.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ReaderCSV<T> {

    private int bookId;
    private Set<User> usersReg;
    private BookService bookService;
    private Login login;
    private static boolean flag = false;

    public ReaderCSV(){
        this.usersReg = new HashSet<User>();
        if (!flag) {
            readUsers();
            flag = true;
        }

    }


    public List<Book> readBooks (String path, Class<T> instanceOf) {

        switch (instanceOf.toString()) {
            case "class com.company.product.Novel":
                return readNovelsCSV(path);
            case "class com.company.product.Manual":
                return readManualsCSV(path);
            case "class com.company.product.ChildBook":
                return readChildBooksCSV(path);
        }

        return null;
    }

    public List<Book> readNovelsCSV(String path) {
        List<Book> novels = new ArrayList<Book>();
        bookService = BookService.getInstance();

        try {
            BufferedReader buffer = new BufferedReader(new FileReader(path));

            String line = buffer.readLine();
            line = buffer.readLine();

            while (line != null) {

                String[] array = line.split(",");
                int k = 0;

                String title = array[k++];
                String author = array[k++];
                double price = Double.parseDouble(array[k++]);
                int edition = Integer.parseInt(array[k++]);
                String genre = array[k++];

                Novel novel = new Novel(title, author, price, edition, genre);
                novels.add(novel);
                line = buffer.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return novels;
    }

    public List<Book> readManualsCSV (String path) {
        List<Book> manuals = new ArrayList<>();
        bookService = BookService.getInstance();

        try {
            BufferedReader buffer = new BufferedReader(new FileReader(path));

            String line = buffer.readLine();
            line = buffer.readLine();

            while (line != null) {

                String[] array = line.split(",");
                int k = 0;

                String title = array[k++];
                String author = array[k++];
                double price = Double.parseDouble(array[k++]);
                String subject = array[k++];
                int grade = Integer.parseInt(array[k++]);

                Manual manual = new Manual(title, author, price, subject, grade);
                manuals.add(manual);

                line = buffer.readLine();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return manuals;
    }

    public List<Book> readChildBooksCSV (String path) {
        List<Book> childBooks = new ArrayList<>();
        bookService = BookService.getInstance();

        try {
            BufferedReader buffer = new BufferedReader(new FileReader(path));

            String line = buffer.readLine();
            line = buffer.readLine();

            while (line != null) {

                String[] array = line.split(",");
                int k = 0;

                String title = array[k++];
                String author = array[k++];
                double price = Double.parseDouble(array[k++]);
                int minAge = Integer.parseInt(array[k++]);

                ChildBook childBook = new ChildBook(title, author, price, minAge);
                childBooks.add(childBook);

                line = buffer.readLine();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return childBooks;
    }

    private void readUsers() {
        login = Login.getInstance();
        User admin = new User("admin", "admin@gmail.com", "0765743376", "parolaaa");
        this.usersReg.add(admin);
        login.insertUser(admin);

        // citesc userii din CSV
        try {
            String path = "files/Users.csv";
            BufferedReader buffer = new BufferedReader(new FileReader(path));

            String line = buffer.readLine();
            line = buffer.readLine();

            while (line != null) {
                String[] array = line.split(",");
                User user = new User(array[0], array[1], array[2], array[3]);
                this.usersReg.add(user);
                login.insertUser(user);
                line = buffer.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<User> getUsersReg() {
        return usersReg;
    }

}

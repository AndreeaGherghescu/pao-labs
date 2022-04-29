package com.company.service;

import com.company.library.Library;
import com.company.product.Book;
import com.company.product.ChildBook;
import com.company.product.Manual;
import com.company.product.Novel;
import com.sun.source.tree.LambdaExpressionTree;

import java.util.*;

public class BookService {
    private static BookService single_instance = null;
    private HashMap<Integer, Book> books;
    private int bookId;

    private BookService() {}

    public static BookService getInstance() {
        if (single_instance == null)
            single_instance = new BookService();

        return single_instance;
    }

    public void readBooks () {

        bookId = 0;
        this.books = new HashMap<Integer, Book>();

        List<Book> novels = new ArrayList<Book>();
        List<Book> manuals = new ArrayList<Book>();
        List<Book> childBooks = new ArrayList<Book>();

        novels = new ReaderCSV<Novel>().readBooks("Files/Novels.csv", Novel.class);
        manuals = new ReaderCSV<Manual>().readBooks("Files/Manuals.csv", Manual.class);
        childBooks = new ReaderCSV<ChildBook>().readBooks("Files/ChildBooks.csv", ChildBook.class);

        for (Book it: novels) {
            this.books.put(bookId++, it);
        }

        for (Book it: manuals) {
            this.books.put(bookId++, it);
        }

        for (Book it: childBooks)  {
            this.books.put(bookId++, it);
        }

    }

    public Integer readChildBook() {
        Scanner var = new Scanner(System.in);

        System.out.print("Title of the book is: ");
        String title = var.nextLine();

        System.out.print("Author of the book is: ");
        String author = var.nextLine();

        System.out.print("Price of the book is: ");
        double price = 0;
        boolean flag = true;
        while (flag) {
            try {
                flag = false;
                price = var.nextDouble();
            } catch (Exception e) {
                var.next();
                System.out.print("Please insert a number: ");
                flag = true;
            }
        }
        System.out.print("Minimum age for this book is: ");
        int age = 0;
        flag = true;
        while (flag) {
            try {
                flag = false;
                age = var.nextInt();
            } catch (Exception e) {
                flag = true;
                var.next();
                System.out.print("Please insert a number: ");
            }
        }

        var.nextLine();

        ChildBook childBook = new ChildBook(title, author, price, age);
        books.put(bookId++, childBook);
        new WriterCSV<ChildBook>().writeBooks(childBook);
        System.out.println();

        return bookId - 1;
    }

    public Integer readManual() {
        Scanner var = new Scanner(System.in);

        System.out.print("Title of the book is: ");
        String title = var.nextLine();

        System.out.print("Author of the book is: ");
        String author = var.nextLine();

        System.out.print("Price of the book is: ");
        double price = 0;
        boolean flag = true;
        while (flag) {
            try {
                flag = false;
                price = var.nextDouble();
            } catch (Exception e) {
                var.next();
                System.out.print("Please insert a number: ");
                flag = true;
            }
        }

        var.nextLine();

        System.out.print("Subject of the book is: ");
        String subject = var.nextLine();

        System.out.print("The book is suited for class: ");
        int grade = 0;
        flag = true;
        while (flag) {
            try {
                flag = false;
                grade = var.nextInt();
            } catch (Exception e) {
                var.next();
                System.out.print("Please insert a number: ");
                flag = true;
            }
        }

        Manual manual = new Manual(author, title, price, subject, grade);
        books.put(bookId++, manual);
        new WriterCSV<Manual>().writeBooks(manual);
        System.out.println();

        return bookId - 1;
    }

    public Integer readNovel() {
        Scanner var = new Scanner(System.in);

        System.out.print("Title of the book is: ");
        String title = var.nextLine();

        System.out.print("Author of the book is: ");
        String author = var.nextLine();

        System.out.print("Price of the book is: ");
        double price = 0;
        boolean flag = true;
        while (flag) {
            try {
                flag = false;
                price = var.nextDouble();
            } catch (Exception e) {
                var.next();
                System.out.print("Please insert a number: ");
                flag = true;
            }
        }

        System.out.print("Edition of the book is: ");
        int edition = 0;
        flag = true;
        while (flag) {
            try {
                flag = false;
                edition = var.nextInt();
            } catch (Exception e) {
                var.next();
                System.out.print("Please insert a number: ");
                flag = true;
            }
        }
        var.nextLine();

        System.out.print("Genre of the book is: ");
        String genre = var.nextLine();

        Novel novel = new Novel(title, author, price, edition, genre);
        books.put(bookId++, novel);
        new WriterCSV<Novel>().writeBooks(novel);
        System.out.println();

        return bookId - 1;
    }

    public HashMap<Integer, Book> getBooks() {
        return books;
    }

    public List<Integer> getChildBooks() {
        List<Integer> childBooks = new ArrayList<Integer>();
        Set set = books.entrySet();

        for (Object o : set) {
            Map.Entry entry = (Map.Entry) o;
            if (entry.getValue() instanceof ChildBook) {
                childBooks.add((Integer) entry.getKey());
            }
        }
        return childBooks;
    }

    public List<Integer> getNovels() {
        List<Integer> novels = new ArrayList<Integer>();
        Set set = books.entrySet();

        for (Object o : set) {
            Map.Entry entry = (Map.Entry) o;
            if (entry.getValue() instanceof Novel) {
                novels.add((Integer) entry.getKey());
            }
        }



        return novels;
    }

    public List<Integer> getManuals() {
        List<Integer> manuals = new ArrayList<Integer>();
        Set set = books.entrySet();

        for (Object o : set) {
            Map.Entry entry = (Map.Entry) o;
            if (entry.getValue() instanceof Manual) {
                manuals.add((Integer) entry.getKey());
            }
        }
        return manuals;
    }

    public String getTitleById(int id) {
        Set set = books.entrySet();

        for (Object o : set) {
            Map.Entry entry = (Map.Entry) o;
            if ((Integer) entry.getKey() == id) {
                return ((Book)entry.getValue()).getTitle();
            }
        }

        return null;
    }

    public double getPriceById(int id) {
        Set set = books.entrySet();

        for (Object o : set) {
            Map.Entry entry = (Map.Entry) o;
            if ((Integer) entry.getKey() == id) {
                return ((Book)entry.getValue()).getPrice();
            }
        }

        return 0;
    }

    public Book getBookById(int id) {
        Set set = books.entrySet();

        for (Object o : set) {
            Map.Entry entry = (Map.Entry) o;
            if ((Integer) entry.getKey() == id) {
                return ((Book)entry.getValue());
            }
        }

        return null;
    }
    public String getAuthorById(int id) {
        Set set = books.entrySet();

        for (Object o : set) {
            Map.Entry entry = (Map.Entry) o;
            if ((Integer) entry.getKey() == id) {
                return ((Book)entry.getValue()).getAuthor();
            }
        }

        return null;
    }
}

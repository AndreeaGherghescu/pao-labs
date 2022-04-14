package com.company.service;

import com.company.product.ChildBook;
import com.company.product.Manual;
import com.company.product.Novel;

import java.util.Scanner;

public class BookService {
    private static BookService single_instance = null;

    public static BookService getInstance()
    {
        if (single_instance == null)
            single_instance = new BookService();

        return single_instance;
    }

    public ChildBook readChildBook() {
        Scanner var = new Scanner(System.in);

        System.out.print("Title of the book is: ");
        String title = var.nextLine();

        System.out.print("Author of the book is: ");
        String author = var.nextLine();

        System.out.print("Price of the book is: ");
        double price = var.nextDouble();

        System.out.print("Minimum age for this book is: ");
        int age = var.nextInt();
        var.nextLine();

        ChildBook childBook = new ChildBook(title, author, price, age);
        System.out.println();

        return childBook;
    }

    public Manual readManual() {
        Scanner var = new Scanner(System.in);

        System.out.print("Title of the book is: ");
        String title = var.nextLine();

        System.out.print("Author of the book is: ");
        String author = var.nextLine();

        System.out.print("Price of the book is: ");
        double price = var.nextDouble();
        var.nextLine();

        System.out.print("Subject of the book is: ");
        String subject = var.nextLine();

        System.out.print("The book is suited for class: ");
        int grade = var.nextInt();

        Manual manual = new Manual(author, title, price, subject, grade);
        System.out.println();

        return manual;
    }

    public Novel readNovel() {
        Scanner var = new Scanner(System.in);

        System.out.print("Title of the book is: ");
        String title = var.nextLine();

        System.out.print("Author of the book is: ");
        String author = var.nextLine();

        System.out.print("Price of the book is: ");
        double price = var.nextDouble();

        System.out.print("Edition of the book is: ");
        int edition = var.nextInt();
        var.nextLine();

        System.out.print("Genre of the book is: ");
        String genre = var.nextLine();

        Novel novel = new Novel(title, author, price, edition, genre);
        System.out.println();

        return novel;
    }
}

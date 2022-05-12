package com.company.service;

import com.company.product.Book;
import com.company.product.ChildBook;
import com.company.product.Manual;
import com.company.product.Novel;
import com.company.user.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

public class WriterCSV<T> {

    private BufferedWriter buffer;
    public WriterCSV() {}
    public void writeUser(User user) {
        try {
            buffer = new BufferedWriter(new FileWriter("Files/Users.csv", true));
            new FileWriter("Files/Users.csv", true).close();
            buffer.write(",\n" + user.getName() + "," + user.getEmail() + "," + user.getPhoneNumber() + "," + user.getPassword());
            buffer.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeBooks (T book) {

        if (book instanceof Novel) {
            writeNovelCSV((Novel) book);
        } else if (book instanceof ChildBook) {
            writeChildBookCSV((ChildBook) book);
        } else {
            writeManualCSV((Manual) book);
        }

    }

    public void writeNovelCSV (Novel novel) {
        try {
            buffer = new BufferedWriter(new FileWriter("Files/Novels.csv", true));
            new FileWriter("Files/Novels.csv", true).close();
            buffer.write(",\n" + novel.getTitle() + "," + novel.getAuthor() + "," + novel.getPrice() + "," + novel.getEdition() + "," + novel.getGenre());
            buffer.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeManualCSV (Manual manual) {
        try {
            buffer = new BufferedWriter(new FileWriter("Files/Manuals.csv", true));
            new FileWriter("Files/Manuals.csv", true).close();
            buffer.write(",\n" + manual.getTitle() + "," + manual.getAuthor() + "," + manual.getPrice() + "," + manual.getSubject() + "," + manual.getGrade());
            buffer.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeChildBookCSV (ChildBook book) {
        try {
            buffer = new BufferedWriter(new FileWriter("Files/ChildBooks.csv", true));
            new FileWriter("Files/ChildBooks.csv", true).close();
            buffer.write(",\n" + book.getTitle() + "," + book.getAuthor() + "," + book.getPrice() + "," + book.getMinAge());
            buffer.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

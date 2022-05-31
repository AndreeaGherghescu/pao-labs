package com.company.service;

import com.company.config.DatabaseConfiguration;
import com.company.library.Library;
import com.company.product.Book;
import com.company.product.ChildBook;
import com.company.product.Manual;
import com.company.product.Novel;
import com.sun.source.tree.LambdaExpressionTree;

import java.sql.*;
import java.util.*;

public class BookService { // singleton
    private static BookService single_instance = null;
    private Map<Integer, Book> books;
    private int bookId;

    private BookService() {}

    public static BookService getInstance() {
        if (single_instance == null)
            single_instance = new BookService();

        return single_instance;
    }

    public void createTables() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS childBook " +
                "(id int PRIMARY KEY, " +
                "author varchar(40), " +
                "title varchar(40), " +
                "price double, " +
                "minage int)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        createTableSql = "CREATE TABLE IF NOT EXISTS novel " +
                "(id int PRIMARY KEY, " +
                "author varchar(40), " +
                "title varchar(40), " +
                "price double, " +
                "edition int, " +
                "genre varchar(20))";

        connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        createTableSql = "CREATE TABLE IF NOT EXISTS manual " +
                "(id int PRIMARY KEY, " +
                "author varchar(40), " +
                "title varchar(40), " +
                "price double, " +
                "subject varchar(20), " +
                "grade int)";

        connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // create
    public void insertNovel(Novel novel) {
        String insertBookSql = "INSERT INTO novel(id, author, title, price, edition, genre) VALUES(?, ?, ?, ?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertBookSql)) {
            preparedStatement.setInt(1, bookId++);
            preparedStatement.setString(2, novel.getAuthor());
            preparedStatement.setString(3, novel.getTitle());
            preparedStatement.setDouble(4, novel.getPrice());
            preparedStatement.setInt(5, novel.getEdition());
            preparedStatement.setString(6, novel.getGenre());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertChildBook(ChildBook book) {
        String insertBookSql = "INSERT INTO childBook(id, author, title, price, minage) VALUES(?, ?, ?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertBookSql)) {
            preparedStatement.setInt(1, bookId++);
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getTitle());
            preparedStatement.setDouble(4, book.getPrice());
            preparedStatement.setInt(5, book.getMinAge());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertManual(Manual manual) {
        String insertBookSql = "INSERT INTO manual(id, author, title, price, subject, grade) VALUES(?, ?, ?, ?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertBookSql)) {
            preparedStatement.setInt(1, bookId++);
            preparedStatement.setString(2, manual.getAuthor());
            preparedStatement.setString(3, manual.getTitle());
            preparedStatement.setDouble(4, manual.getPrice());
            preparedStatement.setString(5, manual.getSubject());
            preparedStatement.setInt(6, manual.getGrade());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // read
    // testes in service unde scrie modificat
    public Book getBookByIdSQL(int id) {

        String selectSql = "SELECT * FROM novel WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapToNovel(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        selectSql = "SELECT * FROM manual WHERE id=?";

        connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapToManual(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        selectSql = "SELECT * FROM childBook WHERE id=?";

        connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapToChildBook(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    private Novel mapToNovel(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return new Novel(resultSet.getString(2), resultSet.getString(3), resultSet.getDouble(4), resultSet.getInt(5), resultSet.getString(6));
        }
        return null;
    }
    private Manual mapToManual(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return new Manual(resultSet.getString(2), resultSet.getString(3), resultSet.getDouble(4), resultSet.getString(5), resultSet.getInt(6));
        }
        return null;
    }
    private ChildBook mapToChildBook(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return new ChildBook(resultSet.getString(2), resultSet.getString(3), resultSet.getDouble(4), resultSet.getInt(5));
        }
        return null;
    }

    // update
    public void updateManualGrade(Integer grade, int id) {
        String updateGradeSql = "UPDATE manual SET grade=? WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateGradeSql)) {
            preparedStatement.setInt(1, grade);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // delete
    public void removeManual() {
        List<Integer> manuals = getManuals();

        if (manuals.size() == 0) {
            System.out.println("There are no manuals.");
            return;
        }

        for (int i = 0; i < manuals.size(); i++) {
            System.out.println(manuals.get(i));
            System.out.println((i + 1) + ". " + getBookById(manuals.get(i)));
        }

        System.out.print("Please insert the id of the manual you want to remove: ");
        Scanner var = new Scanner(System.in);

        while (true) {
            try {
                int alege = var.nextInt() - 1;
                if (alege >= 0  && alege < manuals.size()) {
                    int id = manuals.get(alege);
                    removeManualById(id);
                    break;
                } else {
                    throw new ArithmeticException("Invalid choice.");
                }
            } catch (InputMismatchException e) {
                var.next();
                System.out.print("Please insert a number: ");
            } catch (Exception e) {
                System.out.print("Please insert a valid id: ");
            }
        }
        System.out.println("Manual removed successfully!");
    }
    public void removeManualById (int id) {
        String deleteSql ="DELETE FROM manual WHERE id=?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = databaseConnection.prepareStatement(deleteSql)){
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void removeNovel() {
        List<Integer> novels = getNovels();

        if (novels.size() == 0) {
            System.out.println("There are no novels.");
            return;
        }

        for (int i = 0; i < novels.size(); i++) {
            System.out.println((i + 1) + ". " + getBookById(novels.get(i)));
        }

        System.out.print("Please insert the id of the novel you want to remove: ");
        Scanner var = new Scanner(System.in);

        while (true) {
            try {
                int alege = var.nextInt() - 1;
                if (alege >= 0  && alege < novels.size()) {
                    int id = novels.get(alege);
                    removeNovelById(id);
                    break;
                } else {
                    throw new ArithmeticException("Invalid choice.");
                }
            } catch (InputMismatchException e) {
                var.next();
                System.out.print("Please insert a number: ");
            } catch (Exception e) {
                System.out.print("Please insert a valid id: ");
            }
        }
        System.out.println("Novel removed successfully!");
    }
    public void removeNovelById (int id) {
        String deleteSql ="DELETE FROM novel WHERE id=?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = databaseConnection.prepareStatement(deleteSql)){
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeChildBook() {
        List<Integer> childBooks = getChildBooks();

        if (childBooks.size() == 0) {
            System.out.println("There are no child books.");
            return;
        }

        for (int i = 0; i < childBooks.size(); i++) {
            System.out.println((i + 1) + ". " + getBookById(childBooks.get(i)));
        }

        System.out.print("Please insert the id of the child book you want to remove: ");
        Scanner var = new Scanner(System.in);

        while (true) {
            try {
                int alege = var.nextInt() - 1;
                if (alege >= 0  && alege < childBooks.size()) {
                    int id = childBooks.get(alege);
                    removeChildBookById(id);
                    break;
                } else {
                    throw new ArithmeticException("Invalid choice.");
                }
            } catch (InputMismatchException e) {
                var.next();
                System.out.print("Please insert a number: ");
            } catch (Exception e) {
                System.out.print("Please insert a valid id: ");
            }
        }
        System.out.println("Child book removed successfully!");
    }
    public void removeChildBookById (int id) {
        String deleteSql ="DELETE FROM childBook WHERE id=?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = databaseConnection.prepareStatement(deleteSql)){
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //
    public void readBooks () { // from CSV

        bookId = 0;
        this.books = new HashMap<Integer, Book>();

        List<Book> novels = new ArrayList<Book>();
        List<Book> manuals = new ArrayList<Book>();
        List<Book> childBooks = new ArrayList<Book>();

        novels = new ReaderCSV<Novel>().readBooks("files/Novels.csv", Novel.class);
        manuals = new ReaderCSV<Manual>().readBooks("files/Manuals.csv", Manual.class);
        childBooks = new ReaderCSV<ChildBook>().readBooks("files/ChildBooks.csv", ChildBook.class);

        for (Book it: novels) {
            this.books.put(bookId, it);
            insertNovel((Novel) it);
        }

        for (Book it: manuals) {
            this.books.put(bookId, it);
            insertManual((Manual) it);
        }

        for (Book it: childBooks)  {
            this.books.put(bookId, it);
            insertChildBook((ChildBook) it);
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
        books.put(bookId, childBook);
        insertChildBook(childBook);
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
        books.put(bookId, manual);
        insertManual(manual);
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
        books.put(bookId, novel);
        insertNovel(novel);
        new WriterCSV<Novel>().writeBooks(novel);
        System.out.println();

        return bookId - 1;
    }

    public Map<Integer, Book> getBooks() {
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

package com.company.service;

import com.company.config.DatabaseConfiguration;
import com.company.product.Novel;
import com.company.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class Login { // singleton
    private Set<User> users;
    private static Login single_instance = null;
    private User curentUser;

    private Login(){
        this.users = new HashSet<User>();
        this.curentUser = null;
    }

    public static Login getInstance()
    {
        if (single_instance == null)
            single_instance = new Login();

        return single_instance;
    }

    public void createTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS user " +
                "(id int PRIMARY KEY AUTO_INCREMENT, " +
                "name varchar(40), " +
                "email varchar(50), " +
                "phoneNr varchar(14), " +
                "password varchar(30))";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertUser(User user) {
        String insertUserSql = "INSERT INTO user(name, email, phoneNr, password) VALUES(?, ?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertUserSql)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPhoneNumber());
            preparedStatement.setString(4, user.getPassword());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean signIn(String email, String password){
        if(users != null) {
            for (User it : users)
                if (email.equals(it.getEmail()) && password.equals(it.getPassword())) {
                    this.curentUser = it;
                    return true;
                }
        }
        return false;
    }

    //inscriere
    public boolean signUp(User u){
        if(users != null) {
            for (User it : users)
                if ((u.getEmail()).equals(it.getEmail()) && (u.getPassword()).equals(it.getPassword()))
                    return false;
        }
        this.users.add(u);
        insertUser(u);
        return true;
    }

    public User getCurentUser() {
        return curentUser;
    }

    public void setCurentUser(User curentUser) {
        this.curentUser = curentUser;
    }

    public void setUsersReg(Set<User> usersReg) {
        this.users = usersReg;
    }


}

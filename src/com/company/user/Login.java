package com.company.user;

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

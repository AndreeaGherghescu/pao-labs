package com.company;

import com.company.config.DatabaseConfiguration;
import com.company.service.Application;

public class Main {

    public static void main(String[] args) {
        Application application = Application.getInstance();
        application.start();

        DatabaseConfiguration.closeDatabaseConnection();
    }
}

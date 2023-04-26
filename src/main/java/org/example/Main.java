package org.example;

import org.example.air_insert_into.Inserter;
import org.example.air_manager.ConnectionManager;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        Connection connection = ConnectionManager.getConnection();


        Inserter inserter = new Inserter();
        inserter.insertCompanyTable(connection);
        inserter.insertAddressTable(connection);
        inserter.insertPassengerTable(connection);
        inserter.insertTripTable(connection);
        inserter.insertPassInTripTable(connection);



        ConnectionManager.closeConnection();
    }
}


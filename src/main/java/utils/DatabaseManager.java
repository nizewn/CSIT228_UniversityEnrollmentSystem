package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Singleton pattern, salamat chatgpt

/*
    basically, di nata kailangan mag instantiate or mag create2 og bago
    nga DatabaseManager for each page, mogamit ratag .getInstance().getConnection()
    (example usage sa UserManager.java)

    para ni efficient sya para usa ra ka connection sa whole app,
    di nata mag reconnect2 for each page hehe chat lng if paexplain pamo
 */
public class DatabaseManager {

    private static DatabaseManager instance;
    private Connection connection;

    private DatabaseManager() {
        System.out.println("Instantiating DatabaseManager. this should only print once");
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/g2group1", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}

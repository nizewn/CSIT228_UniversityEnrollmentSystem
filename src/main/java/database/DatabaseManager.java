package database;

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
        try {
            // Initialize and establish the database connection
            connection = DriverManager.getConnection("jdbc:sqlite:path/to/database.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            // if wala patay instance, edi instantiate for the first time
            // pero if naa nay instance daan, kato ra sya ireturn balik, no need maghimog lain
            synchronized (DatabaseManager.class) {
                if (instance == null) {
                    instance = new DatabaseManager();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}

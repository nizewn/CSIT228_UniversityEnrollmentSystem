package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    public Connection getDatabaseConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:universityenrollmentsystem.db");
    }
}
// TODO

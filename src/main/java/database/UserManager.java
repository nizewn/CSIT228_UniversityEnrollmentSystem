package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import entities.User;

public class UserManager {

    private Connection connection;

    public UserManager() {
        connection = DatabaseManager.getInstance().getConnection();
    }

    // TODO
    public User createStudent() {
        PreparedStatement statement = connection.prepareStatement()
    }
}

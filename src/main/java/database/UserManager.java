package database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import entities.User;

public class UserManager {

    private final DatabaseManager manager;
    private final Connection connection;

    public UserManager() {
        manager = DatabaseManager.getInstance();
        connection = manager.getConnection();
    }

    // int ang type kay userid iya ireturn, nya 0 ang ireturn if failed
    public int createUser(String type, String username, String password, String lastName, String firstName,
            char gender, String birthDate, String address, String contactNo, String email) {

        String sql = "INSERT INTO user (type, username, password, lastname, firstname, gender, birthdate, address, contactno, email) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING userid";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, type);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.setString(4, lastName);
            statement.setString(5, firstName);
            statement.setString(6, String.valueOf(gender));
            statement.setString(7, birthDate);
            statement.setString(8, address);
            statement.setString(9, contactNo);
            statement.setString(10, email);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getInt("userid");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public ArrayList<User> getAllUsers() {
        String sql = "SELECT * FROM users;";
        try {
            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            ArrayList userList = new ArrayList<User>();

            while (result.next()) {
                User user = new User(
                        result.getInt("userid"),
                        result.getString("type"),
                        result.getString("username"),
                        result.getString("email"),
                        result.getString("lastname"),
                        result.getString("firstname"),
                        result.getInt("tuitionfee"));
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUser(int userid) {
        String sql = "SELECT * FROM users WHERE userid = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, userid);

            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {
                User user = new User(
                        result.getInt("userid"),
                        result.getString("type"),
                        result.getString("username"),
                        result.getString("email"),
                        result.getString("lastname"),
                        result.getString("firstname"),
                        result.getInt("tuitionfee"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // if successful ang login, moreturn ang User, otherwise null ang ireturn
    public User loginUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {
                User user = new User(
                        result.getInt("userid"),
                        result.getString("type"),
                        result.getString("username"),
                        result.getString("email"),
                        result.getString("lastname"),
                        result.getString("firstname"),
                        result.getInt("tuitionfee"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    // TODO: update & delete(?) user
    // pwede sad mag add og searchUser gamit LIKE keyword sa sql, depende ninyo
}

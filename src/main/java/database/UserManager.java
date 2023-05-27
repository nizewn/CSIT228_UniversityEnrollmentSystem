package database;

import entities.User;
import utils.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;

public class UserManager {

    private final Connection connection;

    public UserManager() {
        DatabaseManager manager = DatabaseManager.getInstance();
        connection = manager.getConnection();
    }

    public User createUser(boolean admin, String username, String password, String lastName, String firstName, String email) {

        String sql = "INSERT INTO users (admin, username, password, lastname, firstname, email) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setBoolean(1, admin);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.setString(4, lastName);
            statement.setString(5, firstName);
            statement.setString(6, email);

            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();

            if (result.next()) {
                return new User(
                        result.getInt(1),
                        admin,
                        username,
                        email,
                        lastName,
                        firstName,
                        0);

            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        try {
            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            ArrayList<User> userList = new ArrayList<User>();

            while (result.next()) {
                User user = new User(
                        result.getInt("userid"),
                        result.getBoolean("admin"),
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
        String sql = "SELECT * FROM users WHERE userid = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, userid);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return new User(
                        result.getInt("userid"),
                        result.getBoolean("admin"),
                        result.getString("username"),
                        result.getString("email"),
                        result.getString("lastname"),
                        result.getString("firstname"),
                        result.getInt("tuitionfee"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User loginUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return new User(
                        result.getInt("userid"),
                        result.getBoolean("admin"),
                        result.getString("username"),
                        result.getString("email"),
                        result.getString("lastname"),
                        result.getString("firstname"),
                        result.getInt("tuitionfee"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE users SET admin = ?, username = ?, lastname = ?, firstname = ?, email = ?, tuitionfee = ? WHERE userid = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setBoolean(1, user.isAdmin());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getFirstName());
            statement.setString(5, user.getEmail());
            statement.setInt(6, user.getTuitionFee());
            statement.setInt(7, user.getId());

            int result = statement.executeUpdate();

            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePassword(int userId, String password) {
        String sql = "UPDATE users SET password = ? WHERE userid = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, password);
            statement.setInt(2, userId);

            int result = statement.executeUpdate();

            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(int userid) {
        String sql = "DELETE FROM users WHERE userid = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, userid);

            int result = statement.executeUpdate();

            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // pwede sad mag add og searchUser gamit LIKE keyword sa sql, depende ninyo
}

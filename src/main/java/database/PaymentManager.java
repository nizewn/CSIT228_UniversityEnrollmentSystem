package database;

import entities.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class PaymentManager {

    private final Connection connection;

    public PaymentManager() {
        DatabaseManager manager = DatabaseManager.getInstance();
        connection = manager.getConnection();
    }

    public Payment createPayment(int userId, int adminId, int amount, Date date) {
        String sql = "INSERT INTO payments (userid, adminid, amount, date) "
                + "VALUES (?, ?, ?, ?) RETURNING paymentid";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, userId);
            statement.setInt(2, adminId);
            statement.setInt(3, amount);
            statement.setTimestamp(4, new java.sql.Timestamp(date.getTime()));

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                UserManager userManager = new UserManager();
                return new Payment(
                        result.getInt("paymentid"),
                        amount,
                        date,
                        userManager.getUser(userId),
                        userManager.getUser(adminId));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Payment getPayment(int paymentId) {
        String sql = "SELECT * FROM payments WHERE paymentid = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, paymentId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                UserManager userManager = new UserManager();
                return new Payment(
                        result.getInt("paymentid"),
                        result.getInt("amount"),
                        result.getTimestamp("date"),
                        userManager.getUser(result.getInt("userid")),
                        userManager.getUser(result.getInt("adminid")));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Payment getPaymentByUser(int userId) {
        String sql = "SELECT * FROM payments WHERE userid = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                UserManager userManager = new UserManager();
                return new Payment(
                        result.getInt("paymentid"),
                        result.getInt("amount"),
                        result.getTimestamp("date"),
                        userManager.getUser(userId),
                        userManager.getUser(result.getInt("adminid")));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updatePayment(Payment payment) {
        String sql = "UPDATE payments SET amount = ?, date = ?, userid = ?, adminid = ? WHERE paymentid = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, payment.getAmount());
            statement.setTimestamp(2, new java.sql.Timestamp(payment.getDate().getTime()));
            statement.setInt(3, payment.getUser().getId());
            statement.setInt(4, payment.getAdmin().getId());
            statement.setInt(5, payment.getId());

            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePayment(int paymentId) {
        String sql = "DELETE FROM payments WHERE paymentid = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, paymentId);

            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

package database;

import entities.Announcement;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class AnnouncementManager {
    private final Connection connection;

    public AnnouncementManager() {
        DatabaseManager manager = DatabaseManager.getInstance();
        connection = manager.getConnection();
    }

    public Announcement createAnnouncement(int adminId, Date date, String message) {
        String sql = "INSERT INTO announcements (adminid, date, message) "
                + "VALUES (?, ?, ?) RETURNING announcementid";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, adminId);
            statement.setTimestamp(2, new Timestamp(date.getTime()));
            statement.setString(3, message);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                UserManager userManager = new UserManager();
                return new Announcement(
                        result.getInt("announcementid"),
                        userManager.getUser(adminId),
                        date,
                        message);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Announcement> getAllAnnouncements() {
        String sql = "SELECT * FROM announcements";
        try {
            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            ArrayList<Announcement> announcementList = new ArrayList<>();

            while (result.next()) {
                UserManager userManager = new UserManager();
                announcementList.add(new Announcement(
                        result.getInt("announcementid"),
                        userManager.getUser(result.getInt("adminid")),
                        result.getTimestamp("date"),
                        result.getString("message")));
            }

            return announcementList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Announcement getAnnouncement(int announcementId) {
        String sql = "SELECT * FROM announcements WHERE announcementid = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, announcementId);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                UserManager userManager = new UserManager();
                return new Announcement(
                        result.getInt("announcementid"),
                        userManager.getUser(result.getInt("adminid")),
                        result.getTimestamp("date"),
                        result.getString("message"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateAnnouncement(int announcementId, String message) {
        String sql = "UPDATE announcements SET message = ? WHERE announcementid = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, message);
            statement.setInt(2, announcementId);

            int result = statement.executeUpdate();

            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAnnouncement(int announcementId) {
        String sql = "DELETE FROM announcements WHERE announcementid = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, announcementId);

            int result = statement.executeUpdate();

            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

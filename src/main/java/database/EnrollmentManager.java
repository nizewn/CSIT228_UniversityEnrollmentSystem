package database;

import entities.Enrollment;
import utils.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;

public class EnrollmentManager {
    private final Connection connection;

    public EnrollmentManager() {
        DatabaseManager manager = DatabaseManager.getInstance();
        connection = manager.getConnection();
    }

    public Enrollment createEnrollment(int userId, int sectionId, double midtermGrade, double finalGrade) {
        String sql = "INSERT INTO enrollments (userid, sectionid, midtermgrade, finalgrade) "
                + "VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, userId);
            statement.setInt(2, sectionId);
            statement.setDouble(3, midtermGrade);
            statement.setDouble(4, finalGrade);

            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();

            if (result.next()) {
                UserManager userManager = new UserManager();
                SectionManager sectionManager = new SectionManager();
                return new Enrollment(
                        result.getInt(1),
                        userManager.getUser(userId),
                        sectionManager.getSection(sectionId),
                        midtermGrade,
                        finalGrade);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Enrollment> getAllEnrollmentsByUser(int userId) {
        String sql = "SELECT * FROM enrollments WHERE userid = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet result = statement.executeQuery();
            ArrayList<Enrollment> enrollments = new ArrayList<>();
            while (result.next()) {
                UserManager userManager = new UserManager();
                SectionManager sectionManager = new SectionManager();
                enrollments.add(new Enrollment(
                        result.getInt("enrollmentid"),
                        userManager.getUser(userId),
                        sectionManager.getSection(result.getInt("sectionid")),
                        result.getDouble("midtermgrade"),
                        result.getDouble("finalgrade")));
            }
            return enrollments;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Enrollment getEnrollmentBySectionAndUser(int sectionId, int userId) {
        String sql = "SELECT * FROM enrollments WHERE sectionid = ? AND userid = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, sectionId);
            statement.setInt(2, userId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                UserManager userManager = new UserManager();
                SectionManager sectionManager = new SectionManager();
                return new Enrollment(
                        result.getInt("enrollmentid"),
                        userManager.getUser(userId),
                        sectionManager.getSection(sectionId),
                        result.getDouble("midtermgrade"),
                        result.getDouble("finalgrade"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Enrollment> getAllEnrollmentsBySection(int sectionId) {
        String sql = "SELECT * FROM enrollments WHERE sectionid = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, sectionId);
            ResultSet result = statement.executeQuery();
            ArrayList<Enrollment> enrollments = new ArrayList<>();
            while (result.next()) {
                UserManager userManager = new UserManager();
                SectionManager sectionManager = new SectionManager();
                enrollments.add(new Enrollment(
                        result.getInt("enrollmentid"),
                        userManager.getUser(result.getInt("userid")),
                        sectionManager.getSection(sectionId),
                        result.getDouble("midtermgrade"),
                        result.getDouble("finalgrade")));
            }
            return enrollments;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Enrollment getEnrollment(int enrollmentId) {
        String sql = "SELECT * FROM enrollments WHERE enrollmentid = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, enrollmentId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                UserManager userManager = new UserManager();
                SectionManager sectionManager = new SectionManager();
                return new Enrollment(
                        result.getInt("enrollmentid"),
                        userManager.getUser(result.getInt("userid")),
                        sectionManager.getSection(result.getInt("sectionid")),
                        result.getDouble("midtermgrade"),
                        result.getDouble("finalgrade"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateEnrollment(Enrollment enrollment) {
        String sql = "UPDATE enrollments SET userid = ?, sectionid = ?, midtermgrade = ?, finalgrade = ? "
                + "WHERE enrollmentid = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, enrollment.getUser().getId());
            statement.setInt(2, enrollment.getSection().getId());
            statement.setDouble(3, enrollment.getMidtermGrade());
            statement.setDouble(4, enrollment.getFinalGrade());
            statement.setInt(5, enrollment.getId());
            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteEnrollment(int enrollmentId) {
        String sql = "DELETE FROM enrollments WHERE enrollmentid = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, enrollmentId);
            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

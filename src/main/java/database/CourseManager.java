package database;

import entities.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseManager {
    private final Connection connection;

    public CourseManager() {
        DatabaseManager manager = DatabaseManager.getInstance();
        connection = manager.getConnection();
    }

    public Course createCourse(String code, String description, int units, int tuition) {
        String sql = "INSERT INTO courses (code, description, units, tuition) "
                + "VALUES (?, ?, ?, ?) RETURNING courseid";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, code);
            statement.setString(2, description);
            statement.setInt(3, units);
            statement.setInt(4, tuition);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return new Course(
                        result.getInt("courseid"),
                        code,
                        description,
                        units,
                        tuition);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Course getCourse(int courseId) {
        String sql = "SELECT * FROM courses WHERE courseid = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, courseId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return new Course(
                        result.getInt("courseid"),
                        result.getString("code"),
                        result.getString("description"),
                        result.getInt("units"),
                        result.getInt("tuition"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Course> getAllCourses() {
        String sql = "SELECT * FROM courses";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            ArrayList<Course> courses = new ArrayList<>();
            while (result.next()) {
                courses.add(new Course(
                        result.getInt("courseid"),
                        result.getString("code"),
                        result.getString("description"),
                        result.getInt("units"),
                        result.getInt("tuition")));
            }
            return courses;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateCourse(Course course) {
        String sql = "UPDATE courses SET code = ?, description = ?, units = ?, tuition = ? WHERE courseid = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, course.getCode());
            statement.setString(2, course.getDescription());
            statement.setInt(3, course.getUnits());
            statement.setInt(4, course.getTuition());
            statement.setInt(5, course.getId());

            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCourse(int courseId) {
        String sql = "DELETE FROM courses WHERE courseid = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, courseId);

            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

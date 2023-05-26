package database;

import entities.Section;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SectionManager {
    private final Connection connection;

    public SectionManager() {
        DatabaseManager manager = DatabaseManager.getInstance();
        connection = manager.getConnection();
    }

    public Section createSection(int courseId, String instructorName, String location, String days, String timeStart, String timeEnd, int semester) {
        String sql = "INSERT INTO sections (courseid, instructorname, location, days, timestart, timeend, semester) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING sectionid";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, courseId);
            statement.setString(2, instructorName);
            statement.setString(3, location);
            statement.setString(4, days);
            statement.setString(5, timeStart);
            statement.setString(6, timeEnd);
            statement.setInt(7, semester);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                CourseManager courseManager = new CourseManager();
                return new Section(
                        result.getInt("sectionid"),
                        courseManager.getCourse(courseId),
                        instructorName,
                        location,
                        days,
                        timeStart,
                        timeEnd,
                        semester);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Section getSection(int sectionId) {
        String sql = "SELECT * FROM sections WHERE sectionid = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, sectionId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                CourseManager courseManager = new CourseManager();
                return new Section(
                        result.getInt("sectionid"),
                        courseManager.getCourse(result.getInt("courseid")),
                        result.getString("instructorname"),
                        result.getString("location"),
                        result.getString("days"),
                        result.getString("timestart"),
                        result.getString("timeend"),
                        result.getInt("semester"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Section> getAllSectionsByCourse(int courseId) {
        String sql = "SELECT * FROM sections WHERE courseid = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, courseId);
            ResultSet result = statement.executeQuery();

            ArrayList<Section> sections = new ArrayList<>();
            while (result.next()) {
                CourseManager courseManager = new CourseManager();
                sections.add(new Section(
                        result.getInt("sectionid"),
                        courseManager.getCourse(courseId),
                        result.getString("instructorname"),
                        result.getString("location"),
                        result.getString("days"),
                        result.getString("timestart"),
                        result.getString("timeend"),
                        result.getInt("semester")));
            }
            return sections;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateSection(Section section) {
        String sql = "UPDATE sections SET courseid = ?, instructorname = ?, location = ?, days = ?, timestart = ?, timeend = ?, semester = ? WHERE sectionid = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, section.getCourse().getId());
            statement.setString(2, section.getInstructorName());
            statement.setString(3, section.getLocation());
            statement.setString(4, section.getDays());
            statement.setString(5, section.getTimeStart());
            statement.setString(6, section.getTimeEnd());
            statement.setInt(7, section.getSemester());
            statement.setInt(8, section.getId());

            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteSection(int sectionId) {
        String sql = "DELETE FROM sections WHERE sectionid = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, sectionId);

            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

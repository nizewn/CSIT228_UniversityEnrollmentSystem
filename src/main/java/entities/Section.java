package entities;

import java.sql.Time;

public class Section {

    private final int id;

    private Course course;
    private String instructorName,
            location,
            days; // MWF/TTH
    private Time timeStart,
            timeEnd;
    private int semester;

    public Section(int id, Course course, String instructorName, String location, String days, Time timeStart, Time timeEnd, int semester) {
        this.id = id;
        this.course = course;
        this.instructorName = instructorName;
        this.location = location;
        this.days = days;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.semester = semester;
    }

    public int getId() {
        return id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public Time getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Time timeStart) {
        this.timeStart = timeStart;
    }

    public Time getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Time timeEnd) {
        this.timeEnd = timeEnd;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

}

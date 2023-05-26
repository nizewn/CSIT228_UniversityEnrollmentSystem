package entities;

import java.util.Date;

public class Announcement {

    private final int id;
    private User admin;
    private Date date;
    private String message;

    public Announcement(int id, User admin, Date date, String message) {
        this.id = id;
        this.admin = admin;
        this.date = date;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

package entities;

import java.util.Date;

public class Payment {
    private final int id;

    private int amount;
    private Date date;
    private User user;
    private User admin;

    public Payment(int id, int amount, Date date, User user, User admin) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.user = user;
        this.admin = admin;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }
}

package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// Singleton pattern, salamat chatgpt

/*
    basically, di nata kailangan mag instantiate or mag create2 og bago
    nga DatabaseManager for each page, mogamit ratag .getInstance().getConnection()
    (example usage sa UserManager.java)

    para ni efficient sya para usa ra ka connection sa whole app,
    di nata mag reconnect2 for each page hehe chat lng if paexplain pamo
 */
public class DatabaseManager {

    private static DatabaseManager instance;
    private Connection connection;
    private int userId; // of current logged in user

    private DatabaseManager() {
        System.out.println("Instantiating DatabaseManager. this should only print once");
        try {
            // Establish the database connection
            connection = DriverManager.getConnection("jdbc:sqlite:database.db");

            Statement statement = connection.createStatement();
            // Initialize tables if they don't exist
            // notice the IF NOT EXISTS clause
            String usersTable = """
                CREATE TABLE IF NOT EXISTS users (
                  userid INTEGER PRIMARY KEY AUTOINCREMENT,
                  username VARCHAR(64),
                  password TEXT,
                  lastname VARCHAR(64),
                  firstname VARCHAR(64),
                  yearlevel INTEGER DEFAULT 1,
                  gender CHAR(1),
                  birthdate VARCHAR(32),
                  address TEXT,
                  contactno VARCHAR(16),
                  email VARCHAR(64),
                  tuitionfee INTEGER DEFAULT 0
                );""";
            statement.addBatch(usersTable);
            String paymentsTable = """
                CREATE TABLE IF NOT EXISTS payments (
                  paymentid INTEGER PRIMARY KEY AUTOINCREMENT,
                  amount INTEGER,
                  date DATE,
                  userid INTEGER REFERENCES users(userid),
                  accountantid INTEGER REFERENCES users(userid)
                );""";
            statement.addBatch(paymentsTable);
            String coursesTable = """
                CREATE TABLE IF NOT EXISTS courses (
                    courseid INTEGER PRIMARY KEY AUTOINCREMENT,
                    code VARCHAR(16) UNIQUE,
                    units INTEGER,
                    description VARCHAR(64),
                    tuition INTEGER
                );""";
            statement.addBatch(coursesTable);
            String classesTable = """
                CREATE TABLE IF NOT EXISTS classes (
                    classid INTEGER PRIMARY KEY AUTOINCREMENT,
                    courseid INTEGER REFERENCES courses(courseid),
                    instructorid INTEGER REFERENCES users(userid),
                    location VARCHAR(16),
                    days VARCHAR(8),
                    time VARCHAR(16)
                );""";
            statement.addBatch(classesTable);
            String announcementsTable = """
                CREATE TABLE IF NOT EXISTS announcements (
                    announcementid INTEGER PRIMARY KEY AUTOINCREMENT,
                    adminid INTEGER REFERENCES users (userid),
                    date DATE,
                    message TEXT
                );""";
            statement.addBatch(announcementsTable);
            String enrollmentsTable = """
                CREATE TABLE IF NOT EXISTS enrollments (
                  enrollmentid INTEGER PRIMARY KEY AUTOINCREMENT,
                  userid INTEGER REFERENCES users(userid),
                  classid INTEGER REFERENCES classes(classid),
                  midtermgrade REAL,
                  finalgrade REAL
                );""";
            statement.addBatch(enrollmentsTable);

            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            // if wala patay instance, edi instantiate for the first time
            // pero if naa nay instance daan, kato ra sya ireturn balik, no need maghimog lain
            instance = new DatabaseManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

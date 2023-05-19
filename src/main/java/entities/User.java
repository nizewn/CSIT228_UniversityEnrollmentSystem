package entities;

public class User {

    private final String type; // student, instructor, or admin

    // di nalang tanan fields, ang uban kay iretrieve ra sa database
    // nya ioutput ra ditso, no need iapil sa user object
    private final int userId;
    private int tuitionFee;
    private String username,
            email,
            lastName,
            firstName;

    public User(int userId, String type, String username, String email, String lastName, String firstName, int tuitionFee) {
        this.userId = userId;
        this.type = type;
        this.username = username;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.tuitionFee = tuitionFee;
    }

    public int getUserId() {
        return userId;
    }

    public String getType() {
        return type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getTuitionFee() {
        return tuitionFee;
    }

    public void setTuitionFee(int tuitionFee) {
        this.tuitionFee = tuitionFee;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

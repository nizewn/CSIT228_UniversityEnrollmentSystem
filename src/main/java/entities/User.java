package entities;

public class User {

    private final String type; // student, instructor, admin, accountant?

    // di nalang tanan fields, ang uban kay iretrieve ra sa database
    // nya ioutput ra ditso, no need iapil sa user object
    private String email,
            lastName,
            firstName,
            idNumber;

    private int totalTuitionFee;
    // iminus lang ni nato sa iya total payments para makuha ang remaining balance

    public User(String type, String email, String lastName, String firstName, String idNumber) {
        this.type = type;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.idNumber = idNumber;
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

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public int getTotalTuitionFee() {
        return totalTuitionFee;
    }

    public void setTotalTuitionFee(int totalTuitionFee) {
        this.totalTuitionFee = totalTuitionFee;
    }
}

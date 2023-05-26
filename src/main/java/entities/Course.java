package entities;

public class Course {

    private final int id;

    private String code, description;
    private int units, tuition;

    public Course(int id, String code, String description, int units, int tuition) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.units = units;
        this.tuition = tuition;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public int getTuition() {
        return tuition;
    }

    public void setTuition(int tuition) {
        this.tuition = tuition;
    }
}

package pages;

import database.EnrollmentManager;
import entities.Enrollment;
import entities.User;
import utils.UserEventListener;
import utils.UserState;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

public class GradesPage extends JPanel implements UserEventListener {

    private final JComboBox<String> dropdown;
    private final JTable table;
    ArrayList<Enrollment> enrollments;
    int currentSemester = 1;

    public GradesPage() {
        super();
        setLayout(new BorderLayout());

        // Dropdown menu
        dropdown = new JComboBox<>(new String[]{"1st Semester", "2nd Semester"});
        dropdown.addItemListener(l -> {
            if (l.getStateChange() == ItemEvent.SELECTED) {
                currentSemester = dropdown.getSelectedIndex() + 1;
                updateTable();
            }
        });
        JPanel dropdownPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        dropdownPanel.add(dropdown);
        add(dropdownPanel, BorderLayout.NORTH);

        // Label
        JLabel label = new JLabel("This is a label");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);

        // Table
        String[] columnNames = {"Course #", "Description", "Instructor", "Midterm Grade", "Final Grade"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.SOUTH);

        UserState.getInstance().addUpdateListener(this);
    }

    @Override
    public void onUserUpdate(User user) {
        if (user != null) {
            refreshData();
        }
    }

    void refreshData() {
        int userId = UserState.getInstance().getCurrentUser().getId();
        EnrollmentManager enrollmentManager = new EnrollmentManager();
        enrollments = enrollmentManager.getAllEnrollmentsByUser(userId);

        updateTable();
    }

    void updateTable() {
        if (enrollments == null) {
            return;
        }

        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getSection().getSemester() != currentSemester) {
                continue;
            }
            double midtermGrade = enrollment.getMidtermGrade();
            double finalGrade = enrollment.getFinalGrade();
            String[] data = {
                    enrollment.getSection().getCourse().getCode(),
                    enrollment.getSection().getCourse().getDescription(),
                    enrollment.getSection().getInstructorName(),
                    midtermGrade <= 0.00 ? "" : String.valueOf(midtermGrade),
                    finalGrade <= 0.00 ? "" : String.valueOf(finalGrade),
            };
            tableModel.addRow(data);
        }
    }
}

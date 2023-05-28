package pages;

import database.EnrollmentManager;
import database.SectionManager;
import database.UserManager;
import entities.Enrollment;
import entities.Section;
import entities.User;
import utils.UserState;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class CoursesPage extends JPanel {

    JTable table;

    public CoursesPage() {
        super();

        // Create the search bar and label
        JLabel searchLabel = new JLabel("Search course:");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchButton.addActionListener(e -> search(searchField.getText()));

        JPanel actionPanel = new JPanel(new BorderLayout());
        JButton enrollButton = new JButton("Enroll");
        enrollButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a section to enroll in.");
                return;
            }

            int userId = UserState.getInstance().getCurrentUser().getId();
            int sectionId = Integer.parseInt((String) table.getValueAt(row, 0));
            EnrollmentManager enrollmentManager = new EnrollmentManager();
            UserManager userManager = new UserManager();

            Enrollment enrollment = enrollmentManager.getEnrollmentBySectionAndUser(sectionId, userId);
            if (enrollment != null) {
                JOptionPane.showMessageDialog(this, "You are already enrolled in this section.");
                return;
            }

            enrollment = enrollmentManager.createEnrollment(userId, sectionId, 0, 0);
            int tuitionFee = enrollment.getSection().getCourse().getTuition();

            User currentUser = UserState.getInstance().getCurrentUser();
            currentUser.setTuitionFee(currentUser.getTuitionFee() + tuitionFee);

            boolean success = userManager.updateUser(currentUser);
            if (success) {
                UserState.getInstance().updateCurrentUser(currentUser);
            }

            JOptionPane.showMessageDialog(this, "Successfully enrolled in section " + sectionId);
        });
        JPanel extraPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        extraPanel.add(enrollButton);
        actionPanel.add(extraPanel, BorderLayout.PAGE_END);

        // Create the table
        String[] columnNames = {"Section", "Description", "Instructor", "Schedule", "Room #"};
        table = new JTable(new DefaultTableModel(columnNames, 0));
        table.setDefaultEditor(Object.class, null);
        table.setPreferredScrollableViewportSize(new Dimension(800, 400));
        JScrollPane scrollPane = new JScrollPane(table);

        setLayout(new BorderLayout());

        add(searchPanel, BorderLayout.NORTH);
        add(actionPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
    }

    void search(String query) {
        SectionManager manager = new SectionManager();
        ArrayList<Section> sections = manager.searchSectionByCourse(query);

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (Section section : sections) {

            String[] data = {
                    String.valueOf(section.getId()),
                    section.getCourse().getCode(),
                    section.getInstructorName(),
                    section.getDays() + " " + section.getTimeStart() + "-" + section.getTimeEnd(),
                    section.getLocation()
            };

            model.addRow(data);
        }
    }
}

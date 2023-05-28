package pages;

import database.EnrollmentManager;
import entities.Enrollment;
import entities.Section;
import entities.User;
import utils.UserEventListener;
import utils.UserState;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SchedulePage extends JPanel implements UserEventListener {

    private JTable table;

    public SchedulePage() {
        super();

        setLayout(new BorderLayout());

        // Create the label panel and add labels to it
        JPanel labelPanelWrapper = new JPanel(new BorderLayout());
        JPanel labelPanel = new JPanel(new GridLayout(3, 1, 20, 10));
        JLabel welcomeLabel = new JLabel("Welcome Back!");
        labelPanel.add(welcomeLabel);

        JLabel yearSemesterLabel = new JLabel("Academic Year : 2023");
        labelPanel.add(yearSemesterLabel);

        JLabel semesterLabel = new JLabel("Second Semester");
        labelPanel.add(semesterLabel);

        labelPanelWrapper.add(labelPanel, BorderLayout.NORTH);
        labelPanelWrapper.setBorder(new EmptyBorder(20, 0, 0, 0));
        add(labelPanelWrapper, BorderLayout.NORTH);

        // Create the table panel and add the table to it
        JPanel tablePanel = new JPanel(new GridLayout(1, 1));
        String[] columnNames = {"Faculty", "Course & Section", "Description", "Schedule", "Room #"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setDefaultEditor(Object.class, null); // disable editing
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane);
        add(tablePanel, BorderLayout.CENTER);

        UserState.getInstance().addUpdateListener(this); // necessary ni para mogana ang onUserUpdate
    }

    @Override
    public void onUserUpdate(User user) {
        if (user != null) {
            refreshData();
        }
    }

    void refreshData() {
        int currentUserId = UserState.getInstance().getCurrentUser().getId();
        EnrollmentManager eManager = new EnrollmentManager();
        ArrayList<Enrollment> enrollments = eManager.getAllEnrollmentsByUser(currentUserId);

        DefaultTableModel model = (DefaultTableModel) table.getModel();

        // iclear ang table / iremove ang old nga gidisplay
        model.setNumRows(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

        for (Enrollment e : enrollments) {
            Section section = e.getSection();

            // format to HH:MM AM/PM using formatter above
            LocalTime localTimeStart = section.getTimeStart().toLocalTime();
            LocalTime localTimeEnd = section.getTimeEnd().toLocalTime();
            String timeStart = localTimeStart.format(formatter);
            String timeEnd = localTimeEnd.format(formatter);

            String[] data = {
                    section.getInstructorName(),
                    section.getCourse().getCode() + "-" + section.getId(),
                    section.getCourse().getDescription(),
                    section.getDays() + " " + timeStart + "-" + timeEnd,
                    section.getLocation()
            };
            model.addRow(data);
        }
    }
}

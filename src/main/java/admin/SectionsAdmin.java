package admin;

import database.CourseManager;
import database.SectionManager;
import entities.Section;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class SectionsAdmin extends JPanel {
    JComboBox<String> courseMenu;
    JTable table;
    JLabel courseInfoLabel;

    int selectedCourseId = 0;


    public SectionsAdmin() {
        super();

        JPanel coursePanel = new JPanel();
        JLabel courseLabel = new JLabel("Course: ");
        courseMenu = new JComboBox<>();

        JButton btnRefreshCourses = new JButton("Refresh Courses");
        btnRefreshCourses.addActionListener(l -> {
            refreshCourseList();
        });

        coursePanel.add(courseLabel);
        coursePanel.add(courseMenu);
        coursePanel.add(btnRefreshCourses);

        // Create buttons
        JButton createButton = new JButton("Create");
        createButton.addActionListener(l -> openDialog(0));

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(l -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a section.");
                return;
            }
            int id = (int) table.getValueAt(selectedRow, 0);
            openDialog(id);
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(l -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a section.");
                return;
            }
            int id = (int) table.getValueAt(selectedRow, 0);
            SectionManager manager = new SectionManager();
            manager.deleteSection(id);
            refreshData();
        });

        // Create table
        String[] columnNames = {"ID", "Instructor name", "Semester", "Location", "Schedule"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

        setLayout(new BorderLayout());
        add(coursePanel, BorderLayout.NORTH);


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        courseInfoLabel = new JLabel();

        JPanel labelAndButtonPanel = new JPanel();
        labelAndButtonPanel.setLayout(new BoxLayout(labelAndButtonPanel, BoxLayout.Y_AXIS));
        labelAndButtonPanel.add(courseInfoLabel);
        labelAndButtonPanel.add(buttonPanel);

        add(labelAndButtonPanel, BorderLayout.CENTER);
        add(new JScrollPane(table), BorderLayout.SOUTH);

        refreshCourseList();
        courseMenu.addItemListener(l -> {
            if (l.getStateChange() == ItemEvent.SELECTED) {
                String course = (String) l.getItem();
                String[] courseParts = course.split("_");
                selectedCourseId = Integer.parseInt(courseParts[0]);
                refreshData();
            }
        });
    }

    void refreshCourseList() {
        courseMenu.removeAll();
        CourseManager courseManager = new CourseManager();
        courseManager.getAllCourses().forEach(course -> {
            courseMenu.addItem(course.getId() + "_" + course.getCode());
        });
        refreshData();
    }

    void openDialog(int id) {
        String title = id == 0 ? "Create section" : "Update section";
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), title, Dialog.ModalityType.APPLICATION_MODAL);

        Section section;
        if (id != 0) {
            SectionManager sectionManager = new SectionManager();
            section = sectionManager.getSection(id);
        } else {
            section = null;
        }

        JLabel instructorLabel = new JLabel("Instructor name");
        JTextField instructorField = new JTextField(20);
        if (section != null) instructorField.setText(section.getInstructorName());

        // A number field for semester limited to 1 and 2 only
        JLabel semesterLabel = new JLabel("Semester");
        JSpinner semesterSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 2, 1));
        if (section != null) semesterSpinner.setValue(section.getSemester());

        JLabel locationLabel = new JLabel("Location");
        JTextField locationField = new JTextField(20);
        if (section != null) locationField.setText(section.getLocation());

        JLabel daysLabel = new JLabel("Days");
        JComboBox<String> daysComboBox = new JComboBox<>();
        daysComboBox.addItem("MWF");
        daysComboBox.addItem("TTH");
        if (section != null) daysComboBox.setSelectedItem(section.getDays());

        JLabel startTimeLabel = new JLabel("Start time");
        JSpinner startTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor startTimeEditor = new JSpinner.DateEditor(startTimeSpinner, "hh:mm a");
        startTimeSpinner.setEditor(startTimeEditor);
        if (section != null) startTimeSpinner.setValue(section.getTimeStart());

        JLabel endTimeLabel = new JLabel("End time");
        JSpinner endTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor endTimeEditor = new JSpinner.DateEditor(endTimeSpinner, "hh:mm a");
        endTimeSpinner.setEditor(endTimeEditor);
        if (section != null) endTimeSpinner.setValue(section.getTimeEnd());

        JButton actionButton = new JButton(section == null ? "Create" : "Update");
        actionButton.addActionListener(l -> {
            SectionManager manager = new SectionManager();
            if (section != null) {
                section.setInstructorName(instructorField.getText());
                section.setSemester((int) semesterSpinner.getValue());
                section.setLocation(locationField.getText());
                section.setDays((String) daysComboBox.getSelectedItem());
                // hahaha sorry sir gitapol nakog organize
                section.setTimeStart(new Time(((Date) startTimeSpinner.getValue()).getTime()));
                section.setTimeEnd(new Time(((Date) endTimeSpinner.getValue()).getTime()));
                manager.updateSection(section);
            } else {
                manager.createSection(
                        selectedCourseId,
                        instructorField.getText(),
                        locationField.getText(),
                        (String) daysComboBox.getSelectedItem(),
                        new Time(((Date) startTimeSpinner.getValue()).getTime()),
                        new Time(((Date) endTimeSpinner.getValue()).getTime()),
                        (int) semesterSpinner.getValue()
                );
            }
            refreshData();
            dialog.dispose();
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(l -> dialog.dispose());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(section == null ? 7 : 8, 2));
        if (section != null) {
            panel.add(new JLabel("ID"));
            panel.add(new JLabel(String.valueOf(section.getId())));
        }

        panel.add(instructorLabel);
        panel.add(instructorField);
        panel.add(semesterLabel);
        panel.add(semesterSpinner);
        panel.add(locationLabel);
        panel.add(locationField);
        panel.add(daysLabel);
        panel.add(daysComboBox);
        panel.add(startTimeLabel);
        panel.add(startTimeSpinner);
        panel.add(endTimeLabel);
        panel.add(endTimeSpinner);

        panel.add(actionButton);
        panel.add(cancelButton);

        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    void refreshData() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        if (selectedCourseId == 0) {
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        SectionManager sectionManager = new SectionManager();

        sectionManager.getAllSectionsByCourse(selectedCourseId).forEach(section -> {
            courseInfoLabel.setText(section.getCourse().getCode() + " " + section.getCourse().getDescription());

            // format to HH:MM AM/PM using formatter above
            LocalTime localTimeStart = section.getTimeStart().toLocalTime();
            LocalTime localTimeEnd = section.getTimeEnd().toLocalTime();
            String timeStart = localTimeStart.format(formatter);
            String timeEnd = localTimeEnd.format(formatter);

            model.addRow(new Object[]{
                    section.getId(),
                    section.getInstructorName(),
                    section.getSemester(),
                    section.getLocation(),
                    section.getDays() + " " + timeStart + "-" + timeEnd
            });
        });
    }
}

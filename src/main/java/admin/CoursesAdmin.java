package admin;

import database.CourseManager;
import entities.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CoursesAdmin extends JPanel {
    private JTable table;

    public CoursesAdmin() {
        super();

        // Create buttons
        JButton createButton = new JButton("Create");
        createButton.addActionListener(l -> openDialog(0));

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(l -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a course.");
                return;
            }
            int id = (int) table.getValueAt(selectedRow, 0);
            openDialog(id);
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(l -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a course.");
                return;
            }
            int id = (int) table.getValueAt(selectedRow, 0);
            CourseManager courseManager = new CourseManager();
            courseManager.deleteCourse(id);
            refreshData();
        });

        // Create table
        String[] columnNames = {"ID", "Code", "Units", "Description", "Tuition Fee"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

        // Set layout
        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        refreshData();
    }

    void refreshData() {
        CourseManager courseManager = new CourseManager();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        courseManager.getAllCourses().forEach(course -> model.addRow(new Object[]{
                course.getId(),
                course.getCode(),
                course.getUnits(),
                course.getDescription(),
                course.getTuition()
        }));
    }

    void openDialog(int id) {
        String title = id == 0 ? "Create course" : "Update course";
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), title, Dialog.ModalityType.APPLICATION_MODAL);

        Course course;
        if (id != 0) {
            CourseManager courseManager = new CourseManager();
            course = courseManager.getCourse(id);
        } else {
            course = null;
        }

        JLabel codeLabel = new JLabel("Code");
        JTextField codeField = new JTextField(20);
        if (course != null) codeField.setText(course.getCode());

        JLabel unitsLabel = new JLabel("Units");
        JTextField unitsField = new JTextField(20);
        if (course != null) unitsField.setText(String.valueOf(course.getUnits()));

        JLabel descriptionLabel = new JLabel("Description");
        JTextField descriptionField = new JTextField(20);
        if (course != null) descriptionField.setText(course.getDescription());

        JLabel tuitionLabel = new JLabel("Tuition Fee");
        JTextField tuitionField = new JTextField(20);
        if (course != null) tuitionField.setText(String.valueOf(course.getTuition()));

        JButton actionButton = new JButton(course == null ? "Create" : "Update");
        actionButton.addActionListener(l -> {
            CourseManager manager = new CourseManager();
            if (course != null) {
                course.setCode(codeField.getText());
                course.setUnits(Integer.parseInt(unitsField.getText()));
                course.setDescription(descriptionField.getText());
                course.setTuition(Integer.parseInt(tuitionField.getText()));
                manager.updateCourse(course);
            } else {
                manager.createCourse(codeField.getText(), descriptionField.getText(), Integer.parseInt(unitsField.getText()), Integer.parseInt(tuitionField.getText()));
            }
            refreshData();
            dialog.dispose();
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(l -> dialog.dispose());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(course == null ? 5 : 6, 2));
        if (course != null) {
            panel.add(new JLabel("ID"));
            panel.add(new JLabel(String.valueOf(course.getId())));
        }
        panel.add(codeLabel);
        panel.add(codeField);
        panel.add(unitsLabel);
        panel.add(unitsField);
        panel.add(descriptionLabel);
        panel.add(descriptionField);
        panel.add(tuitionLabel);
        panel.add(tuitionField);
        panel.add(actionButton);
        panel.add(cancelButton);

        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}

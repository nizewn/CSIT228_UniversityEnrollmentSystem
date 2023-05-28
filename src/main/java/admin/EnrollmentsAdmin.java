package admin;

import database.EnrollmentManager;
import database.SectionManager;
import database.UserManager;
import entities.Enrollment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;

public class EnrollmentsAdmin extends JPanel {
    JComboBox<String> sectionMenu;
    JTable table;
    JLabel sectionInfoLabel;

    int selectedSectionId = 0;


    public EnrollmentsAdmin() {
        super();

        JPanel sectionPanel = new JPanel();
        JLabel sectionLabel = new JLabel("Section: ");
        sectionMenu = new JComboBox<>();

        JButton btnRefreshSections = new JButton("Refresh Sections");
        btnRefreshSections.addActionListener(l -> {
            refreshSectionList();
        });

        sectionPanel.add(sectionLabel);
        sectionPanel.add(sectionMenu);
        sectionPanel.add(btnRefreshSections);

        // Create buttons
        JButton createButton = new JButton("Create");
        createButton.addActionListener(l -> openDialog(0));

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(l -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select an enrollment.");
                return;
            }
            int id = (int) table.getValueAt(selectedRow, 0);
            openDialog(id);
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(l -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select an enrollment.");
                return;
            }
            int id = (int) table.getValueAt(selectedRow, 0);
            EnrollmentManager manager = new EnrollmentManager();
            manager.deleteEnrollment(id);
            refreshData();
        });

        // Create table
        String[] columnNames = {"ID", "Student", "Midterm grade", "Final grade"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

        setLayout(new BorderLayout());
        add(sectionPanel, BorderLayout.NORTH);


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        sectionInfoLabel = new JLabel();

        JPanel labelAndButtonPanel = new JPanel();
        labelAndButtonPanel.setLayout(new BoxLayout(labelAndButtonPanel, BoxLayout.Y_AXIS));
        labelAndButtonPanel.add(sectionInfoLabel);
        labelAndButtonPanel.add(buttonPanel);

        add(labelAndButtonPanel, BorderLayout.CENTER);
        add(new JScrollPane(table), BorderLayout.SOUTH);

        refreshSectionList();
        sectionMenu.addItemListener(l -> {
            if (l.getStateChange() == ItemEvent.SELECTED) {
                String course = (String) l.getItem();
                String[] courseParts = course.split("_");
                selectedSectionId = Integer.parseInt(courseParts[0]);
                refreshData();
            }
        });
    }

    void refreshSectionList() {
        sectionMenu.removeAll();
        SectionManager sectionManager = new SectionManager();
        sectionManager.getAllSections().forEach(section -> {
            sectionMenu.addItem(section.getId() + "_" + section.getCourse().getCode());
        });
        refreshData();
    }

    void openDialog(int id) {
        String title = id == 0 ? "Create enrollment" : "Update enrollment";
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), title, Dialog.ModalityType.APPLICATION_MODAL);

        Enrollment enrollment;
        if (id != 0) {
            EnrollmentManager enrollmentManager = new EnrollmentManager();
            enrollment = enrollmentManager.getEnrollment(id);
        } else {
            enrollment = null;
        }

        JLabel studentLabel = new JLabel("Student");
        JComboBox<String> studentComboBox = new JComboBox<>();
        UserManager userManager = new UserManager();
        userManager.getAllUsers().forEach(user -> {
            studentComboBox.addItem(user.getId() + "_" + user.getLastName() + ", " + user.getFirstName());
        });
        if (enrollment != null) {
            studentComboBox.setSelectedItem(enrollment.getUser().getId() + "_" + enrollment.getUser().getLastName() + ", " + enrollment.getUser().getFirstName());
        }

        JLabel midtermLabel = new JLabel("Midterm grade");
        JTextField midtermField = new JTextField(20);
        if (enrollment != null) midtermField.setText(String.valueOf(enrollment.getMidtermGrade()));

        JLabel finalLabel = new JLabel("Final grade");
        JTextField finalField = new JTextField(20);
        if (enrollment != null) finalField.setText(String.valueOf(enrollment.getFinalGrade()));

        JButton actionButton = new JButton(enrollment == null ? "Create" : "Update");
        actionButton.addActionListener(l -> {
            EnrollmentManager manager = new EnrollmentManager();
            if (enrollment != null) {
                enrollment.setUser(userManager.getUser(Integer.parseInt(((String) studentComboBox.getSelectedItem()).split("_")[0])));
                enrollment.setMidtermGrade(Double.parseDouble(midtermField.getText()));
                enrollment.setFinalGrade(Double.parseDouble(finalField.getText()));
                manager.updateEnrollment(enrollment);
            } else {
                manager.createEnrollment(
                        Integer.parseInt(((String) studentComboBox.getSelectedItem()).split("_")[0]),
                        selectedSectionId,
                        Double.parseDouble(midtermField.getText()),
                        Double.parseDouble(finalField.getText())
                );
            }
            refreshData();
            dialog.dispose();
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(l -> dialog.dispose());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(enrollment == null ? 4 : 5, 2));
        if (enrollment != null) {
            panel.add(new JLabel("ID"));
            panel.add(new JLabel(String.valueOf(enrollment.getId())));
        }

        panel.add(studentLabel);
        panel.add(studentComboBox);
        panel.add(midtermLabel);
        panel.add(midtermField);
        panel.add(finalLabel);
        panel.add(finalField);

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
        if (selectedSectionId == 0) {
            return;
        }
        EnrollmentManager enrollmentManager = new EnrollmentManager();

        enrollmentManager.getAllEnrollmentsBySection(selectedSectionId).forEach(enrollment -> {
            sectionInfoLabel.setText(enrollment.getSection().getCourse().getCode() + "-" + enrollment.getSection().getId());

            model.addRow(new Object[]{
                    enrollment.getId(),
                    enrollment.getUser().getLastName() + ", " + enrollment.getUser().getFirstName(),
                    enrollment.getMidtermGrade(),
                    enrollment.getFinalGrade()
            });
        });
    }
}

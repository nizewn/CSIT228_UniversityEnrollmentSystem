package admin;

import database.UserManager;
import entities.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UsersAdmin extends JPanel {
    private JTable table;

    public UsersAdmin() {
        super();

        // Create buttons
        JButton createButton = new JButton("Create");
        createButton.addActionListener(l -> openDialog(0));

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(l -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a user.");
                return;
            }
            int id = (int) table.getValueAt(selectedRow, 0);
            openDialog(id);
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(l -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a user.");
                return;
            }
            int id = (int) table.getValueAt(selectedRow, 0);
            UserManager userManager = new UserManager();
            userManager.deleteUser(id);
            refreshData();
        });

        // Create table
        String[] columnNames = {"ID", "Username", "Last name", "First name", "Email", "Tuition Fee", "Is admin?"};
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
        UserManager userManager = new UserManager();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        userManager.getAllUsers().forEach(user -> model.addRow(new Object[]{
                user.getId(),
                user.getUsername(),
                user.getLastName(),
                user.getFirstName(),
                user.getEmail(),
                user.getTuitionFee(),
                user.isAdmin() ? "Yes" : "No"
        }));
    }

    void openDialog(int id) {
        String title = id == 0 ? "Create user" : "Update user";
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), title, Dialog.ModalityType.APPLICATION_MODAL);

        User user;
        if (id != 0) {
            UserManager userManager = new UserManager();
            user = userManager.getUser(id);
        } else {
            user = null;
        }

        JLabel usernameLabel = new JLabel("Username");
        JTextField usernameField = new JTextField(20);
        if (user != null) usernameField.setText(user.getUsername());

        JLabel passwordLabel = new JLabel("Password");
        JTextField passwordField = new JTextField(20);

        JLabel lastNameLabel = new JLabel("Last name");
        JTextField lastNameField = new JTextField(20);
        if (user != null) lastNameField.setText(user.getLastName());

        JLabel firstNameLabel = new JLabel("First name");
        JTextField firstNameField = new JTextField(20);
        if (user != null) firstNameField.setText(user.getFirstName());

        JLabel emailLabel = new JLabel("Email");
        JTextField emailField = new JTextField(20);
        if (user != null) emailField.setText(user.getEmail());

        JLabel tuitionLabel = new JLabel("Tuition Fee");
        JTextField tuitionField = new JTextField(20);
        if (user != null) tuitionField.setText(String.valueOf(user.getTuitionFee()));

        // use checkbox for admin
        JLabel adminLabel = new JLabel("Is admin?");
        JCheckBox adminCheckBox = new JCheckBox();
        if (user != null) adminCheckBox.setSelected(user.isAdmin());

        JButton actionButton = new JButton(user == null ? "Create" : "Update");
        actionButton.addActionListener(l -> {
            UserManager manager = new UserManager();
            if (user != null) {
                user.setUsername(usernameField.getText());
                user.setLastName(lastNameField.getText());
                user.setFirstName(firstNameField.getText());
                user.setEmail(emailField.getText());
                user.setTuitionFee(Integer.parseInt(tuitionField.getText()));
                manager.updateUser(user);
            } else {
                manager.createUser(adminCheckBox.isSelected(), usernameField.getText(), passwordField.getText(), lastNameField.getText(), firstNameField.getText(), emailField.getText());
            }
            refreshData();
            dialog.dispose();
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(l -> dialog.dispose());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));
        if (user != null) {
            panel.add(new JLabel("ID"));
            panel.add(new JLabel(String.valueOf(user.getId())));
        }
        panel.add(usernameLabel);
        panel.add(usernameField);
        if (user == null) {
            panel.add(passwordLabel);
            panel.add(passwordField);
        }
        panel.add(lastNameLabel);
        panel.add(lastNameField);
        panel.add(firstNameLabel);
        panel.add(firstNameField);
        panel.add(emailLabel);
        panel.add(emailField);

        if (user != null) {
            panel.add(tuitionLabel);
            panel.add(tuitionField);
        } else {
            panel.add(adminLabel);
            panel.add(adminCheckBox);
        }

        panel.add(actionButton);
        panel.add(cancelButton);

        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}

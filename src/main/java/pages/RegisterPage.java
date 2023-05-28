package pages;

import database.UserManager;
import entities.User;
import utils.UserState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterPage extends JPanel implements ActionListener {

    private JTextField lastNameField, firstNameField, emailField, usernameField;
    private JPasswordField passwordField, confirmPasswordField;
    private JLabel personalInfoLabel, loginInfoLabel;
    private JButton submitButton, resetButton;

    public RegisterPage() {
        super(new BorderLayout(8, 10));

        setLayout(new FlowLayout(FlowLayout.CENTER, 8, 10));

        int separatorCount = 3;
        JSeparator[] separators = new JSeparator[separatorCount];
        for (int i = 0; i < separatorCount; i++) {
            separators[i] = new JSeparator();
            separators[i].setOrientation(SwingConstants.HORIZONTAL);
            separators[i].setPreferredSize(new Dimension(650, 10));
            separators[i].setForeground(new Color(200, 200, 200));
            separators[i].setBackground(null);
        }

        JLabel titleLabel = new JLabel("STUDENT REGISTRATION FORM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel);
        add(separators[0]);

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));

        personalInfoLabel = new JLabel("Personal Information");
        personalInfoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        formPanel.add(personalInfoLabel);

        formPanel.add(new JLabel()); // Empty label for spacing

        formPanel.add(new JLabel("Last Name"));
        lastNameField = new JTextField(20);
        formPanel.add(lastNameField);

        formPanel.add(new JLabel("First Name"));
        firstNameField = new JTextField(20);
        formPanel.add(firstNameField);

        formPanel.add(new JLabel("Email Address"));
        emailField = new JTextField(20);
        formPanel.add(emailField);

        loginInfoLabel = new JLabel("Login Information");
        loginInfoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        formPanel.add(loginInfoLabel);

        formPanel.add(new JLabel()); // Empty label for spacing

        formPanel.add(new JLabel("Username"));
        usernameField = new JTextField(20);
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Password"));
        passwordField = new JPasswordField(20);
        formPanel.add(passwordField);

        formPanel.add(new JLabel("Confirm Password"));
        confirmPasswordField = new JPasswordField(20);
        formPanel.add(confirmPasswordField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        submitButton = new JButton("Register");
        submitButton.addActionListener(this);
        resetButton = new JButton("Clear");
        resetButton.addActionListener(this);
        buttonPanel.add(submitButton);
        buttonPanel.add(resetButton);

        JPanel confirmPanel = new JPanel(new BorderLayout());
        confirmPanel.add(formPanel, BorderLayout.CENTER);
        confirmPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(confirmPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String lastName = lastNameField.getText();
            String firstName = firstNameField.getText();
            String email = emailField.getText();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            // check if a field is empty
            if (lastName.isEmpty() || firstName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields!");
                return;
            }

            // confirm password
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match!");
                return;
            }

            UserManager manager = new UserManager();
            User user = manager.createUser(false, username, password, lastName, firstName, email);

            if (user != null) {

                JOptionPane.showMessageDialog(this, "Registration successful!");

                lastNameField.setText("");
                firstNameField.setText("");
                emailField.setText("");
                usernameField.setText("");
                passwordField.setText("");
                confirmPasswordField.setText("");

                UserState.getInstance().updateCurrentUser(user);
            } else {
                confirmPasswordField.setText("");
                JOptionPane.showMessageDialog(this, "Registration failed. Please try again.");
            }
        } else if (e.getSource() == resetButton) {
            // Clear all input fields
            lastNameField.setText("");
            firstNameField.setText("");
            emailField.setText("");
            usernameField.setText("");
            passwordField.setText("");
            confirmPasswordField.setText("");
        }
    }
}
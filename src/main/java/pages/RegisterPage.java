package pages;

import database.UserManager;
import entities.User;
import utils.UserState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterPage extends JPanel implements ActionListener {

    private JTextField lastNameField, firstNameField, emailField;
    private JPasswordField passwordField, confirmPasswordField;
    private JLabel personalInfoLabel, loginInfoLabel;
    private JButton submitButton, resetButton;

    public RegisterPage() {
        super();
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

        personalInfoLabel = new JLabel("Personal Information");
        personalInfoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(personalInfoLabel);

        lastNameField = new JTextField(20);
        addFieldPanel("Last name", lastNameField);

        firstNameField = new JTextField(20);
        addFieldPanel("First name", firstNameField);

        emailField = new JTextField(20);
        addFieldPanel("Email Address", emailField);

        loginInfoLabel = new JLabel("Login Information");
        loginInfoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(loginInfoLabel);

        emailField = new JTextField(20);
        addFieldPanel("Email Address", emailField);

        passwordField = new JPasswordField(20);
        addFieldPanel("Password", passwordField);

        confirmPasswordField = new JPasswordField(20);
        addFieldPanel("Confirm Password", confirmPasswordField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        submitButton = new JButton("Save");
        resetButton = new JButton("Clear");
        buttonPanel.add(submitButton);
        buttonPanel.add(resetButton);
        buttonPanel.setPreferredSize(new Dimension(500, getPreferredSize().height));
        add(buttonPanel);

        // Add action listeners
        submitButton.addActionListener(this);
        resetButton.addActionListener(this);
    }

    private void addFieldPanel(String label, JComponent component) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel fieldLabel = new JLabel(label);
        panel.add(fieldLabel);
        panel.add(component);
        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == submitButton) {
            String lastName = lastNameField.getText();
            String firstName = firstNameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            UserManager manager = new UserManager();
            User user = manager.createUser(false, email, password, lastName, firstName, email);

            //boolean success = userManager.updateUser(user);
            if (user != null) {

                JOptionPane.showMessageDialog(this, "Registration successful!");

                UserState.getInstance().updateCurrentUser(user);

                // Display a success message to the user or perform any other necessary actions
                JOptionPane.showMessageDialog(this, "Registration successful!");
            } else {
                // Display an error message or perform any other necessary actions
                JOptionPane.showMessageDialog(this, "Registration failed. Please try again.");
            }
        } else if (e.getSource() == resetButton) {
            // Clear all input fields
            lastNameField.setText("");
            firstNameField.setText("");
            emailField.setText("");
            passwordField.setText("");
            confirmPasswordField.setText("");
        }
    }
}
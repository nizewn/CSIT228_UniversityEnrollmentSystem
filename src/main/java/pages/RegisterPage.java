package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Arrays;

public class RegisterPage extends JPanel implements ActionListener {

    private JLabel titleLabel, personalInfoLabel, loginInfoLabel, profileImageLabel;
    private JTextField firstNameTextField, lastNameTextField, dobTextField, addressTextField, contactTextField, emailTextField, usernameTextField;
    private JPasswordField passwordField, confirmField;
    private JButton selectImageButton, submitButton;
    private JComboBox<String> genderComboBox;

    public RegisterPage() {
        super();

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        // add the title label
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 6;
        titleLabel = new JLabel("STUDENT REGISTRATION FORM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, constraints);

        //personal information label and components
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.WEST;
        personalInfoLabel = new JLabel("PERSONAL INFORMATION");
        personalInfoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(personalInfoLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.WEST;
        JLabel firstNameLabel = new JLabel("First Name:");
        add(firstNameLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        firstNameTextField = new JTextField(20);
        add(firstNameTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        JLabel lastNameLabel = new JLabel("Last Name:");
        add(lastNameLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        lastNameTextField = new JTextField(20);
        add(lastNameTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        JLabel genderLabel = new JLabel("Gender:");
        add(genderLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 4;
        String[] genderOptions = {"Male", "Female", "Other"};
        genderComboBox = new JComboBox<>(genderOptions);
        add(genderComboBox, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        JLabel dobLabel = new JLabel("Date of Birth:");
        add(dobLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 5;
        dobTextField = new JTextField(20);
        add(dobTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 6;
        JLabel addressLabel = new JLabel("Address:");
        add(addressLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 6;
        addressTextField = new JTextField(20);
        add(addressTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 7;
        JLabel contactLabel = new JLabel("Contact Number:");
        add(contactLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 7;
        contactTextField = new JTextField(20);

        add(contactTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 8;
        JLabel emailLabel = new JLabel("Email Address:");
        add(emailLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 8;
        emailTextField = new JTextField(20);
        add(emailTextField, constraints);

        // login information label and components
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.WEST;
        loginInfoLabel = new JLabel("LOGIN INFORMATION");
        loginInfoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(loginInfoLabel, constraints);

        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.EAST;
        JLabel usernameLabel = new JLabel("Username:");
        add(usernameLabel, constraints);

        constraints.gridx = 3;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        usernameTextField = new JTextField(20);
        add(usernameTextField, constraints);

        constraints.gridx = 2;
        constraints.gridy = 3;
        JLabel passwordLabel = new JLabel("Password:");
        add(passwordLabel, constraints);

        constraints.gridx = 3;
        constraints.gridy = 3;
        passwordField = new JPasswordField(20);
        add(passwordField, constraints);

        constraints.gridx = 2;
        constraints.gridy = 4;
        JLabel confirmLabel = new JLabel("Confirm Password:");
        add(confirmLabel, constraints);

        constraints.gridx = 3;
        constraints.gridy = 4;
        confirmField = new JPasswordField(20);
        add(confirmField, constraints);

        //profile image
        constraints.gridx = 2;
        constraints.gridy = 5;
        JLabel profileImage = new JLabel("PROFILE IMAGE");
        loginInfoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(profileImage, constraints);

        //wapa nko na tarung ari, di pa madisplay ang image sdskdk
        constraints.gridx = 3;
        constraints.gridy = 5;
        constraints.gridheight = 6;
        selectImageButton = new JButton("Select Image");
        selectImageButton.setPreferredSize(new Dimension(100, 100));
        selectImageButton.addActionListener(this);
        add(selectImageButton, constraints);

        constraints.gridx = 3;
        constraints.gridy = 6;
        profileImageLabel = new JLabel();
        add(profileImageLabel, constraints);

        //sumbit and clear buttons
        JPanel buttonPanel = new JPanel();
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        buttonPanel.add(submitButton);

        JButton cancelButton = new JButton("Clear");
        cancelButton.addActionListener(this);
        buttonPanel.add(cancelButton);

        constraints.gridx = 0;
        constraints.gridy = 14;
        constraints.gridwidth = 6;
        add(buttonPanel, constraints);
    }

    public void actionPerformed(ActionEvent e) {
        Object cancelButton = null;
        if (e.getSource() == selectImageButton) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(null);
            File file = fileChooser.getSelectedFile();
            profileImageLabel.setText(file.getName());
        } else if (e.getSource() == submitButton) {
            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();
            String dob = dobTextField.getText();
            String address = addressTextField.getText();
            String contact = contactTextField.getText();
            String email = emailTextField.getText();
            String username = usernameTextField.getText();
            String password = new String(passwordField.getPassword());
            String confirm = new String(confirmField.getPassword());

            if (firstName.isEmpty() || lastName.isEmpty() || dob.isEmpty() || address.isEmpty() || contact.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill out all fields");
            } else if (!Arrays.equals(passwordField.getPassword(), confirmField.getPassword())) {
                JOptionPane.showMessageDialog(null, "Passwords do not match");
            } else {
                JOptionPane.showMessageDialog(null, "Registration Successful");
            }
        } else if (e.getSource() == cancelButton) {
            firstNameTextField.setText("");
            lastNameTextField.setText("");
            genderComboBox.setSelectedIndex(0);
            dobTextField.setText("");
            addressTextField.setText("");
            contactTextField.setText("");
            emailTextField.setText("");
            usernameTextField.setText("");
            passwordField.setText("");
            confirmField.setText("");
        }
    }
}

// katong wala gigamit
/*
public void actionPerformed(ActionEvent e) {
        Object cancelButton = null;
    if (e.getSource() == selectImageButton) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null);
        File file = fileChooser.getSelectedFile();
        profileImageLabel.setText(file.getName());
    } else if (e.getSource() == submitButton) {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String dob = dobTextField.getText();
        String address = addressTextField.getText();
        String contact = contactTextField.getText();
        String email = emailTextField.getText();
        String username = usernameTextField.getText();
        String password = new String(passwordField.getPassword());
        String confirm = new String(confirmField.getPassword());

        if (firstName.isEmpty() || lastName.isEmpty() || dob.isEmpty() || address.isEmpty() || contact.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill out all fields");
        } else if (!Arrays.equals(passwordField.getPassword(), confirmField.getPassword())) {
            JOptionPane.showMessageDialog(null, "Passwords do not match");
        } else {
            JOptionPane.showMessageDialog(null, "Registration Successful");
        }
    } else if (e.getSource() == cancelButton) {
        System.exit(0);

    }
} */

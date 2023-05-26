/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pages;

import database.UserManager;
import entities.User;
import utils.UserState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JPanel implements ActionListener {

    private JLabel userLabel, passwordLabel, messageLabel;
    private JTextField userTextField;
    private JPasswordField passwordField;
    private JButton loginButton, resetButton;

    public LoginPage() {
        super();

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        // add the username label and text field
        constraints.gridx = 0;
        constraints.gridy = 0;
        userLabel = new JLabel("Username:");
        add(userLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        userTextField = new JTextField(20);
        add(userTextField, constraints);

        // add the password label and text field
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.NONE;
        passwordLabel = new JLabel("Password:");
        add(passwordLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        passwordField = new JPasswordField(20);
        add(passwordField, constraints);

        // add the login and reset buttons
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // added spacing between buttons
        loginButton = new JButton("Login");
        resetButton = new JButton("Clear");
        buttonPanel.add(loginButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0))); // added spacing between buttons
        buttonPanel.add(resetButton);
        add(buttonPanel, constraints);

        // add the message label
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        messageLabel = new JLabel();
        add(messageLabel, constraints);

        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = userTextField.getText();
            String password = new String(passwordField.getPassword());

            UserManager manager = new UserManager();
            User user = manager.loginUser(username, password);

            if (user != null) {
                messageLabel.setForeground(Color.GREEN);
                messageLabel.setText("Login successful!");

                UserState.getInstance().updateCurrentUser(user);
            } else {
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Invalid username or password");
            }
        } else if (e.getSource() == resetButton) {
            userTextField.setText("");
            passwordField.setText("");
            messageLabel.setText("");
        }
    }
}

package pages;

import javax.swing.*;
import java.awt.*;

public class HomePage extends JPanel {

    private JLabel titleLabel;
    private JButton registerButton;
    private JButton loginButton;
    private JLabel welcomeLabel;

    public HomePage() {
        super();
        initComponents();
        //addButtonListeners();

    }

    private void initComponents() {
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.CENTER;

        titleLabel = new JLabel("Welcome, students! It is time for another School Year.");
        titleLabel.setFont(new Font("", Font.PLAIN, 24));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        add(titleLabel, constraints);

        welcomeLabel = new JLabel("ENROLL NOW!");
        welcomeLabel.setFont(new Font("", Font.PLAIN, 24));
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        add(welcomeLabel, constraints);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        registerButton = new JButton("REGISTER");
        buttonPanel.add(registerButton);

        loginButton = new JButton("LOG IN");
        buttonPanel.add(loginButton);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        add(buttonPanel, constraints);
    }
}
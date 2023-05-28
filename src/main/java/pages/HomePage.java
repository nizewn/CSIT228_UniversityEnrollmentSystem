package pages;

import javax.swing.*;
import java.awt.*;

public class HomePage extends JPanel {

    private JLabel titleLabel;
    private JLabel welcomeLabel;
    private JLabel imageLabel;

    public HomePage() {
        super();

        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.CENTER;

        setBackground(Color.decode("#8c383e"));

        ImageIcon imageIcon = new ImageIcon("ueslogo.jpg");
        imageLabel = new JLabel(imageIcon);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        add(imageLabel, constraints);

        titleLabel = new JLabel("Welcome to Saint T University: Where Your Journey Begins!");
        titleLabel.setFont(new Font("", Font.PLAIN, 24));
        titleLabel.setForeground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        add(titleLabel, constraints);

        welcomeLabel = new JLabel("ENROLL NOW!");
        welcomeLabel.setFont(new Font("", Font.PLAIN, 24));
        welcomeLabel.setForeground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        add(welcomeLabel, constraints);
    }


}
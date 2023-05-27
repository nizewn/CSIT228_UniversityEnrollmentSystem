package pages;

import javax.swing.*;
import java.awt.*;

public class AnnouncementsPage extends JPanel {

    public AnnouncementsPage() {
        super(new GridLayout(1, 2));

        // Enrollment schedule columnS
        JPanel enrollmentPanel = new JPanel();
        enrollmentPanel.setBorder(BorderFactory.createTitledBorder("Enrollment Schedule"));
        JTextArea enrollmentText = new JTextArea("Enrollment Schedule:\n\n- Date 1: 1st June 2023\n- Date 2: 5th June 2023");
        enrollmentText.setEditable(false);
        enrollmentPanel.add(enrollmentText);
        add(enrollmentPanel);

        // Payment methods column
        JPanel paymentPanel = new JPanel();
        paymentPanel.setBorder(BorderFactory.createTitledBorder("Payment Methods"));
        JTextArea paymentText = new JTextArea("Payment Methods:\n\n- Credit Card\n- Bank Transfer\n- Cash");
        paymentText.setEditable(false);
        paymentPanel.add(paymentText);
        add(paymentPanel);
    }
}
package pages;

import database.AnnouncementManager;
import entities.Announcement;
import entities.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class AnnouncementsPage extends JPanel {

    private JTextArea enrollmentText;
    private JTextArea paymentText;

    public AnnouncementsPage() {
        super(new GridLayout(1, 2));

        // Enrollment schedule column
        JPanel enrollmentPanel = new JPanel();
        enrollmentPanel.setBorder(BorderFactory.createTitledBorder("Enrollment Schedule"));
        enrollmentText = new JTextArea();
        enrollmentText.setEditable(false);
        enrollmentPanel.add(enrollmentText);
        add(enrollmentPanel);

        // Payment methods column
        JPanel paymentPanel = new JPanel();
        paymentPanel.setBorder(BorderFactory.createTitledBorder("Payment Methods"));
        paymentText = new JTextArea();
        paymentText.setEditable(false);
        paymentPanel.add(paymentText);
        add(paymentPanel);

        // Button to add announcements
        JButton addButton = new JButton("Add Announcements");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAnnouncements();
            }
        });

        // Panel to hold the button
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        add(buttonPanel);
    }

    private void addAnnouncements() {
        String enrollmentSchedule = JOptionPane.showInputDialog(this, "Enter Enrollment Schedule:");
        String paymentMethods = JOptionPane.showInputDialog(this, "Enter Payment Methods:");

        enrollmentText.setText("Enrollment Schedule:\n\n" + enrollmentSchedule);
        paymentText.setText("Payment Methods:\n\n" + paymentMethods);

        // Save the announcements to the database
        AnnouncementManager announcementManager = new AnnouncementManager();
        int adminId = 2;
        User admin = new User(adminId, true, "admin", "password", "Admin", "User", 30);
        Date date = new Date();
        Announcement enrollmentAnnouncement = announcementManager.createAnnouncement(admin.getId(), date, enrollmentSchedule);
        Announcement paymentAnnouncement = announcementManager.createAnnouncement(admin.getId(), date, paymentMethods);

        if (enrollmentAnnouncement != null && paymentAnnouncement != null) {
            JOptionPane.showMessageDialog(this, "Announcements added successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add announcements. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
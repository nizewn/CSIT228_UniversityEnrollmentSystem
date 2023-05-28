package pages;

import admin.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class AdminPage extends JPanel {
    String[] tables = {"Users", "Courses", "Sections", "Enrollments", "Payments", "Announcements"};

    private CardLayout cardLayout;
    private JPanel adminPanel;

    public AdminPage() {
        super();

        JPanel comboPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel comboLabel = new JLabel("Table: ");
        JComboBox<String> tableComboBox = new JComboBox<>(tables);
        tableComboBox.addItemListener(l -> {
            if (l.getStateChange() == ItemEvent.SELECTED) {
                cardLayout.show(adminPanel, (String) l.getItem());
            }
        });
        comboPanel.add(comboLabel);
        comboPanel.add(tableComboBox);

        cardLayout = new CardLayout();
        adminPanel = new JPanel(cardLayout);

        // Add all admin panels
        UsersAdmin usersAdmin = new UsersAdmin();
        CoursesAdmin coursesAdmin = new CoursesAdmin();
        SectionsAdmin sectionsAdmin = new SectionsAdmin();
        EnrollmentsAdmin enrollmentsAdmin = new EnrollmentsAdmin();
        PaymentsAdmin paymentsAdmin = new PaymentsAdmin();
        AnnouncementsAdmin announcementsAdmin = new AnnouncementsAdmin();

        adminPanel.add(usersAdmin, "Users");
        adminPanel.add(coursesAdmin, "Courses");
        adminPanel.add(sectionsAdmin, "Sections");
        adminPanel.add(enrollmentsAdmin, "Enrollments");
        adminPanel.add(paymentsAdmin, "Payments");
        adminPanel.add(announcementsAdmin, "Announcements");

        setLayout(new BorderLayout());
        add(comboPanel, BorderLayout.NORTH);
        add(adminPanel, BorderLayout.CENTER);
    }
}

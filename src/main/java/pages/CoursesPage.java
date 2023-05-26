package pages;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.*;

public class CoursesPage extends JPanel {

    public CoursesPage() {
        super();

        // Create the search bar and label
        JLabel searchLabel = new JLabel("Search course:");
        JTextField searchField = new JTextField(20);
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);

        // Create the label for search results
        JLabel resultsLabel = new JLabel("Available classes for CSIT228");
        resultsLabel.setHorizontalAlignment(JLabel.CENTER);

        // Create the table
        String[] columnNames = {"Section", "Instructor", "Schedule", "Room #", ""};
        Object[][] data = {
            {"F1", "John Wick", "MWF 10-11 AM", "GLE-701", "Enroll"},
            {"F2", "Carl Johnson", "MWF 8-9 AM", "NGE-104", "Enroll"},
            {"F3", "Jon Snow", "TTH 9-11 AM", "NGE-102", "Enroll"},
            {"G1", "Gagamboy Jackson", "MWF 2-3 PM", "GLE-603", "Enroll"},
            {"G2", "Lady Gaga", "MWF 3-4 PM", "NGE-304", "Enroll"},
            {"G3", "Jane Doe", "TTH 2-4 PM", "GLE-202", "Enroll"},};
        JTable table = new JTable(data, columnNames);
        table.setDefaultEditor(Object.class, null);
        table.setPreferredScrollableViewportSize(new Dimension(800, 400));
        JScrollPane scrollPane = new JScrollPane(table);

        setLayout(new BorderLayout());

        add(searchPanel, BorderLayout.NORTH);
        add(resultsLabel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
    }
}

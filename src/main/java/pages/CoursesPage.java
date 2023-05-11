package pages;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
        table.setPreferredScrollableViewportSize(new Dimension(800, 100));
        JScrollPane scrollPane = new JScrollPane(table);

        // Add components to the window using GridBagLayout
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(searchPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(resultsLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        gbc.anchor = GridBagConstraints.CENTER;
        add(scrollPane, gbc);
    }
}

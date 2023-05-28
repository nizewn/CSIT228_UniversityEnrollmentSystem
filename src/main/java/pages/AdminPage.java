package pages;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminPage extends JPanel {
    String[] tables = {"Users", "Courses", "Sections", "Enrollments", "Payments", "Announcements"};
    private JComboBox<String> tableComboBox;
    private JButton createButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTable table;

    public AdminPage() {
        super();

        tableComboBox = new JComboBox<>(tables);

        createButton = new JButton("Create");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");

        // Create table
        String[] columnNames = {"Column 1", "Column 2", "Column 3", "Column 4", "Column 5"};
        DefaultTableModel tableModel = new DefaultTableModel(null, columnNames);
        table = new JTable(tableModel);

        // Set layout
        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        add(tableComboBox, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(new JScrollPane(table), BorderLayout.SOUTH);
    }
}

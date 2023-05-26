package pages;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class SchedulePage extends JPanel {

    private JTable table;

    public SchedulePage() {
        super();
        setLayout(new BorderLayout());

        // Create the label panel and add labels to it
        JPanel labelPanelWrapper = new JPanel(new BorderLayout());
        JPanel labelPanel = new JPanel(new GridLayout(3, 1, 20, 10));
        JLabel welcomeLabel = new JLabel("Welcome Back!");
        labelPanel.add(welcomeLabel);

        JLabel yearSemesterLabel = new JLabel("Academic Year : 2023");
        labelPanel.add(yearSemesterLabel);

        JLabel semesterLabel = new JLabel("Second Semester");
        labelPanel.add(semesterLabel);

        labelPanelWrapper.add(labelPanel, BorderLayout.NORTH);
        labelPanelWrapper.setBorder(new EmptyBorder(10, 0, 0, 0));
        add(labelPanelWrapper, BorderLayout.NORTH);

        // Create the table panel and add the table to it
        JPanel tablePanel = new JPanel(new GridLayout(1, 1));
        String[] columnNames = {"Faculty", "Course Code", "Description", "Section - Schedule - Room"};
        Object[][] rowData = {
            {"Faculty 1", "Code 1", "Description 1", "G2-MWF-GLE"},
            {"Faculty 2", "Code 2", "Description 2", "G2-MWF-GLE"},
            {"Faculty 3", "Code 3", "Description 3", "G2-MWF-GLE"},
            {"Faculty 4", "Code 4", "Description 4", "G2-MWF-GLE"},
            {"Faculty 5", "Code 5", "Description 5", "G2-MWF-GLE"},};
        DefaultTableModel tableModel = new DefaultTableModel(rowData, columnNames);
        table = new JTable(tableModel);
        int tableWidth = table.getPreferredSize().width;
        int numOfColumns = table.getColumnCount();
        int columnWidth = tableWidth / numOfColumns;
        TableColumn column;
        for (int i = 0; i < numOfColumns; i++) {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidth);
            JPanel headerPanel = new JPanel(new GridLayout(2, 1));
            headerPanel.add(new JLabel(columnNames[i]));
            headerPanel.add(new JTextField());
            column.setHeaderRenderer(new JPanelHeaderRenderer(headerPanel));
        }
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane);
        add(tablePanel, BorderLayout.CENTER);

        // Create the refresh button
        JButton refreshButton = new JButton("Refresh");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add an action listener for the refresh button
        refreshButton.addActionListener(e -> {
            // Perform refresh action here
            // For example, update the table data with new values
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Clear existing rows

            // Add new data to the table
            Object[][] newData = {
                {"Faculty 6", "Code 6", "Description 6", "G2-MWF-GLE"},
                {"Faculty 7", "Code 7", "Description 7", "G2-MWF-GLE"},
                {"Faculty 8", "Code 8", "Description 8", "G2-MWF-GLE"},};
            for (Object[] row : newData) {
                model.addRow(row);
            }
        });
    }

    private class JPanelHeaderRenderer implements javax.swing.table.TableCellRenderer {

        private JPanel panel;

        public JPanelHeaderRenderer(JPanel panel) {
            this.panel = panel;
        }

        public java.awt.Component getTableCellRendererComponent(
                javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return panel;
        }
    }
}

package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarPage extends JPanel {

    public CalendarPage() {
        super();

        setLayout(new BorderLayout());

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton dateButton = new JButton("Open Date Picker");
        buttonPanel.add(dateButton);

        // Create label panel
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel dateLabel = new JLabel("Selected Date: ");
        labelPanel.add(dateLabel);

        // Create table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] columnNames = {"Column 1", "Column 2", "Column 3", "Column 4", "Column 5"};
        String[][] data = {
            {"Row 1-1", "Row 1-2", "Row 1-3", "Row 1-4", "Row 1-5"},
            {"Row 2-1", "Row 2-2", "Row 2-3", "Row 2-4", "Row 2-5"},
            {"Row 3-1", "Row 3-2", "Row 3-3", "Row 3-4", "Row 3-5"},
            {"Row 4-1", "Row 4-2", "Row 4-3", "Row 4-4", "Row 4-5"},
            {"Row 5-1", "Row 5-2", "Row 5-3", "Row 5-4", "Row 5-5"}
        };
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Add components to the frame
        add(buttonPanel, BorderLayout.NORTH);
        add(labelPanel, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.SOUTH);

        // Action listener for the date button
        dateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open date picker or perform any other actions
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String selectedDate = dateFormat.format(new Date());
                dateLabel.setText("Selected Date: " + selectedDate);
            }
        });
    }
}

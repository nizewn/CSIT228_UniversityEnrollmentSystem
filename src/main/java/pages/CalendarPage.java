package pages;

import com.toedter.calendar.JCalendar;

import javax.swing.*;
import javax.swing.plaf.PanelUI;
import java.awt.*;

public class CalendarPage extends JPanel {

    public CalendarPage() {
        super();

        setLayout(new BorderLayout());

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JCalendar calendar = new JCalendar();
        UIDefaults defaults = UIManager.getDefaults();
        calendar.setUI((PanelUI) defaults.getUI(calendar));
        buttonPanel.add(calendar);

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
    }
}

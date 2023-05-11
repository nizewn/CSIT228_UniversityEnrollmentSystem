package pages;

import java.awt.*;
import javax.swing.*;

public class AccountBalancePage extends JPanel {

    public AccountBalancePage() {
        super();

        setLayout(new BorderLayout());

        JLabel studentIdLabel = new JLabel("Student ID no.: ");
        studentIdLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(studentIdLabel, BorderLayout.NORTH);

        String[] columnNames = {"Semester", "Year Level", "School Year", "Date", "Payment", "Balance"};
        Object[][] data = {
            {"            ", "             ", "             ", "             ", "             ", "             "},
            {"            ", "             ", "             ", "             ", "             ", "             "},
            {"            ", "             ", "             ", "             ", "             ", "             "},
            {"            ", "             ", "             ", "             ", "             ", "             "},
            {"            ", "             ", "             ", "             ", "             ", "             "},
            {"            ", "             ", "             ", "             ", "             ", "             "},
            {"            ", "             ", "             ", "             ", "             ", "             "},
            {"            ", "             ", "             ", "             ", "             ", "             "},
            {"            ", "             ", "             ", "             ", "             ", "             "},
            {"            ", "             ", "             ", "             ", "             ", "             "},
            {"            ", "             ", "             ", "             ", "             ", "             "},
            {"            ", "             ", "             ", "             ", "             ", "             "},
            {"            ", "             ", "             ", "             ", "             ", "             "},
            {"            ", "             ", "             ", "             ", "             ", "             "},
            {"            ", "             ", "             ", "             ", "             ", "             "},
            {"            ", "             ", "             ", "             ", "             ", "             "},
            {"            ", "             ", "             ", "             ", "             ", "             "},
            {"            ", "             ", "             ", "             ", "             ", "             "},
            {"            ", "             ", "             ", "             ", "             ", "             "},
            {"            ", "             ", "             ", "             ", "             ", "             "},
            {"            ", "             ", "             ", "             ", "             ", "             "},
            {"            ", "             ", "             ", "             ", "             ", "             "},
            {"            ", "             ", "             ", "             ", "             ", "             "},
            {"            ", "             ", "             ", "             ", "             ", "             "},
            {"            ", "             ", "             ", "             ", "             ", "             "},
            {"            ", "             ", "             ", "             ", "             ", "             "},
            {"            ", "             ", "             ", "             ", "             ", "             "},
            {"            ", "             ", "             ", "             ", "             ", "             "},
            {"            ", "             ", "             ", "             ", "             ", "             "},
            {"            ", "             ", "             ", "             ", "             ", "             "},
            {"            ", "             ", "             ", "             ", "             ", "             "},
            {"            ", "             ", "             ", "             ", "             ", "             "},};
        JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 250));
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);
    }
}

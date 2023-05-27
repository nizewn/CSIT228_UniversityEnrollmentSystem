package pages;

import entities.User;
import utils.UserEventListener;
import utils.UserState;

import javax.swing.*;
import java.awt.*;

public class AccountBalancePage extends JPanel implements UserEventListener {

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

        UserState.getInstance().addListener(this); // necessary ni para mogana ang onUserUpdate
    }

    @Override
    public void onUserUpdate(User user) {
        refreshData();
    }

    void refreshData() {
        // TODO: code diri para moshow ang data sa table
    }
}

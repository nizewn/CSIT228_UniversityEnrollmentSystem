package pages;

import entities.User;
import utils.UserEventListener;
import utils.UserState;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GradesPage extends JPanel implements UserEventListener {

    private JComboBox<String> dropdown;
    private JLabel label;
    private JTable table;

    public GradesPage() {
        super();
        setLayout(new BorderLayout());

        // Dropdown menu
        dropdown = new JComboBox<>(new String[]{"Option 1", "Option 2", "Option 3"});
        JPanel dropdownPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        dropdownPanel.add(dropdown);
        add(dropdownPanel, BorderLayout.NORTH);

        // Label
        label = new JLabel("This is a label");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);

        // Table
        String[] columnNames = {"Column 1", "Column 2", "Column 3", "Column 4"};
        Object[][] rowData = {
                {"Row 1", "Data 1", "Data 2", "Data 3"},
                {"Row 2", "Data 4", "Data 5", "Data 6"},
                {"Row 3", "Data 7", "Data 8", "Data 9"}
        };
        DefaultTableModel tableModel = new DefaultTableModel(rowData, columnNames);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.SOUTH);

        UserState.getInstance().addListener(this);
    }

    @Override
    public void onUserUpdate(User user) {
        if (user != null) {
            refreshData();
        }
    }

    void refreshData() {
        // TODO: code diri para moshow ang data sa table
    }
}

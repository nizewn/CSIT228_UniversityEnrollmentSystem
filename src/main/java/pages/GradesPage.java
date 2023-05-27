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
        DefaultTableModel tableModel = new DefaultTableModel(null, columnNames);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.SOUTH);

        UserState.getInstance().addListener(this);
    }

    @Override
    public void onUserUpdate(User user) {
        if (user != null) {
            //refreshData();
        }
    }

//    void refreshData() {
//        int userId = UserState.getInstance().getCurrentUser().getId();
//        EnrollmentManager enrollmentManager = new EnrollmentManager();
//        ArrayList<Enrollment> enrollments = enrollmentManager.getAllEnrollmentsByUser(userId);
//
//        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
//        tableModel.setRowCount(0);
//        for(Enrollment enrollment : enrollments) {
//            String[] data = {
//                    enrollment.getSection().getCourse().getCode(),
//                    enrollment.getSection().getCourse().getDescription(),
//                    enrollment.getSection().getInstructorName(),
//                    enrollment.
//            };
//            tableModel.addRow(data);
//        }
//    }
}

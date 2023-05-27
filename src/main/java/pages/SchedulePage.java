package pages;

import database.EnrollmentManager;
import entities.Enrollment;
import entities.Section;
import entities.User;
import utils.UserEventListener;
import utils.UserState;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;

public class SchedulePage extends JPanel implements UserEventListener {

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
        DefaultTableModel tableModel = new DefaultTableModel(null, columnNames);
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
            refreshData();
        });

        UserState.getInstance().addListener(this); // necessary ni para mogana ang onUserUpdate
    }

    @Override
    public void onUserUpdate(User user) {
        if (user != null) {
            refreshData();
        }
    }

    void refreshData() {
        int currentUserId = UserState.getInstance().getCurrentUser().getId();
        EnrollmentManager eManager = new EnrollmentManager();
        ArrayList<Enrollment> enrollments = eManager.getAllEnrollmentsByUser(currentUserId);

        DefaultTableModel model = (DefaultTableModel) table.getModel();

        model.setNumRows(0);
        for (Enrollment e : enrollments) {
            Section section = e.getSection();

            String[] data = {
                    section.getInstructorName(),
                    section.getCourse().getCode(),
                    section.getCourse().getDescription(),
                    section.getId() + " - " + section.getDays() + " - " + section.getLocation()
            };
            model.addRow(data);
        }
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

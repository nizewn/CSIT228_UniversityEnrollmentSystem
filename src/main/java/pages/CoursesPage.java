package pages;

import database.SectionManager;
import entities.Section;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class CoursesPage extends JPanel {

    JTable table;
    JLabel resultsLabel;

    public CoursesPage() {
        super();

        // Create the search bar and label
        JLabel searchLabel = new JLabel("Search course:");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchButton.addActionListener(e -> search(searchField.getText()));

        // Create the label for search results
        resultsLabel = new JLabel();
        resultsLabel.setHorizontalAlignment(JLabel.CENTER);

        // Create the table
        String[] columnNames = {"Section", "Description", "Instructor", "Schedule", "Room #"};
        table = new JTable(new DefaultTableModel(null, columnNames));
        table.setDefaultEditor(Object.class, null);
        table.setPreferredScrollableViewportSize(new Dimension(800, 400));
        JScrollPane scrollPane = new JScrollPane(table);

        setLayout(new BorderLayout());

        add(searchPanel, BorderLayout.NORTH);
        add(resultsLabel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
    }

    void search(String query) {
        SectionManager manager = new SectionManager();
        ArrayList<Section> sections = manager.searchSectionByCourse(query);

        resultsLabel.setText("Results for " + query);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (Section section : sections) {

            String[] data = {
                    section.getCourse().getCode() + "-" + section.getId(),
                    section.getCourse().getDescription(),
                    section.getInstructorName(),
                    section.getDays() + " " + section.getTimeStart() + "-" + section.getTimeEnd(),
                    section.getLocation()
            };

            model.addRow(data);
        }
    }
}

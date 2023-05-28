package admin;

import database.AnnouncementManager;
import entities.Announcement;
import utils.UserState;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AnnouncementsAdmin extends JPanel {
    private JTable table;

    public AnnouncementsAdmin() {
        super();

        // Create buttons
        JButton createButton = new JButton("Create");
        createButton.addActionListener(l -> openDialog(0));

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(l -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select an announcement.");
                return;
            }
            int id = (int) table.getValueAt(selectedRow, 0);
            openDialog(id);
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(l -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select an announcement.");
                return;
            }
            int id = (int) table.getValueAt(selectedRow, 0);
            AnnouncementManager announcementManager = new AnnouncementManager();
            announcementManager.deleteAnnouncement(id);
            refreshData();
        });

        // Create table
        String[] columnNames = {"ID", "Message", "Updated on", "By"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        table.setDefaultEditor(Object.class, null); // disable editing

        // Set layout
        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        refreshData();
    }

    void refreshData() {
        AnnouncementManager announcementManager = new AnnouncementManager();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        announcementManager.getAllAnnouncements().forEach(announcement -> model.addRow(new Object[]{
                announcement.getId(),
                announcement.getMessage(),
                dateFormat.format(announcement.getDate()),
                announcement.getAdmin().getLastName() + ", " + announcement.getAdmin().getFirstName()
        }));
    }

    void openDialog(int id) {
        String title = id == 0 ? "Create announcement" : "Update announcement";
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), title, Dialog.ModalityType.APPLICATION_MODAL);

        Announcement announcement;
        if (id != 0) {
            AnnouncementManager announcementManager = new AnnouncementManager();
            announcement = announcementManager.getAnnouncement(id);
        } else {
            announcement = null;
        }

        JLabel messageLabel = new JLabel("Message");
        JTextField messageField = new JTextField(announcement == null ? "" : announcement.getMessage());
        messageField.setPreferredSize(new Dimension(200, 24));

        JButton actionButton = new JButton(announcement == null ? "Create" : "Update");
        actionButton.addActionListener(l -> {
            AnnouncementManager manager = new AnnouncementManager();
            if (announcement != null) {
                manager.updateAnnouncement(announcement.getId(), messageField.getText());
            } else {
                manager.createAnnouncement(UserState.getInstance().getCurrentUser().getId(), new Date(), messageField.getText());
            }
            refreshData();
            dialog.dispose();
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(l -> dialog.dispose());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(announcement == null ? 2 : 3, 2));
        if (announcement != null) {
            panel.add(new JLabel("ID"));
            panel.add(new JLabel(String.valueOf(announcement.getId())));
        }
        panel.add(messageLabel);
        panel.add(messageField);
        panel.add(actionButton);
        panel.add(cancelButton);

        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}

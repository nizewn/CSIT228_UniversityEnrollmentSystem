package pages;

import database.AnnouncementManager;
import entities.Announcement;
import entities.User;
import utils.UserEventListener;
import utils.UserState;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AnnouncementsPage extends JPanel implements UserEventListener {

    private final JPanel announcementPanel;

    public AnnouncementsPage() {
        super(new BorderLayout());

        announcementPanel = new JPanel();
        announcementPanel.setLayout(new BoxLayout(announcementPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(announcementPanel);
        add(scrollPane, BorderLayout.CENTER);

        UserState.getInstance().addUpdateListener(this); // necessary ni para mogana ang onUserUpdate
    }

    @Override
    public void onUserUpdate(User user) {
        loadAnnouncements();
    }

    private void loadAnnouncements() {
        AnnouncementManager announcementManager = new AnnouncementManager();
        ArrayList<Announcement> announcements = announcementManager.getAllAnnouncements();

        for (Announcement announcement : announcements) {
            JLabel announcementLabel = createAnnouncementLabel(announcement.getMessage());
            announcementPanel.add(announcementLabel);
        }
    }

    private JLabel createAnnouncementLabel(String announcementText) {
        JLabel announcementLabel = new JLabel(announcementText);
        announcementLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        announcementLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return announcementLabel;
    }
}
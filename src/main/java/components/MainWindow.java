package components;

import entities.User;
import pages.*;
import utils.UserEventListener;
import utils.UserState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainWindow extends JFrame implements ActionListener, UserEventListener {

    private final int sidebarWidth = 150;
    private final String[] pageList = {
            "Home",
            "Login",
            "Register",
            "Student Information",
            "Schedule",
            "Courses",
            "Grades",
            "Calendar",
            "Announcements",
            "Account Balance",
            "Admin"
    };
    private JPanel sidebarPanel, contentPanel;
    private NavButton logoutButton;
    private CardLayout cardLayout;
    private ArrayList<NavButton> navButtons;
    private JLabel topLabel;

    public MainWindow() {
        super("University Enrollment System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setSize(900, 600);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // center to screen

        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        // Add all pages
        HomePage homePage = new HomePage();
        LoginPage loginPage = new LoginPage();
        RegisterPage registerPage = new RegisterPage();
        StudentInfoPage studentInfoPage = new StudentInfoPage();
        SchedulePage schedulePage = new SchedulePage();
        CoursesPage coursesPage = new CoursesPage();
        GradesPage gradesPage = new GradesPage();
        CalendarPage calendarPage = new CalendarPage();
        AnnouncementsPage announcementsPage = new AnnouncementsPage();
        AccountBalancePage accountBalancePage = new AccountBalancePage();
        AdminPage adminPage = new AdminPage();

        contentPanel.add(homePage, "Home");
        contentPanel.add(loginPage, "Login");
        contentPanel.add(registerPage, "Register");
        contentPanel.add(studentInfoPage, "Student Information");
        contentPanel.add(schedulePage, "Schedule");
        contentPanel.add(coursesPage, "Courses");
        contentPanel.add(gradesPage, "Grades");
        contentPanel.add(calendarPage, "Calendar");
        contentPanel.add(announcementsPage, "Announcements");
        contentPanel.add(accountBalancePage, "Account Balance");
        contentPanel.add(adminPage, "Admin");

        initSidebar();
        add(sidebarPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        UserState.getInstance().addUpdateListener(this);
    }

    private void initSidebar() {
        sidebarPanel = new JPanel();
        // slightly darker background
        sidebarPanel.setBackground(new Color(210, 210, 210));
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setPreferredSize(new Dimension(sidebarWidth, 600));

        topLabel = new JLabel("Welcome, guest!");
        topLabel.setOpaque(true);
        topLabel.setBackground(new Color(230, 230, 230));
        topLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topLabel.setVerticalAlignment(SwingConstants.CENTER);
        topLabel.setMaximumSize(new Dimension(sidebarWidth, 150));
        sidebarPanel.add(topLabel);

        navButtons = new ArrayList<NavButton>();
        for (String page : pageList) {
            NavButton btn = new NavButton(page, sidebarWidth);
            if (page.equals("Home")) {
                btn.setActive(true);
            }
            if (!page.equals("Login") && !page.equals("Register") && !page.equals("Home")) {
                btn.setVisible(false);
            }

            btn.addActionListener(this);
            navButtons.add(btn);
            sidebarPanel.add(btn);
        }

        logoutButton = new NavButton("Logout", sidebarWidth);
        logoutButton.addActionListener(this);
        logoutButton.setVisible(false);
        sidebarPanel.add(logoutButton);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logoutButton) {
            UserState.getInstance().updateCurrentUser(null);
            return;
        }

        if (e.getSource() instanceof NavButton navBtn) {
            switchTab(navBtn.getText());
        }
    }

    private void switchTab(String tabName) {
        // set last active button to inactive, and set new button to active
        for (NavButton btn : navButtons) {
            if (btn.isActive() && !btn.getText().equals(tabName)) {
                btn.setActive(false);
            } else if (btn.getText().equals(tabName)) {
                btn.setActive(true);
            }
        }

        cardLayout.show(contentPanel, tabName);
    }

    private String getActiveTab() {
        for (NavButton btn : navButtons) {
            if (btn.isActive()) {
                return btn.getText();
            }
        }
        return null;
    }

    @Override
    public void onUserUpdate(User user) {
        // update sidebar
        for (NavButton b : navButtons) {
            if (b.getText().equals("Login") || b.getText().equals("Register") || b.getText().equals("Home")) {
                b.setVisible(user == null);
            } else if (b.getText().equals("Admin")) {
                b.setVisible(user != null && user.isAdmin());
            } else {
                b.setVisible(user != null && !user.isAdmin());
            }
        }
        logoutButton.setVisible(user != null);

        if (user == null) {
            topLabel.setText("Welcome, guest!");
            switchTab("Home");
        } else {
            topLabel.setText("Welcome, " + user.getUsername() + "!");
            if (user.isAdmin()) {
                switchTab("Admin");
            } else if (getActiveTab() == null || !getActiveTab().equals("Courses")) {
                switchTab("Schedule");
            }
        }
    }
}

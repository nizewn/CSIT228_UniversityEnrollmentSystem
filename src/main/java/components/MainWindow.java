package components;

import pages.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainWindow extends JFrame implements ActionListener {

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
            "Account Balance"
    };
    private JPanel sidebarPanel, contentPanel;
    private CardLayout cardLayout;
    private ArrayList<NavButton> navButtons;

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

        initSidebar();
        add(sidebarPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void initSidebar() {
        sidebarPanel = new JPanel();
        // slightly darker background
        sidebarPanel.setBackground(new Color(210, 210, 210));
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setPreferredSize(new Dimension(sidebarWidth, 600));

        JLabel topLabel = new JLabel("Welcome, butwiki!");
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

            btn.addActionListener(this);
            navButtons.add(btn);
            sidebarPanel.add(btn);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof NavButton) {
            NavButton navBtn = (NavButton) e.getSource();

            // set last active button to inactive
            for (NavButton b : navButtons) {
                if (b.isActive() && b.getText() != navBtn.getText()) {
                    b.setActive(false);
                    break;
                }
            }

            navBtn.setActive(true);
            cardLayout.show(contentPanel, navBtn.getText());
        }
    }
}

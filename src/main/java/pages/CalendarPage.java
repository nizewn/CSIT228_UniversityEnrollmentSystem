package pages;

import com.toedter.calendar.JCalendar;
import database.EnrollmentManager;
import entities.Enrollment;
import entities.User;
import utils.UserEventListener;
import utils.UserState;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CalendarPage extends JPanel implements UserEventListener {
    ArrayList<Enrollment> enrollments;
    JTable table;

    public CalendarPage() {
        super();

        setLayout(new BorderLayout());

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JCalendar calendar = new JCalendar();
        calendar.setLocale(new Locale("en", "PH"));
        calendar.setPreferredSize(new Dimension(300, 200));
        buttonPanel.add(calendar);
        calendar.addPropertyChangeListener(p -> {
            Calendar c = Calendar.getInstance();
            c.setTime(calendar.getDate());

            updateTable(c.get(Calendar.DAY_OF_WEEK));
        });
        calendar.getDayChooser().setAlwaysFireDayProperty(true);

        // Create label panel
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel dateLabel = new JLabel();
        labelPanel.add(dateLabel);

        // Create table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] columnNames = {"Course & Section", "Description", "Time", "Location"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setDefaultEditor(Object.class, null); // disable editing
        table.setPreferredScrollableViewportSize(new Dimension(800, 320));
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Add components to the frame
        add(buttonPanel, BorderLayout.NORTH);
        add(labelPanel, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.SOUTH);

        UserState.getInstance().addUpdateListener(this);
    }

    @Override
    public void onUserUpdate(User user) {
        if (user != null) {
            refreshData();
        }
    }

    void refreshData() {
        int userId = UserState.getInstance().getCurrentUser().getId();
        EnrollmentManager enrollmentManager = new EnrollmentManager();
        enrollments = enrollmentManager.getAllEnrollmentsByUser(userId);
    }

    void updateTable(int dayOfWeek) {
        if (enrollments == null) {
            return;
        }

        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

        for (Enrollment enrollment : enrollments) {
            if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY)
                continue; // No classes on weekends, yehey
            if ((dayOfWeek == Calendar.MONDAY || dayOfWeek == Calendar.WEDNESDAY || dayOfWeek == Calendar.FRIDAY) && !enrollment.getSection().getDays().equals("MWF"))
                continue;
            if ((dayOfWeek == Calendar.TUESDAY || dayOfWeek == Calendar.THURSDAY) && !enrollment.getSection().getDays().equals("TTH"))
                continue;

            LocalTime localTimeStart = enrollment.getSection().getTimeStart().toLocalTime();
            LocalTime localTimeEnd = enrollment.getSection().getTimeEnd().toLocalTime();
            String timeStart = localTimeStart.format(formatter);
            String timeEnd = localTimeEnd.format(formatter);

            String[] data = {
                    enrollment.getSection().getCourse().getCode() + "-" + enrollment.getSection().getId(),
                    enrollment.getSection().getCourse().getDescription(),
                    timeStart + "-" + timeEnd,
                    enrollment.getSection().getLocation()
            };
            tableModel.addRow(data);
        }
    }
}

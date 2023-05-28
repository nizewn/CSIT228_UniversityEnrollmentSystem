package admin;

import com.toedter.calendar.JDateChooser;
import database.PaymentManager;
import database.UserManager;
import entities.Payment;
import utils.UserState;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.text.SimpleDateFormat;

public class PaymentsAdmin extends JPanel {
    JComboBox<String> userMenu;
    JTable table;
    JLabel userInfoLabel;

    int selectedUserId = 0;


    public PaymentsAdmin() {
        super();

        JPanel userPanel = new JPanel();
        JLabel userLabel = new JLabel("User: ");
        userMenu = new JComboBox<>();

        JButton btnRefreshUsers = new JButton("Refresh Users");
        btnRefreshUsers.addActionListener(l -> {
            refreshUserList();
        });

        userPanel.add(userLabel);
        userPanel.add(userMenu);
        userPanel.add(btnRefreshUsers);

        // Create buttons
        JButton createButton = new JButton("Create");
        createButton.addActionListener(l -> openDialog(0));

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(l -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a payment.");
                return;
            }
            int id = (int) table.getValueAt(selectedRow, 0);
            openDialog(id);
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(l -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a payment.");
                return;
            }
            int id = (int) table.getValueAt(selectedRow, 0);
            PaymentManager manager = new PaymentManager();
            manager.deletePayment(id);
            refreshData();
        });

        // Create table
        String[] columnNames = {"ID", "Amount", "Date", "Last edited by"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        table.setDefaultEditor(Object.class, null); // disable editing

        setLayout(new BorderLayout());
        add(userPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        userInfoLabel = new JLabel();

        JPanel labelAndButtonPanel = new JPanel();
        labelAndButtonPanel.setLayout(new BoxLayout(labelAndButtonPanel, BoxLayout.Y_AXIS));
        labelAndButtonPanel.add(userInfoLabel);
        labelAndButtonPanel.add(buttonPanel);

        add(labelAndButtonPanel, BorderLayout.CENTER);
        add(new JScrollPane(table), BorderLayout.SOUTH);

        refreshUserList();
        userMenu.addItemListener(l -> {
            if (l.getStateChange() == ItemEvent.SELECTED) {
                String course = (String) l.getItem();
                String[] courseParts = course.split("_");
                selectedUserId = Integer.parseInt(courseParts[0]);
                refreshData();
            }
        });
    }

    void refreshUserList() {
        userMenu.removeAll();
        UserManager userManager = new UserManager();
        userManager.getAllUsers().forEach(user -> {
            userMenu.addItem(user.getId() + "_" + user.getLastName() + ", " + user.getFirstName());
        });
        refreshData();
    }

    void openDialog(int id) {
        String title = id == 0 ? "Create payment" : "Update payment";
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), title, Dialog.ModalityType.APPLICATION_MODAL);

        Payment payment;
        if (id != 0) {
            PaymentManager paymentManager = new PaymentManager();
            payment = paymentManager.getPayment(id);
        } else {
            payment = null;
        }

        // amount field
        JLabel amountLabel = new JLabel("Amount");
        JFormattedTextField amountField = new JFormattedTextField();
        amountField.setValue(0);
        amountField.setColumns(10);
        if (payment != null) {
            amountField.setValue(payment.getAmount());
        }

        // date field
        JLabel dateLabel = new JLabel("Date");
        JDateChooser dateChooser = new JDateChooser();
        if (payment != null) {
            dateChooser.setDate(payment.getDate());
        }

        JButton actionButton = new JButton(payment == null ? "Create" : "Update");
        actionButton.addActionListener(l -> {
            PaymentManager manager = new PaymentManager();
            if (payment != null) {
                payment.setDate(dateChooser.getDate());
                payment.setAdmin(UserState.getInstance().getCurrentUser());
                payment.setAmount((int) amountField.getValue());
                manager.updatePayment(payment);
            } else {
                manager.createPayment(
                        selectedUserId,
                        UserState.getInstance().getCurrentUser().getId(),
                        (int) amountField.getValue(),
                        dateChooser.getDate()
                );
            }
            refreshData();
            dialog.dispose();
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(l -> dialog.dispose());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(payment == null ? 3 : 4, 2));
        if (payment != null) {
            panel.add(new JLabel("ID"));
            panel.add(new JLabel(String.valueOf(payment.getId())));
        }

        panel.add(amountLabel);
        panel.add(amountField);

        panel.add(dateLabel);
        panel.add(dateChooser);

        panel.add(actionButton);
        panel.add(cancelButton);

        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    void refreshData() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        if (selectedUserId == 0) {
            return;
        }
        PaymentManager paymentManager = new PaymentManager();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        paymentManager.getAllPaymentsByUser(selectedUserId).forEach(payment -> {
            userInfoLabel.setText(payment.getUser().getLastName() + ", " + payment.getUser().getFirstName());

            model.addRow(new Object[]{
                    payment.getId(),
                    payment.getAmount(),
                    dateFormat.format(payment.getDate()),
                    payment.getAdmin().getLastName() + ", " + payment.getAdmin().getFirstName()
            });
        });
    }
}

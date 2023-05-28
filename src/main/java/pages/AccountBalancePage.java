package pages;

import database.PaymentManager;
import entities.Payment;
import entities.User;
import utils.UserEventListener;
import utils.UserState;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AccountBalancePage extends JPanel implements UserEventListener {

    JLabel balanceInfoLabel;
    JTable table;

    public AccountBalancePage() {
        super();

        setLayout(new BorderLayout());

        balanceInfoLabel = new JLabel("Balance: ");
        add(balanceInfoLabel, BorderLayout.NORTH);

        String[] columnNames = {"Date", "Amount", "Verified by", "Remaining balance"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(500, 250));
        table.setDefaultEditor(Object.class, null); // disable editing
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.SOUTH);

        UserState.getInstance().addUpdateListener(this); // necessary ni para mogana ang onUserUpdate
    }

    @Override
    public void onUserUpdate(User user) {
        if (user != null) {
            refreshData();
        }
    }

    void refreshData() {
        PaymentManager paymentManager = new PaymentManager();
        User user = UserState.getInstance().getCurrentUser();

        ArrayList<Payment> payments = paymentManager.getAllPaymentsByUser(user.getId());
        int remainingBalance = user.getTuitionFee();

        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        tableModel.setRowCount(0);
        for (Payment payment : payments) {
            remainingBalance -= payment.getAmount();

            String[] row = {
                    dateFormat.format(payment.getDate()),
                    payment.getAmount() + " PHP",
                    payment.getAdmin().getLastName() + ", " + payment.getAdmin().getFirstName(),
                    remainingBalance + " PHP"
            };

            tableModel.addRow(row);
        }
        balanceInfoLabel.setText("<html>Total tuition for this semester: <b>" +
                user.getTuitionFee() +
                " PHP</b><br/>Remaining Balance: <b>" +
                remainingBalance +
                " PHP</b></html>");
    }
}

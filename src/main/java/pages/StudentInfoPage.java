package pages;

import database.UserManager;
import entities.User;
import utils.UserEventListener;
import utils.UserState;

import javax.swing.*;
import java.awt.*;

public class StudentInfoPage extends JPanel implements UserEventListener {

    private FieldPanel lastNamePanel,
            firstNamePanel,
            emailPanel,
            usernamePanel,
            passwordPanel;

    public StudentInfoPage() {
        super();
        setLayout(new FlowLayout(FlowLayout.CENTER, 8, 10));

        lastNamePanel = new FieldPanel("Last name");
        add(lastNamePanel);

        firstNamePanel = new FieldPanel("First name");
        add(firstNamePanel);

        usernamePanel = new FieldPanel("Username");
        add(usernamePanel);

        emailPanel = new FieldPanel("Email");
        add(emailPanel);

        passwordPanel = new FieldPanel("New password (optional)");
        add(passwordPanel);

        JButton saveButton = new JButton("Save");
        add(saveButton);
        saveButton.addActionListener(l -> saveInfo());

        UserState.getInstance().addUpdateListener(this); // necessary ni para mogana ang onUserUpdate
    }

    @Override
    public void onUserUpdate(User user) {
        if (user != null) {
            refreshData();
        }
    }

    void refreshData() {
        User currentUser = UserState.getInstance().getCurrentUser();
        lastNamePanel.setTextValue(currentUser.getLastName());
        firstNamePanel.setTextValue(currentUser.getFirstName());
        usernamePanel.setTextValue(currentUser.getUsername());
        emailPanel.setTextValue(currentUser.getEmail());
        passwordPanel.setTextValue("");
    }

    void saveInfo() {
        User currentUser = UserState.getInstance().getCurrentUser();
        UserManager userManager = new UserManager();
        currentUser.setLastName(lastNamePanel.getTextValue());
        currentUser.setFirstName(firstNamePanel.getTextValue());
        currentUser.setUsername(usernamePanel.getTextValue());
        currentUser.setEmail(emailPanel.getTextValue());

        if (!passwordPanel.getTextValue().isEmpty()) {
            userManager.updatePassword(currentUser.getId(), passwordPanel.getTextValue());
        }
        boolean success = userManager.updateUser(currentUser);
        if (success) {
            JOptionPane.showMessageDialog(this, "Successfully updated user info!");
            UserState.getInstance().updateCurrentUser(currentUser);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update user info!");
            refreshData();
        }
    }
}

class FieldPanel extends JPanel {

    private JLabel label;
    private JTextField textField;

    public FieldPanel(String labelName) {
        super(new BorderLayout());
        label = new JLabel(labelName);
        if (labelName.contains("password")) {
            textField = new JPasswordField(50);
        } else {
            textField = new JTextField(50);
        }
        add(label, BorderLayout.NORTH);
        add(textField, BorderLayout.SOUTH);
    }

    public String getLabelText() {
        return label.getText();
    }

    public void setLabelText(String text) {
        label.setText(text);
    }

    public String getTextValue() {
        return textField.getText();
    }

    public void setTextValue(String text) {
        textField.setText(text);
    }

    public void setEditable(boolean editable) {
        textField.setEditable(editable);
    }

    public void setWidth(int width) {
        setPreferredSize(new Dimension(width, getPreferredSize().height));
        textField.setPreferredSize(new Dimension(width, textField.getPreferredSize().height));
    }
}

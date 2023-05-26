package pages;

import java.awt.*;
import javax.swing.*;

public class StudentInfoPage extends JPanel {

    private FieldPanel lastNamePanel,
            firstNamePanel,
            emailPanel,
            usernamePanel,
            yearlevelPanel;

    // pag add lang ^
    public StudentInfoPage() {
        super();
        setLayout(new FlowLayout(FlowLayout.CENTER, 8, 10));

        int separatorCount = 3;
        JSeparator[] separators = new JSeparator[separatorCount];
        for (int i = 0; i < separatorCount; i++) {
            separators[i] = new JSeparator();
            separators[i].setOrientation(SwingConstants.HORIZONTAL);
            separators[i].setPreferredSize(new Dimension(650, 10));
            separators[i].setForeground(new Color(200, 200, 200));
            separators[i].setBackground(null);
        }

        lastNamePanel = new FieldPanel("Last name");
        add(lastNamePanel);

        firstNamePanel = new FieldPanel("First name");
        add(firstNamePanel);

        yearlevelPanel = new FieldPanel("Year Level");
        add(yearlevelPanel);

        usernamePanel = new FieldPanel("Username");
        add(usernamePanel);

        emailPanel = new FieldPanel("Email");
        add(emailPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("Save");
        JButton refreshButton = new JButton("Refresh");
        buttonPanel.add(saveButton);
        buttonPanel.add(refreshButton);
        buttonPanel.setPreferredSize(new Dimension(500, getPreferredSize().height));
        add(buttonPanel);

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.add(new StudentInfoPage());
        frame.setVisible(true);
    }
}
// tanawa lang nya ni or pwede ka mag add2

class FieldPanel extends JPanel {

    private JLabel label;
    private JTextField textField;

    public FieldPanel(String labelName) {
        super(new BorderLayout());
        label = new JLabel(labelName);
        textField = new JTextField(50);
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

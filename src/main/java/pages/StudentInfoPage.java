package pages;

import java.awt.*;
import javax.swing.*;

public class StudentInfoPage extends JPanel {

    private FieldPanel lastNamePanel,
            firstNamePanel,
            idNumberPanel,
            programPanel,
            yearPanel;
    // pag add lang ^

    public StudentInfoPage() {
        super();
        setLayout(new FlowLayout(1, 8, 10));

        int separatorCount = 3;
        JSeparator[] separators = new JSeparator[separatorCount];
        for (int i = 0; i < separatorCount; i++) {
            separators[i] = new JSeparator();
            separators[i].setOrientation(SwingConstants.HORIZONTAL);
            separators[i].setPreferredSize(new Dimension(650, 8));
            separators[i].setForeground(new Color(200, 200, 200));
            separators[i].setBackground(null);
        }

        lastNamePanel = new FieldPanel("Last name");
        add(lastNamePanel);
        firstNamePanel = new FieldPanel("First name");
        add(firstNamePanel);

        idNumberPanel = new FieldPanel("ID Number");
        idNumberPanel.setTextValue("123-test-456");
        idNumberPanel.setEditable(false);
        add(idNumberPanel);

        // separators para gray line
        add(separators[0]);

        programPanel = new FieldPanel("Test");
        add(programPanel);

        add(separators[1]);

        yearPanel = new FieldPanel("Test nasad");
        yearPanel.setWidth(680); // para mogamit full width
        add(yearPanel);

        add(separators[2]);

        // ^ examples rani, usba2 lang
        // FieldPanel ray need icreate, sagol nana syag Label og TextField,
        // nya add() ra ditso
    }
}

// tanawa lang nya ni or pwede ka mag add2
class FieldPanel extends JPanel {

    private JLabel label;
    private JTextField textField;

    public FieldPanel(String labelName) {
        super(new BorderLayout());
        label = new JLabel(" " + labelName);
        textField = new JTextField(20);
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

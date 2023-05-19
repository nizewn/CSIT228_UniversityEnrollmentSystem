package com.groupone.universityenrollmentsystem;

import com.formdev.flatlaf.FlatLightLaf;
import components.MainWindow;
import javax.swing.UIManager;

public class UniversityEnrollmentSystem {

    public static void main(String[] args) {
        // FlatLaf theme
        FlatLightLaf.setup();
        // rounded corners hehe
        UIManager.put("Button.arc", 999);
        UIManager.put("Component.arc", 999);
        UIManager.put("ProgressBar.arc", 999);
        UIManager.put("TextComponent.arc", 999);

        MainWindow app = new MainWindow();
        app.setVisible(true);
    }
}

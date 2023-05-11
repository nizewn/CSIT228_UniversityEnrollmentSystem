package components;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class NavButton extends JButton implements MouseListener {

    private boolean active = false;

    public NavButton(String text, int width) {
        super(text);

        setMaximumSize(new Dimension(width, 50));
        setBackground(null);
        setBorder(null);
        setFocusPainted(false);

        addMouseListener(this);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (!active) {
            setBackground(Color.LIGHT_GRAY);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (!active) {
            setBackground(null);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // unused
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // unused
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // unused, handled in MainWindow
    }

    public void setActive(boolean active) {
        if (active) {
            setBackground(new Color(140, 56, 62)); // maroon
            setForeground(Color.WHITE);
        } else {
            setBackground(null);
            setForeground(null);
        }

        this.active = active;
    }

    public boolean getActive() {
        return this.active;
    }
}

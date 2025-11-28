package com.shreyash.banking;

import com.shreyash.banking.ui.MainFrame;
import com.shreyash.banking.ui.LoginPanel;
import javax.swing.SwingUtilities;

public class AppMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.showPanel(new LoginPanel(frame));
            frame.setVisible(true);
        });
    }
}

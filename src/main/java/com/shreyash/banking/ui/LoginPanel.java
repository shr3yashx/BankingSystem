package com.shreyash.banking.ui;

import com.shreyash.banking.dao.UserDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginPanel extends JPanel {
    private JTextField emailField = new JTextField(20);
    private JPasswordField passField = new JPasswordField(20);
    private UserDAO userDAO = new UserDAO();
    private MainFrame frame;

    public LoginPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8,8,8,8);

        c.gridx = 0; c.gridy = 0; add(new JLabel("Email"), c);
        c.gridx = 1; add(emailField, c);
        c.gridx = 0; c.gridy = 1; add(new JLabel("Password"), c);
        c.gridx = 1; add(passField, c);

        JButton loginBtn = new JButton("Login");
        JButton regBtn = new JButton("Register");

        c.gridx=0; c.gridy=2; add(loginBtn, c);
        c.gridx=1; add(regBtn, c);

        loginBtn.addActionListener((ActionEvent e) -> {
            try {
                int userId = userDAO.login(emailField.getText(), new String(passField.getPassword()));
                if (userId != -1) {
                    JOptionPane.showMessageDialog(this, "Login successful");
                    frame.showPanel(new DashboardPanel(frame, userId));
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid login");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        regBtn.addActionListener((ActionEvent e) -> {
            String name = JOptionPane.showInputDialog(this, "Name");
            String email = emailField.getText();
            String pass = new String(passField.getPassword());
            try {
                boolean ok = userDAO.register(name, email, pass);
                if (ok) JOptionPane.showMessageDialog(this, "Registered");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
    }
}

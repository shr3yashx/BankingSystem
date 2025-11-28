package com.shreyash.banking.ui;

import com.shreyash.banking.dao.AccountDAO;
import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {
    private AccountDAO accountDAO = new AccountDAO();
    private MainFrame frame;
    private int userId;

    public DashboardPanel(MainFrame frame, int userId) {
        this.frame = frame;
        this.userId = userId;
        setLayout(new BorderLayout());
        JPanel controls = new JPanel(new GridLayout(6,1,6,6));
        JButton create = new JButton("Create Account");
        JButton balance = new JButton("Check Balance");
        JButton deposit = new JButton("Deposit");
        JButton withdraw = new JButton("Withdraw");
        JButton transfer = new JButton("Transfer");
        JButton logout = new JButton("Logout");
        controls.add(create);
        controls.add(balance);
        controls.add(deposit);
        controls.add(withdraw);
        controls.add(transfer);
        controls.add(logout);
        add(controls, BorderLayout.WEST);

        JTextArea output = new JTextArea();
        output.setEditable(false);
        add(new JScrollPane(output), BorderLayout.CENTER);

        create.addActionListener(e -> {
            try {
                String acc = accountDAO.createAccount(userId);
                output.append("Created account: " + acc + "\n");
            } catch (Exception ex) {
                output.append("Error: " + ex.getMessage() + "\n");
            }
        });

        balance.addActionListener(e -> {
            String acc = JOptionPane.showInputDialog(this, "Account Number");
            try {
                double bal = accountDAO.getBalance(acc);
                output.append(acc + " balance: " + bal + "\n");
            } catch (Exception ex) {
                output.append("Error: " + ex.getMessage() + "\n");
            }
        });

        deposit.addActionListener(e -> {
            String acc = JOptionPane.showInputDialog(this, "Account Number");
            String amt = JOptionPane.showInputDialog(this, "Amount");
            try {
                boolean ok = accountDAO.deposit(acc, Double.parseDouble(amt));
                output.append("Deposit " + (ok ? "successful" : "failed") + "\n");
            } catch (Exception ex) {
                output.append("Error: " + ex.getMessage() + "\n");
            }
        });

        withdraw.addActionListener(e -> {
            String acc = JOptionPane.showInputDialog(this, "Account Number");
            String amt = JOptionPane.showInputDialog(this, "Amount");
            try {
                boolean ok = accountDAO.withdraw(acc, Double.parseDouble(amt));
                output.append("Withdraw " + (ok ? "successful" : "failed") + "\n");
            } catch (Exception ex) {
                output.append("Error: " + ex.getMessage() + "\n");
            }
        });

        transfer.addActionListener(e -> {
            String from = JOptionPane.showInputDialog(this, "From Account");
            String to = JOptionPane.showInputDialog(this, "To Account");
            String amt = JOptionPane.showInputDialog(this, "Amount");
            try {
                boolean ok = accountDAO.transfer(from, to, Double.parseDouble(amt));
                output.append("Transfer " + (ok ? "successful" : "failed") + "\n");
            } catch (Exception ex) {
                output.append("Error: " + ex.getMessage() + "\n");
            }
        });

        logout.addActionListener(e -> {
            frame.showPanel(new LoginPanel(frame));
        });
    }
}

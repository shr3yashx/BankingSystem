package com.shreyash.banking.dao;


import com.shreyash.banking.db.DBConnection;
import java.sql.*;
import java.util.Random;

public class AccountDAO {

    private String generateAccountNumber() {
        return "AC" + (100000 + new Random().nextInt(900000));
    }

    public String createAccount(int userId) throws SQLException {
        String accNo = generateAccountNumber();
        String sql = "INSERT INTO accounts(user_id, account_number, balance) VALUES(?,?,0)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, accNo);
            ps.executeUpdate();
            return accNo;
        }
    }

    public double getBalance(String accNo) throws SQLException {
        String sql = "SELECT balance FROM accounts WHERE account_number=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, accNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble("balance");
            throw new SQLException("Account not found");
        }
    }

    public boolean deposit(String accNo, double amount) throws SQLException {
        String update = "UPDATE accounts SET balance = balance + ? WHERE account_number=?";
        String tx = "INSERT INTO transactions(account_number,type,amount,balance_after) VALUES(?,?,?,?)";
        try (Connection c = DBConnection.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps = c.prepareStatement(update)) {
                ps.setDouble(1, amount);
                ps.setString(2, accNo);
                int count = ps.executeUpdate();
                if (count == 0) throw new SQLException("Account not found");
            }
            double newBal = getBalance(accNo);
            try (PreparedStatement ps2 = c.prepareStatement(tx)) {
                ps2.setString(1, accNo);
                ps2.setString(2, "DEPOSIT");
                ps2.setDouble(3, amount);
                ps2.setDouble(4, newBal);
                ps2.executeUpdate();
            }
            c.commit();
            return true;
        }
    }

    public boolean withdraw(String accNo, double amount) throws SQLException {
        double current = getBalance(accNo);
        if (current < amount) return false;
        String update = "UPDATE accounts SET balance = balance - ? WHERE account_number=?";
        String tx = "INSERT INTO transactions(account_number,type,amount,balance_after) VALUES(?,?,?,?)";
        try (Connection c = DBConnection.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps = c.prepareStatement(update)) {
                ps.setDouble(1, amount);
                ps.setString(2, accNo);
                ps.executeUpdate();
            }
            double newBal = getBalance(accNo);
            try (PreparedStatement ps2 = c.prepareStatement(tx)) {
                ps2.setString(1, accNo);
                ps2.setString(2, "WITHDRAW");
                ps2.setDouble(3, amount);
                ps2.setDouble(4, newBal);
                ps2.executeUpdate();
            }
            c.commit();
            return true;
        }
    }

    public boolean transfer(String fromAcc, String toAcc, double amount) throws SQLException {
        double balFrom = getBalance(fromAcc);
        if (balFrom < amount) return false;
        try (Connection c = DBConnection.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps1 = c.prepareStatement(
                    "UPDATE accounts SET balance = balance - ? WHERE account_number=?");
                 PreparedStatement ps2 = c.prepareStatement(
                         "UPDATE accounts SET balance = balance + ? WHERE account_number=?")) {
                ps1.setDouble(1, amount);
                ps1.setString(2, fromAcc);
                ps1.executeUpdate();
                ps2.setDouble(1, amount);
                ps2.setString(2, toAcc);
                ps2.executeUpdate();
            }
            double bal1 = getBalance(fromAcc);
            double bal2 = getBalance(toAcc);
            try (PreparedStatement tx1 = c.prepareStatement(
                    "INSERT INTO transactions(account_number,type,amount,balance_after) VALUES(?,?,?,?)");
                 PreparedStatement tx2 = c.prepareStatement(
                         "INSERT INTO transactions(account_number,type,amount,balance_after) VALUES(?,?,?,?)")) {
                tx1.setString(1, fromAcc);
                tx1.setString(2, "TRANSFER_OUT");
                tx1.setDouble(3, amount);
                tx1.setDouble(4, bal1);
                tx1.executeUpdate();

                tx2.setString(1, toAcc);
                tx2.setString(2, "TRANSFER_IN");
                tx2.setDouble(3, amount);
                tx2.setDouble(4, bal2);
                tx2.executeUpdate();
            }
            c.commit();
            return true;
        }
    }
}

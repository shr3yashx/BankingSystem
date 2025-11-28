package com.shreyash.banking.dao;

import com.shreyash.banking.db.DBConnection;
import java.sql.*;

public class UserDAO {
    public boolean register(String name, String email, String pass) throws SQLException {
        String sql = "INSERT INTO users(name,email,password) VALUES(?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, pass);
            ps.executeUpdate();
            return true;
        }
    }

    public int login(String email, String pass) throws SQLException {
        String sql = "SELECT id FROM users WHERE email=? AND password=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id");
            return -1;
        }
    }
}

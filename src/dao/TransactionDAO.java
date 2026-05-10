/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Pedro
 */
//package dao;

import model.Transaction;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    
    public void addTransaction(Transaction t) {
        String sql = "INSERT INTO transactions (user_id, tipo, operadora, valor, categoria, motivo, contacto_outra_parte) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, t.getUserId());
            stmt.setString(2, t.getTipo());
            stmt.setString(3, t.getOperadora());
            stmt.setDouble(4, t.getValor());
            stmt.setString(5, t.getCategoria());
            stmt.setString(6, t.getMotivo());
            stmt.setString(7, t.getContactoOutraParte());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Transaction> getTransactionsByUser(int userId) {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE user_id = ? ORDER BY data_hora DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Transaction t = new Transaction();
                t.setId(rs.getInt("id"));
                t.setUserId(rs.getInt("user_id"));
                t.setTipo(rs.getString("tipo"));
                t.setOperadora(rs.getString("operadora"));
                t.setValor(rs.getDouble("valor"));
                t.setCategoria(rs.getString("categoria"));
                t.setMotivo(rs.getString("motivo"));
                t.setContactoOutraParte(rs.getString("contacto_outra_parte"));
                t.setDataHora(rs.getTimestamp("data_hora"));
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public void deleteTransaction(int id) {
        String sql = "DELETE FROM transactions WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

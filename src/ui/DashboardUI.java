/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

/**
 *
 * @author Pedro
 */
//package ui;

//package ui;

import model.User;
import model.Transaction;
import dao.TransactionDAO;
import dao.UserDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DashboardUI extends JFrame {
    private User user;
    private TransactionDAO transactionDAO = new TransactionDAO();
    private UserDAO userDAO = new UserDAO();
    private JLabel lblSaldo;
    private JTable table;
    private DefaultTableModel tableModel;
    
    public DashboardUI(User user) {
        this.user = user;
        setTitle("TrackMoney - Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Topo com saldo
        JPanel topPanel = new JPanel();
        lblSaldo = new JLabel();
        atualizarSaldoLabel();
        topPanel.add(lblSaldo);
        
        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> logout());
        topPanel.add(btnLogout);
        
        add(topPanel, BorderLayout.NORTH);
        
        // Tabela
        String[] colunas = {"ID", "Tipo", "Operadora", "Valor", "Categoria", "Motivo", "Contacto", "Data/Hora"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        
        // Aplicar cores nas linhas
        table.setDefaultRenderer(Object.class, new CorLinhaRenderer());
        
        carregarTransacoes();
        
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);
        
        // Botões CRUD
        JPanel btnPanel = new JPanel();
        JButton btnAdd = new JButton("Nova Transação");
        JButton btnDelete = new JButton("Remover");
        btnAdd.addActionListener(e -> novaTransacao());
        btnDelete.addActionListener(e -> removerTransacao());
        btnPanel.add(btnAdd);
        btnPanel.add(btnDelete);
        add(btnPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    // Método público para atualizar saldo (será chamado pelo AddEditTransactionUI)
    public void atualizarSaldoLabel() {
        if (lblSaldo != null) {
            lblSaldo.setText("Saldo atual: " + String.format("%.2f", user.getSaldoAtual()) + " MZN");
            lblSaldo.setFont(new Font("Arial", Font.BOLD, 16));
        }
    }
    
    // Método público para carregar transações (será chamado pelo AddEditTransactionUI)
    public void carregarTransacoes() {
        tableModel.setRowCount(0);
        List<Transaction> list = transactionDAO.getTransactionsByUser(user.getId());
        for (Transaction t : list) {
            tableModel.addRow(new Object[]{
                t.getId(), 
                t.getTipo().toUpperCase(), 
                t.getOperadora(), 
                String.format("%.2f", t.getValor()),
                t.getCategoria(), 
                t.getMotivo(), 
                t.getContactoOutraParte(), 
                t.getDataHora()
            });
        }
    }
    
    // Classe interna para renderizar cores nas linhas
    private class CorLinhaRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if (!isSelected) {
                String operadora = (String) tableModel.getValueAt(row, 2);
                Color cor = getCorOperadora(operadora);
                c.setBackground(cor);
                c.setForeground(Color.BLACK);
            } else {
                c.setBackground(table.getSelectionBackground());
                c.setForeground(table.getSelectionForeground());
            }
            
            return c;
        }
        
        private Color getCorOperadora(String operadora) {
            if (operadora.equals("M-Pesa")) return new Color(255, 200, 200); // vermelho claro
            if (operadora.equals("eMola")) return new Color(255, 220, 180); // laranja claro
            if (operadora.equals("M-Kesh")) return new Color(255, 255, 180); // amarelo claro
            return Color.WHITE;
        }
    }
    
    private void novaTransacao() {
        new AddEditTransactionUI(user, this);
    }
    
    private void removerTransacao() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma transação");
            return;
        }
        int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
        String tipo = tableModel.getValueAt(row, 1).toString().toLowerCase();
        double valor = Double.parseDouble(tableModel.getValueAt(row, 3).toString());
        
        int confirm = JOptionPane.showConfirmDialog(this, "Remover esta transação?");
        if (confirm == JOptionPane.YES_OPTION) {
            transactionDAO.deleteTransaction(id);
            // Atualizar saldo do usuário
            double novoSaldo = user.getSaldoAtual();
            if (tipo.equals("receita")) novoSaldo -= valor;
            else novoSaldo += valor;
            user.setSaldoAtual(novoSaldo);
            userDAO.atualizarSaldo(user.getId(), novoSaldo);
            atualizarSaldoLabel();
            carregarTransacoes();
        }
    }
    
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, "Deseja sair?");
        if (confirm == JOptionPane.YES_OPTION) {
            new LoginUI();
            dispose();
        }
    }
}
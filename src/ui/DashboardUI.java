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

import model.User;
import model.Transaction;
import dao.TransactionDAO;
import dao.UserDAO;
import javax.swing.*;
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
    
    private void atualizarSaldoLabel() {
        lblSaldo.setText("Saldo atual: " + user.getSaldoAtual() + " MZN");
        lblSaldo.setFont(new Font("Arial", Font.BOLD, 16));
    }
    
    private void carregarTransacoes() {
        tableModel.setRowCount(0);
        List<Transaction> list = transactionDAO.getTransactionsByUser(user.getId());
        for (Transaction t : list) {
            Color cor = getCorOperadora(t.getOperadora());
            tableModel.addRow(new Object[]{
                t.getId(), t.getTipo(), t.getOperadora(), t.getValor(),
                t.getCategoria(), t.getMotivo(), t.getContactoOutraParte(), t.getDataHora()
            });
            // Aplicar cor na linha (sobrescrever prepareRenderer)
            table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if (!isSelected) {
                        String operadora = (String) tableModel.getValueAt(row, 2);
                        c.setBackground(getCorOperadora(operadora));
                    }
                    return c;
                }
            });
        }
    }
    
    private Color getCorOperadora(String operadora) {
        if (operadora.equals("M-Pesa")) return new Color(255, 200, 200); // vermelho claro
        if (operadora.equals("eMola")) return new Color(255, 220, 180); // laranja claro
        if (operadora.equals("M-Kesh")) return new Color(255, 255, 180); // amarelo claro
        return Color.WHITE;
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
        int id = (int) tableModel.getValueAt(row, 0);
        String tipo = (String) tableModel.getValueAt(row, 1);
        double valor = (double) tableModel.getValueAt(row, 3);
        
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

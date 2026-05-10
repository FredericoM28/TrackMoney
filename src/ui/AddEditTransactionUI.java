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
import utils.MznumberValidator;
import javax.swing.*;
import java.awt.*;

public class AddEditTransactionUI extends JDialog {
    private User user;
    private DashboardUI parent;
    private TransactionDAO transactionDAO = new TransactionDAO();
    private UserDAO userDAO = new UserDAO();
    
    private JComboBox<String> cmbTipo, cmbOperadora, cmbCategoria;
    private JTextField txtValor, txtContacto;
    private JTextArea txtMotivo;
    
    public AddEditTransactionUI(User user, DashboardUI parent) {
        super(parent, "Nova Transação", true);
        this.user = user;
        this.parent = parent;
        setSize(450, 400);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int y = 0;
        // Tipo
        gbc.gridx=0; gbc.gridy=y; add(new JLabel("Tipo:"), gbc);
        cmbTipo = new JComboBox<>(new String[]{"despesa", "receita"});
        gbc.gridx=1; add(cmbTipo, gbc);
        y++;
        
        // Operadora
        gbc.gridx=0; gbc.gridy=y; add(new JLabel("Operadora:"), gbc);
        cmbOperadora = new JComboBox<>(new String[]{"M-Pesa", "eMola", "M-Kesh"});
        gbc.gridx=1; add(cmbOperadora, gbc);
        y++;
        
        // Valor
        gbc.gridx=0; gbc.gridy=y; add(new JLabel("Valor (MZN):"), gbc);
        txtValor = new JTextField();
        gbc.gridx=1; add(txtValor, gbc);
        y++;
        
        // Categoria
        gbc.gridx=0; gbc.gridy=y; add(new JLabel("Categoria:"), gbc);
        cmbCategoria = new JComboBox<>(new String[]{"jackpot", "sms", "mb", "transferencia", "pagamento", "outro", "credito"});
        gbc.gridx=1; add(cmbCategoria, gbc);
        y++;
        
        // Contacto outra parte
        gbc.gridx=0; gbc.gridy=y; add(new JLabel("Contacto (9 dígitos):"), gbc);
        txtContacto = new JTextField();
        gbc.gridx=1; add(txtContacto, gbc);
        y++;
        
        // Motivo
        gbc.gridx=0; gbc.gridy=y; add(new JLabel("Motivo:"), gbc);
        txtMotivo = new JTextArea(3,15);
        JScrollPane sp = new JScrollPane(txtMotivo);
        gbc.gridx=1; add(sp, gbc);
        y++;
        
        // Botão salvar
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarTransacao());
        gbc.gridx=0; gbc.gridy=y; gbc.gridwidth=2;
        add(btnSalvar, gbc);
        
        setVisible(true);
    }
    
    private void salvarTransacao() {
        try {
            String tipo = (String) cmbTipo.getSelectedItem();
            String operadora = (String) cmbOperadora.getSelectedItem();
            double valor = Double.parseDouble(txtValor.getText().trim());
            String categoria = (String) cmbCategoria.getSelectedItem();
            String motivo = txtMotivo.getText().trim();
            String contacto = txtContacto.getText().trim();
            
            if (contacto.isEmpty()) contacto = null;
            else if (!MznumberValidator.isValidMozNumber(contacto)) {
                JOptionPane.showMessageDialog(this, "Número moçambicano inválido (9 dígitos, 82/83/84/85/86/87)");
                return;
            }
            
            // Validar saldo para despesa
            if (tipo.equals("despesa") && valor > user.getSaldoAtual()) {
                JOptionPane.showMessageDialog(this, "Saldo insuficiente");
                return;
            }
            
            Transaction t = new Transaction();
            t.setUserId(user.getId());
            t.setTipo(tipo);
            t.setOperadora(operadora);
            t.setValor(valor);
            t.setCategoria(categoria);
            t.setMotivo(motivo);
            t.setContactoOutraParte(contacto);
            
            transactionDAO.addTransaction(t);
            
            // Atualizar saldo do usuário
            double novoSaldo = user.getSaldoAtual();
            if (tipo.equals("receita")) novoSaldo += valor;
            else novoSaldo -= valor;
            user.setSaldoAtual(novoSaldo);
            userDAO.atualizarSaldo(user.getId(), novoSaldo);
            
            parent.atualizarSaldoLabel();
            parent.carregarTransacoes();
            JOptionPane.showMessageDialog(this, "Transação salva!");
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valor inválido");
        }
    }
}
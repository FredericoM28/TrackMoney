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

import dao.UserDAO;
import model.User;
import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame {
    private JTextField txtNumero;
    private JPasswordField txtPin;
    private UserDAO userDAO = new UserDAO();
    
    public LoginUI() {
        setTitle("TrackMoney - Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        
        // Título
        JLabel lblTitulo = new JLabel("TrackMoney");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(lblTitulo, gbc);
        
        // Número
        gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0;
        add(new JLabel("Número MZN:"), gbc);
        txtNumero = new JTextField(15);
        gbc.gridx = 1;
        add(txtNumero, gbc);
        
        // PIN
        gbc.gridy = 2; gbc.gridx = 0;
        add(new JLabel("PIN:"), gbc);
        txtPin = new JPasswordField(15);
        gbc.gridx = 1;
        add(txtPin, gbc);
        
        // Botão
        JButton btnLogin = new JButton("Entrar");
        btnLogin.addActionListener(e -> fazerLogin());
        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2;
        add(btnLogin, gbc);
        
        setVisible(true);
    }
    
    private void fazerLogin() {
        String numero = txtNumero.getText().trim();
        String pin = new String(txtPin.getPassword());
        User user = userDAO.login(numero, pin);
        if (user != null) {
            JOptionPane.showMessageDialog(this, "Bem-vindo " + user.getNome());
            new DashboardUI(user);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Número ou PIN incorretos");
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginUI::new);
    }
}

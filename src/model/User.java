/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Pedro
 */
//package model;

public class User {
    private int id;
    private String nome;
    private String email;
    private String numeroMocambicano;
    private String pin;
    private double saldoAtual;

    // Construtores, getters e setters
    public User() {}

    public User(int id, String nome, String email, String numeroMocambicano, String pin, double saldoAtual) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.numeroMocambicano = numeroMocambicano;
        this.pin = pin;
        this.saldoAtual = saldoAtual;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getNumeroMocambicano() { return numeroMocambicano; }
    public void setNumeroMocambicano(String numeroMocambicano) { this.numeroMocambicano = numeroMocambicano; }
    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }
    public double getSaldoAtual() { return saldoAtual; }
    public void setSaldoAtual(double saldoAtual) { this.saldoAtual = saldoAtual; }
}

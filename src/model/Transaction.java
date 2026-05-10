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

import java.sql.Timestamp;

public class Transaction {
    private int id;
    private int userId;
    private String tipo; // "receita" ou "despesa"
    private String operadora;
    private double valor;
    private String categoria;
    private String motivo;
    private String contactoOutraParte;
    private Timestamp dataHora;

    // Construtores, getters e setters
    public Transaction() {}

    public Transaction(int id, int userId, String tipo, String operadora, double valor, String categoria, String motivo, String contactoOutraParte, Timestamp dataHora) {
        this.id = id;
        this.userId = userId;
        this.tipo = tipo;
        this.operadora = operadora;
        this.valor = valor;
        this.categoria = categoria;
        this.motivo = motivo;
        this.contactoOutraParte = contactoOutraParte;
        this.dataHora = dataHora;
    }

    // Getters e setters omitidos por brevidade (gerar no IDE)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getOperadora() { return operadora; }
    public void setOperadora(String operadora) { this.operadora = operadora; }
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getContactoOutraParte() { return contactoOutraParte; }
    public void setContactoOutraParte(String contactoOutraParte) { this.contactoOutraParte = contactoOutraParte; }
    public Timestamp getDataHora() { return dataHora; }
    public void setDataHora(Timestamp dataHora) { this.dataHora = dataHora; }
}
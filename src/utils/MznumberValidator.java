/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author Pedro
 */
//package utils;

public class MznumberValidator {
    public static boolean isValidMozNumber(String number) {
        if (number == null || number.length() != 9) return false;
        if (!number.matches("\\d+")) return false;
        String prefix = number.substring(0, 2);
        return prefix.equals("82") || prefix.equals("83") || prefix.equals("84") || prefix.equals("85") || prefix.equals("86") || prefix.equals("87");
    }
    
    public static String getOperadora(String number) {
        String prefix = number.substring(0, 2);
        if (prefix.equals("84") || prefix.equals("85")) return "M-Pesa";
        if (prefix.equals("86") || prefix.equals("87")) return "eMola";
        if (prefix.equals("82") || prefix.equals("83")) return "M-Kesh";
        return "Desconhecida";
    }
}

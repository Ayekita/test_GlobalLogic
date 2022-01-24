package com.jcatalan.proyecto.cl.demo.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validations {
	
	public static boolean validateEmail(String email) {
		String regx = "^[A-Za-z0-9._-]+@[A-Za-z0-9_-]+.[A-Za-z]{2,3}$";
        //Compile regular expression to get the pattern  
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(email);	
        return matcher.matches();
	}
	
	public static boolean validatePassword(String password) {
		//1 mayuscula, 2 numero maximo
	   boolean isCorrect = true;
       char clave;
       byte  contNumero = 0, contLetraMay = 0;
       for (byte i = 0; i < password.length(); i++) {
                clave = password.charAt(i);
               String passValue = String.valueOf(clave);
                if (passValue.matches("[A-Z]")) {
                    contLetraMay++;
                } else if (passValue.matches("[0-9]")) {
                    contNumero++;
                }
        }
       if (password.length()<8) {
    	   isCorrect = false;
       }else {
    	   if (password.length()>12) {
    		   isCorrect = false;
    	   }
       }
       if (contLetraMay != 1) {
    	   isCorrect = false;
       }
       if (contNumero == 0 || contNumero > 2) {
    	   isCorrect = false;
       }
        
       return isCorrect;
	}

}

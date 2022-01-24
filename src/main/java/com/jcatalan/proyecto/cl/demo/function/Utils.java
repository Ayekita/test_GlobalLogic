package com.jcatalan.proyecto.cl.demo.function;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

public class Utils {
	private static String ISSUER = "BCI";
	private static String AUDIENCE = "CSDN";
	private static String KEY = "Shenpibaipao";
	
	public static String getToken(String userId, String email) {
	    Algorithm alg = Algorithm.HMAC256(KEY);
	   
	    Date currentTime = new Date();
	    String token = JWT.create()
	            .withIssuer(ISSUER) // Emisor
	            .withSubject(userId) // ID de usuario
	            .withAudience(AUDIENCE) // Unidades de usuario
	            .withIssuedAt(currentTime) // hora de emisión
	            .withExpiresAt(new Date(currentTime.getTime() + 24*3600*1000L)) // Validez de un día
	            .withJWTId("001") // Asignar ID de JWT
	            .withClaim("demo", "1")
	            .sign(alg);
	    
	    return token;
	}
	
	public static Boolean verifyToken(String token) {
		Algorithm alg = Algorithm.HMAC256(KEY);

		JWTVerifier verifier = JWT.require(alg)
	            .withIssuer(ISSUER)
	            .withAudience(AUDIENCE)
	            .build();
		try{
		    verifier.verify(token);
		    System.out.println("¡La verificación pasó!");
		    return true;
		} catch (JWTVerificationException e) {
		    e.printStackTrace();
		    System.out.println("¡Fallo en la verificación!");
		}
		return false;
	}
	
	public static String validateToken(String token) {
		String value = null;
	    try{
	    	if (verifyToken(token)) {
		        DecodedJWT originToken = JWT.decode(token);
		        String issuer = originToken.getIssuer();
		        String userId = originToken.getSubject();
		        if (issuer.equals(ISSUER)){
		        	value = userId;
		        }	
	    	}
	    } catch (JWTDecodeException e){
	        e.printStackTrace();
	    }
	    
		return value;
	}
	
	public static boolean validateEmail(String email) {
		String regx = "^[A-Za-z0-9._-]+@[A-Za-z0-9_-]+[.][A-Za-z]{2,3}$";
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

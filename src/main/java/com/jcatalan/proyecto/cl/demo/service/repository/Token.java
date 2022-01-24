package com.jcatalan.proyecto.cl.demo.service.repository;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class Token {
	
	public static String getToken(String userId, String email) {
		String key = "BCI";
	    // Dado un algoritmo, como HmacSHA-256
	    Algorithm alg = Algorithm.HMAC256(key);

	    // 1 token de emisión
	    Date currentTime = new Date();
	    String token = JWT.create()
	            .withIssuer("bci") // Emisor
	            .withSubject(userId) // ID de usuario
	            .withAudience(email) // Unidades de usuario
	            .withIssuedAt(currentTime) // hora de emisión
	            .withExpiresAt(new Date(currentTime.getTime() + 24*3600*1000L)) // Validez de un día
	            .withJWTId("001") // Asignar ID de JWT
	            .sign(alg);

	    System.out.println("El token generado es:"+token);
	    return token;
	}

}

package com.exam.quiz.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    private static final SecretKey SIGNING_KEY = JwtSecretKey.generateKey();

    public String generateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims,username);
    }

    private String createToken(Map<String, Object> claims, String username) {
        long nowMillis = System.currentTimeMillis();

        return Jwts.builder()
                .claims(claims)
                .subject(username) // Set subject (username)
                .issuedAt(new Date(nowMillis)) // Issue date
                .expiration(new Date(nowMillis + 3600000)) // Expiration time (1 hour)
                .signWith(SIGNING_KEY) // Sign with the key
                .compact();
    }

    private Claims extractClaims(String token){
        return Jwts.parser()
                .verifyWith(SIGNING_KEY)  // Verify the JWT signature with the signing key
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public String extractUsername(String token){
        return extractClaims(token).getSubject();
    }
    private Date extractExpiration(String token){
        return extractClaims(token).getExpiration();
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
   public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
   }
}

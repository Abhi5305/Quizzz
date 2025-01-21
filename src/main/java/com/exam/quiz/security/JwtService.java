package com.exam.quiz.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    private final String secretKey= "4dbb22554b27878f68ee25fa09ef7d821a7579be4fad8dbc200b9d622e8698c9d6142ac96bb6b7c5aa2b4f6e9e2ade1dc1df5e57d45eedc448d4bb12ad6587965eedd26df52009681fccf27c2a95a36a18b488f4715ae499a6e080095736e576fce2ec0b79399c558282a3b4adec5ede6c8fde203d054bda25beaa4ed681b93d7705a534bacd4f4b182f36601ed4720834df44fc6dbb2972c72a8fb318e3681fa1d319a3fc1ab570b77c3faa9833ae2e9cf0d5f4dcb31fcac3c8c13af5d698ba5d2c1286003722b3aa72e1f2def74c13865ec6851b84661485e9d8215d5cd33c49558d72e9ee37c9b98e829a71cd047d80859cb51581885146d1155d4ca0de09";

    private final SecretKey SIGNING_KEY = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));


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

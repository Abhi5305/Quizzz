package com.exam.quiz.security;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class JwtSecretKey {

    public static SecretKey generateKey() {
        // Generate a key from a byte array (32 bytes for HS256)
        byte[] keyBytes = new byte[32];
        new java.security.SecureRandom().nextBytes(keyBytes);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

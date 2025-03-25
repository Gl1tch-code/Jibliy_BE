package com.gl1tch.Jibliy.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class JwtTokenService {

    @Value("${jwt.secret}")
    private String secretKey;

    public String generateToken(String username) throws NoSuchAlgorithmException {
        return Jwts.builder()
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            // Updated for version 0.12.5
            Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Get username from token
    public String extractUsername(String token) {
        // Updated for version 0.12.5
        Claims claims = Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}

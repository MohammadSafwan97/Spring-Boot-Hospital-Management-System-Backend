package com.safwantech.hms_backend.security;


import com.safwantech.hms_backend.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class AuthUtil {

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    // Create secret key from string
    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    // Generate JWT token
    public String generateAccessToken(User user){

        Date now = new Date();

        // Token expiry time (1 hour)
        Date expiryDate = new Date(now.getTime() + 1000 * 60 * 60);

        return Jwts.builder()
                .subject(user.getUsername())           // who is the user
                .claim("userId", user.getId()) // extra data
                .issuedAt(now)                         // token creation time
                .expiration(expiryDate)                // token expiry
                .signWith(getSecretKey())              // digital signature
                .compact();                            // convert to string token
    }

    public String getUsernameFromToken(String token) {
        Claims claims=Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }
}
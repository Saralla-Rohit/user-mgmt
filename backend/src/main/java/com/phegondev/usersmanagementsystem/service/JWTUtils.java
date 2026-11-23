
package com.phegondev.usersmanagementsystem.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JWTUtils {

	 /**
     * Symmetric signing/verification key.
     * The same key signs new tokens and verifies incoming ones,
     * which means rotating this key invalidates all issued tokens.
     */
    private SecretKey key;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;
    
    
//    @Value("${jwt.secret}")
//    private String secretString;

    public JWTUtils(@Value("${jwt.secret}") String secretString) {
        byte[] keyBytes = Base64.getDecoder().decode(secretString);//decoding secretkey
        this.key = new SecretKeySpec(keyBytes, "HmacSHA256");//wrap key & algo
    }

    // Generate Access Token
    public String generateToken(UserDetails userDetails) {

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(key)
                .compact();
    }

    // Generate Refresh Token
    public String generateRefreshToken(
            HashMap<String, Object> claims,
            UserDetails userDetails) {

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(
            String token,
            Function<Claims, T> claimsTFunction) {

        return claimsTFunction.apply(
                Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload() //returns Claims instance
        );
    }
    
    
    public boolean isTokenValid(String token, UserDetails userDetails) {

        final String username = extractUsername(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {

        return extractClaims(token, Claims::getExpiration)
                .before(new Date());
    }
}

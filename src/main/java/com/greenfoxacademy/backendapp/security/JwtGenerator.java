package com.greenfoxacademy.backendapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtGenerator {

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        ZonedDateTime currentDate = ZonedDateTime.now();
        ZonedDateTime expireDate = currentDate.plusSeconds(600); // 10 minutes expiration compared to 24h in previous version which is commented out now

//        Date currentDate = new Date();
//        Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);

        List<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .claim("roles", roles)
                .subject(username)
                .issuedAt(Date.from(currentDate.toInstant()))
                .expiration(Date.from(expireDate.toInstant()))
                .signWith(getSigningKey())
                .compact();
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build().parseSignedClaims(token)
                .getPayload();
        System.out.println("this is run as second from JwtAuthFilter everytime when endpoint is requested and verification is needed");

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        System.out.println("this is run first from JwtAuthFilter everytime when endpoint is requested and verification is needed");
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = SecurityConstants.JWT_SECRET.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}


// TOTO MAM UROBIT JA CELE

//service to check user credentials and generate token
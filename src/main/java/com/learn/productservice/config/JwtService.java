package com.learn.productservice.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtService {
     public static final String SECRET_KEY = "RqxPOuVfHoBA8Uq40MhJvfY6qEHOOWWvg6N9W9vt23s=";
    private final Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    private final long expiration = 86400000; // 1 day

    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    public String getEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public String getRole(String token) {
        return (String) Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().get("role");
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
// import java.security.Key;
// import java.util.Date;
// import java.util.function.Function;

// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.stereotype.Service;

// import io.jsonwebtoken.*;
// import io.jsonwebtoken.io.Decoders;
// import io.jsonwebtoken.security.Keys;

// @Service
// public class JwtService {
    
//     private static final String SECRET_KEY = "your-very-long-secret-key-with-at-least-32-characters";

    
    
//     public String generateToken(UserDetails userDetails) {
//         return Jwts.builder()
//                 .setSubject(userDetails.getUsername())
//                 .setIssuedAt(new Date(System.currentTimeMillis()))
//                 .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
//                 .signWith(getSignKey(), SignatureAlgorithm.HS256)
//                 .compact();
//     }

//     public String extractUsername(String token) {
//         return extractClaim(token, Claims::getSubject);
//     }

//     public boolean isTokenValid(String token, UserDetails userDetails) {
//         return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
//     }

//     private boolean isTokenExpired(String token) {
//         return extractClaim(token, Claims::getExpiration).before(new Date());
//     }

//     private Claims extractAllClaims(String token) {
//         return Jwts.parserBuilder()
//                 .setSigningKey(getSignKey())
//                 .build()
//                 .parseClaimsJws(token)
//                 .getBody();
//     }

//     private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//         final Claims claims = extractAllClaims(token);
//         return claimsResolver.apply(claims);
//     }

//     private Key getSignKey() {
//         byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
//         return Keys.hmacShaKeyFor(keyBytes);
//     }
// }

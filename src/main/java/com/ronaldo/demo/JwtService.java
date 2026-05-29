package com.ronaldo.demo;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    //Must be >_32 bytes when base64 decoded
    private final String SECRET_KEY = "vG95b3ViZXV0aWZ1bHN0cmluZ3RoYXRpc2V4YWN0bHlzaXh0eWZvdXJjaGFycw";

    // -------------------------------
    // LOWEST LEVEL: cryptographic key
    // -------------------------------
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // -----------------------------------------
    // NEXT LEVEL: parsing the token (raw claims)
    // -----------------------------------------
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build().parseClaimsJws(token).getBody();
    }

    // ---------------------------------------------------
    // GENERIC CLAIM EXTRACTOR (used by all extract methods)
    // ---------------------------------------------------
    public <T> T extractClaim(String token, Function<Claims,T> resolver) {
        final Claims claims = extractAllClaims(token); //parse the entire token and get all claim
        return resolver.apply(claims); //pick specific claim you wanna extract
    }

    // -----------------------------------------
    // SPECIFIC CLAIMS (username, role, expiry)
    // -----------------------------------------
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject); //subject = email. just gives you token and its subject
    }

    public String extractRole(String token) {
        //getting role from token, via string class
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    private Date extractExpiration(String token) {
        //extract exp claim from token
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // -----------------------------------------
    // VALIDATION (uses the extract methods above)
    // -----------------------------------------
    public boolean isTokenValid(String token, User user) {
        final String username = extractUsername(token); //getting username from token
        //valid if username given matches user, and if not expired
        return username.equals(user.getEmail()) && !isTokenExpired(token);
    }

    // -----------------------------------------
    // TOKEN CREATION (higher-level operations)
    // -----------------------------------------
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims) //setting custom claims(role)
                .setSubject(subject) //setting subject(username/email)
                .setIssuedAt(new Date(System.currentTimeMillis())) //tjhe date when the token was created
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24)) //24h from now - 1s*60s*60min*24h
                .signWith(getSignKey(), SignatureAlgorithm.HS256) //sign token using hs256 and secret key
                .compact();
    }

    // -----------------------------------------
    // HIGHEST LEVEL: public API to generate token
    // -----------------------------------------
    public String generateToken(User user) {
        //map for the custom map to store custom class
        Map<String,Object> claims = new HashMap<>();
        //add user role to the toklen
        claims.put("role",user.getRole());
        return createToken(claims, user.getEmail()); //passing in the custom claim, and email as the subjhect
    }
}

package com.practice.jwt.security;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationDay;

    //Generate Json Web Token
    public String generateToken( Authentication authentication ) {

        String username = authentication.getName();

        Date currentDate = new Date();

        Date expireDate = new Date( currentDate.getTime() + jwtExpirationDay );

        String token = Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expireDate)
                .signWith(key())
                .compact();

        return token;
    }

    private Key key() {
        return Keys.hmacShaKeyFor( Decoders.BASE64.decode(jwtSecret) );
    }

    // get username from jwt
    public String getUsername( String token ) {

        return Jwts.parser()
                .verifyWith( (SecretKey) key() )
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // validate jwt
    public boolean validateToken( String token ) {
        Jwts.parser()
                .verifyWith( (SecretKey) key() )
                .build()
                .parse(token);
        return true;
    }

}
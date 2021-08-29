package br.com.conexasaude.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String email) {

        return Jwts.builder().setSubject(email).setExpiration(new Date(System.currentTimeMillis()
                + expiration)).signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
    }

    public boolean isValidToken(String token) {

        Claims claims = getClaims(token);

        if (claims != null) {
            String email = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());

            return email != null && expirationDate != null && now.before(expirationDate);
        }

        return false;
    }

    private Claims getClaims(String token) {

        try {
            return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public String getEmail(String token) {

        Claims claims = getClaims(token);

        if (claims != null)
            return claims.getSubject();

        return null;
    }
}

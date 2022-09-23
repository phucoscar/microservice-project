package com.dtsgroup.userservice.util;

import com.dtsgroup.userservice.exception.BusinessException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JWTUtil {
    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hours

    @Value("${app.secret.key}")
    private String secret_key;

    // code to genarate token
    public String genarateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, secret_key)
                .compact();
    }

    // code to get claims
    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret_key)
                .parseClaimsJws(token)
                .getBody();
    }

    // code to check if token is valid
    public boolean isValidToken(String token) {
        return getClaims(token).getExpiration()
                .after(new Date(System.currentTimeMillis()));
    }

    // code to check if token is valid as per usename
    public boolean isValidToken(String token, String username) {
        String tokenUsername = getSubject(token);
        return (username.equals(tokenUsername) && !isTokenExpired(token));
    }

    // code to check if token is expired
    public boolean isTokenExpired(String token) {
        return getExpirationDate(token)
                .before(new Date(System.currentTimeMillis()));
    }

    // code to get expiration date
    public Date getExpirationDate(String token) {
        return getClaims(token).getExpiration();
    }

    // code to get Subject
    public String getSubject(String token) {
        return getClaims(token).getSubject();
    }

    public boolean validateJwtToken(String token) throws BusinessException {
        try {
            Jws<Claims> claimsJwts = Jwts.parser().setSigningKey(secret_key).parseClaimsJws(token);
            return !claimsJwts.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            throw new BusinessException("Token is expired", HttpStatus.UNAUTHORIZED);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        } catch (MalformedJwtException e) {
            System.out.println(e);
        } catch (UnsupportedJwtException e) {
            System.out.println(e);
        } catch (SignatureException e) {
            System.out.println(e);
        }
        return false;
    }
}

package com.eticket.service.interfaces.implementation;

import com.eticket.service.interfaces.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${token.signing.key}")
    private String jwtSigningKey;

    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
//        try {
//            String encodedUsername = URLEncoder.encode(userDetails.getUsername(), "UTF-8");
            return Jwts.builder()
                    .setClaims(extraClaims)
                    .setSubject(userDetails.getUsername())
                    .claim("authorities", getAuthorities(userDetails))
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                    .signWith(SignatureAlgorithm.HS256, getSigningKey())
                    .compact();
//        }catch (UnsupportedEncodingException e){
//            throw new RuntimeException(e.getMessage());
//        }
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = java.util.Base64.getDecoder().decode(jwtSigningKey);
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    private List<String> getAuthorities(UserDetails userDetails) {
        List<String> authorities = new ArrayList<>();
        userDetails.getAuthorities().forEach(authority -> authorities.add(authority.getAuthority()));
        return authorities;
    }


}

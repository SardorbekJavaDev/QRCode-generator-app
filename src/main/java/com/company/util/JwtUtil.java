package com.company.util;

import com.company.dto.UserJWTDTO;
import com.company.exp.AppBadRequestException;
import io.jsonwebtoken.*;

import java.util.Date;

public class JwtUtil {

    private final static String secretKey = "kalitso'z";

    public static String encode(String email) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setSubject(email);
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS256, secretKey);
//        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (60 * 60 * 1000 * 24)));
        jwtBuilder.setIssuer("mazgi production");

        return jwtBuilder.compact();
    }

    public static UserJWTDTO decode(String jwt) {
        try {
            JwtParser jwtParser = Jwts.parser();

            jwtParser.setSigningKey(secretKey);
            Jws jws = jwtParser.parseClaimsJws(jwt);

            Claims claims = (Claims) jws.getBody();

            String email = claims.getSubject();

            return new UserJWTDTO(email);

        } catch (JwtException e) {
            throw new AppBadRequestException("JWT invalid!");
        }
    }

}

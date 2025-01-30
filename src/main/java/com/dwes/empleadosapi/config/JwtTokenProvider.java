package com.dwes.empleadosapi.config;

import ch.qos.logback.core.util.StringUtil;
import com.dwes.empleadosapi.entities.UserEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private static final String SECRET_KEY = "SuperClaveSecretaParaElJwtCon32CaracteresMin";
    private static final long EXPIRATION_TIME = 86400000; // 1 día


    public String generateToken(Authentication authentication) {

        UserEntity user = (UserEntity) authentication.getPrincipal();
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        return Jwts.builder()
                .subject(Long.toString(user.getId())) // Reemplaza setSubject
                .claim("email", user.getEmail())
                .claim("username", user.getUsername())
                .issuedAt(new Date()) // Reemplaza setIssuedAt
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Reemplaza setExpiration
                .signWith(key) // Reemplaza signWith con Key y SignatureAlgorithm
                .compact();
    }

    //Validar firma del token
    public boolean isValidToken(String token) {
        if(StringUtils.isBlank(token)){
            return false;
        }

        try {
            JwtParser validator = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                    .build();
            validator.parseClaimsJws(token);
            return true;
        }catch (Exception e){
            //Aquí entraría si el token no fuera correcto o estuviera caducado.
            // Podríamos hacer un log de los fallos
            System.err.println("Error al validar el token: " + e.getMessage());
            return false;
        }

    }
}

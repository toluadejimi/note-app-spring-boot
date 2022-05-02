package com.example.demo.utils;

import java.security.Key;
import java.util.Base64;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.io.JsonEOFException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;

@Aspect
@Component
public class AutheticateAspect {
    
    @Autowired
    HttpServletRequest request;

    @Around("@annotation(com.example.demo.interfaces.Autheticate)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        String token = request.getHeader("Authorization");

        // take secret from applicatiom.yml from resources
        String secret = "bhagyesh";
        // add this logic to utils with singleton design pattern
        Key key = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());

        try {
            Jws<Claims> jwt = Jwts.parser()
            .setSigningKey(key)
            .parseClaimsJws(token);
 
            request.setAttribute("email", jwt.getBody().get("email"));

            Object proceed = joinPoint.proceed();

            return proceed;
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("expired");
        } catch (JsonEOFException | MalformedJwtException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("bad token");
        }
    }
}
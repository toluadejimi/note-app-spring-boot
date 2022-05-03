package com.example.demo.utils;

import java.security.Key;
import java.util.Base64;
import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import com.example.demo.entity.User;
import java.util.Map;

@Component
public class Utils {
  
  @Value("${spring.jwt.secret}")
  String secret;
  
  @Value("${spring.jwt.exp_time}")
  Integer expTime;

  private Key key;
  
  @PostConstruct
  public void init() {
    key = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());
  }
  
  public String generateToken(User user) {
    Instant now = Instant.now();
    return Jwts.builder()
      .claim("user", user)
      .setIssuedAt(Date.from(now))
      .setExpiration(Date.from(now.plus(expTime, ChronoUnit.SECONDS)))
      .signWith(SignatureAlgorithm.HS256, key)
      .compact();
  }
  
  public Map<String, Object> validateToken(String token) throws Throwable {
    Jws<Claims> jwt = Jwts.parser()
      .setSigningKey(key)
      .parseClaimsJws(token);
    return (Map<String, Object>)jwt.getBody().get("user");
  }
}

package ru.edu.FirstSecurityApp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.time.ZonedDateTime;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {

  @Value("{jwt_secret}")
  private String secret;

  public String generateToken(String username) {
    // строк токена - 60 минут
    Date expiration = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());

    // JWT из библиотеки java-jwt
    return JWT.create()
        .withSubject("User details")
        // claim - пара ключ-значение (может быть много) в payload токена
        .withClaim("username", username)
        .withIssuedAt(new Date()) // когда выдан
        .withIssuer("FirstSecurityApp") // кто выдал токен
        .withExpiresAt(expiration) // срок жизни до
        // подписываем токен с помощью серкрета
        .sign(Algorithm.HMAC256(secret));
  }

  public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
    JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)) // проверяем подпись
        .withSubject("User details")  // проверяем subject
        .withIssuer("FirstSecurityApp") // проверяем, кем выдан
        .build();

    // при проверке токена может кинуть JWTVerificationException
    DecodedJWT jwt = verifier.verify(token);

    return jwt.getClaim("username").asString();
  }

}

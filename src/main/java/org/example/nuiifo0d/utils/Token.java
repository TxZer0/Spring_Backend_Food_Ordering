package org.example.nuiifo0d.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.nuiifo0d.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Component
public class Token {

    private final String secret;

    private static final long ACCESS_TTL = 86400000;
    private static final long REFRESH_TTL = 86400000 * 7;

    @Autowired
    public Token(Environment env) {
        this.secret = Binder.get(env)
                .bind("token.secret", String.class)
                .orElseThrow(() -> new RuntimeException("Something went wrong"));
    }

    private String createToken(User user, long timeToLive) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());

        Key key = new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS512.getJcaName());
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + timeToLive))
                .addClaims(claims)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public HashMap<String, String> createTokenPair(User user) {
        HashMap<String, String> tokenPair = new HashMap<>();
        String accessToken = createToken(user, ACCESS_TTL);
        String refreshToken = createToken(user, REFRESH_TTL);
        tokenPair.put("access_token", accessToken);
        tokenPair.put("refresh_token", refreshToken);
        return tokenPair;
    }
}
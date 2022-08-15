package com.banco.conta_segura.config.security;

import com.banco.conta_segura.models.UsuarioModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;

import java.util.Date;

import static io.jsonwebtoken.Jwts.*;

@Service
public class TokenService {

    @Value("${banco.seguro.jwt.expiration}")
    private String expiration;

    @Value("${banco.seguro.jwt.secret}")
    private String secretKey;

    public String gerarToken(Authentication authentication) {
        UsuarioModel usuarioLogado = (UsuarioModel) authentication.getPrincipal();
        Date hoje = new Date();
        Date dataDeExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
        return builder()
                .setIssuer("API banco seguro")
                .setSubject(usuarioLogado.getId().toString())
                .setIssuedAt(hoje)
                .setExpiration(dataDeExpiracao)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean isTokenValido(String token) {
        try {
            parser().setSigningKey(this.secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getIdUsuario(String token) {
        Claims claims = parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }
}

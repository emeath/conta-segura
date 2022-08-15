package com.banco.conta_segura.config.security;

import com.banco.conta_segura.models.UsuarioModel;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;

import java.util.Date;

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
        return Jwts.builder()
                .setIssuer("API banco seguro")
                .setSubject(usuarioLogado.getId().toString())
                .setIssuedAt(hoje)
                .setExpiration(dataDeExpiracao)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

}

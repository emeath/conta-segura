package com.banco.conta_segura.config.security;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = recuperarToken(request);

        System.out.println(token);

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String formatoDoModoDeAutenticacao = "Bearer ";
        // Validacoes
        if (token == null || token.isEmpty() || !(token.startsWith(formatoDoModoDeAutenticacao))) {
            return null;
        }

        return token.substring(formatoDoModoDeAutenticacao.length(), token.length());
    }
}

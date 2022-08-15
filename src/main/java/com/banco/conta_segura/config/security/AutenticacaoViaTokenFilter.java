package com.banco.conta_segura.config.security;

import com.banco.conta_segura.models.UsuarioModel;
import com.banco.conta_segura.repositories.UsuarioRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {


    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    public AutenticacaoViaTokenFilter(TokenService tokenService,
                                      UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = recuperarToken(request);

        boolean tokenValido = tokenService.isTokenValido(token);

        if (tokenValido){
            try {
                autenticarCliente(token);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        filterChain.doFilter(request, response);
    }

    private void autenticarCliente(String token) throws Exception {
        Long idUsuario = tokenService.getIdUsuario(token);
        Optional<UsuarioModel> usuarioModelOptional = usuarioRepository.findById(idUsuario);
        if (usuarioModelOptional.isPresent())
        {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuarioModelOptional.get(), null, usuarioModelOptional.get().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            throw new Exception("User not found. Not possible to authenticate!");
        }

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

package com.banco.conta_segura.config.security;

import com.banco.conta_segura.models.UsuarioModel;
import com.banco.conta_segura.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public AutenticacaoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UsuarioModel> usuarioModelOptional = usuarioRepository.findByEmail(username);
        if (usuarioModelOptional.isPresent()) {
            return usuarioModelOptional.get();
        }
        throw new UsernameNotFoundException("Dados incorretos!");
    }
}

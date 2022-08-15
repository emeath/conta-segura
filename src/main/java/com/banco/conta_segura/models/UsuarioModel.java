package com.banco.conta_segura.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="usuarios")
public class UsuarioModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<PerfilModel> perfis = new ArrayList<>(); //lista de permissoes do usuario

    public UsuarioModel() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<PerfilModel> getPerfis() {
        return perfis;
    }

    public void setPerfis(List<PerfilModel> perfis) {
        this.perfis = perfis;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return perfis;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Nao vamos controlar isso
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Nao vamos controlar isso
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Nao vamos controlar isso
    }

    @Override
    public boolean isEnabled() {
        return true; // Nao vamos controlar isso
    }

    public void setPassword(String password) {
        this.password = password;
    }


}

package com.banco.conta_segura.dtos;

public class TokenDTO {

    private String token;
    private String tipoAuth;

    public TokenDTO(String token, String tipoAuth) {
        this.token = token;
        this.tipoAuth = tipoAuth;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipoAuth() {
        return tipoAuth;
    }

    public void setTipoAuth(String tipoAuth) {
        this.tipoAuth = tipoAuth;
    }
}

package com.banco.conta_segura.dtos;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CPF;

public class ClienteDTO {

    @NotBlank
    private String nome;
    @NotBlank
    private String profissao;
    @NotBlank
    private String endereco;
    @CPF
    private String cpf;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
    
}

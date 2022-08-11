package com.banco.conta_segura.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="clientes")
public class ClienteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String profissao;
    @Column(nullable = false)
    private String endereco;
    @OneToMany(mappedBy = "cliente")
    private List<ContaModel> contas = new ArrayList<>();
}

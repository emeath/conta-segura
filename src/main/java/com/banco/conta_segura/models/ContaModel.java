package com.banco.conta_segura.models;

import com.sun.tools.javac.jvm.Gen;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "contas")
public class ContaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String agencia;
    @Column(nullable = false)
    private String conta;
    @Column(nullable = false)
    private BigDecimal saldo;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteModel cliente;
}

package com.bb.cadastro_automatico.cadastro.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "agencia")
public class Agencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_agencia")
    private String numeroAgencia;

    @Column(name = "numero_conta")
    private String numeroConta;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
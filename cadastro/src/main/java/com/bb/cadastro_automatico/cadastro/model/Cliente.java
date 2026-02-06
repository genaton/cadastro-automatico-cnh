package com.bb.cadastro_automatico.cadastro.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_completo")
    private String nomeCompleto;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "local_nascimento")
    private String localNascimento;

    @Column(name = "uf_nascimento")
    private String ufNascimento;

    @Column(name = "rg_numero")
    private String rgNumero;

    @Column(name = "rg_orgao_emissor")
    private String rgOrgaoEmissor;

    @Column(name = "rg_uf")
    private String rgUf;

    private String cpf;

    @Column(name = "numero_registro")
    private String numeroRegistro;

    @Column(name = "data_emissao")
    private LocalDate dataEmissao;

    private String nacionalidade;

    private String filiacao;

    @Lob
    @Column(name = "contrato_pdf")
    private byte[] contratoPdf;
}

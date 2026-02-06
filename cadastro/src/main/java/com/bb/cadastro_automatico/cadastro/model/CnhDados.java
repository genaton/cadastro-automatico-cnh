package com.bb.cadastro_automatico.cadastro.model;

public class CnhDados {

    private String nome;
    private String dataNascimento;
    private String cpf;
    private String registro;
    private String categoria;
    private String dataEmissao;
    private String validade;

    // getters e setters

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getRegistro() { return registro; }
    public void setRegistro(String registro) { this.registro = registro; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getDataEmissao() { return dataEmissao; }
    public void setDataEmissao(String dataEmissao) { this.dataEmissao = dataEmissao; }

    public String getValidade() { return validade; }
    public void setValidade(String validade) { this.validade = validade; }
}
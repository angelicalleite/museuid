package br.com.museuid.model;

import java.time.LocalDate;

public class Visitante {

    private int id;
    private String nome;
    private String funcao;
    private String cidade;
    private String estado;
    private String pais;
    private LocalDate dataVisita;
    private String descricao;
    private String tipo;

    public Visitante() {
    }

    public Visitante(int id) {
        this.id = id;
    }

    public Visitante(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Visitante(int id, String nome, String funcao, String cidade, String estado, String pais, LocalDate dataVisita, String descricao, String tipo) {
        this.id = id;
        this.nome = nome;
        this.funcao = funcao;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
        this.dataVisita = dataVisita;
        this.descricao = descricao;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public LocalDate getDataVisita() {
        return dataVisita;
    }

    public void setDataVisita(LocalDate dataVisita) {
        this.dataVisita = dataVisita;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}

package br.com.museuid.model;

import java.time.LocalDate;

public class Organizacao {

    private int id;
    private String nome;
    private String sigla;
    private String email;
    private String fax;
    private String telefone;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String estado;
    private String pais;
    private String descricao;
    private LocalDate data;

    public Organizacao() {
    }

    public Organizacao(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Organizacao(int id, String nome, String sigla, String email, String fax, String telefone, String logradouro, String bairro, String cidade, String estado, String pais, String descricao, LocalDate data) {
        this.id = id;
        this.nome = nome;
        this.sigla = sigla;
        this.email = email;
        this.fax = fax;
        this.telefone = telefone;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
        this.descricao = descricao;
        this.data = data;
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

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataCadastro() {
        return data;
    }

    public void setDataCadastro(LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return nome;
    }

}

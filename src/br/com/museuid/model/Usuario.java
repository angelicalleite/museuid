package br.com.museuid.model;

import java.time.LocalDate;

public class Usuario {

    private int id;
    private String nome;
    private String login;
    private String senha;
    private String email;
    private boolean status;
    private LocalDate dataCriacao;
    private String descricao;
    private TipoUsuario tipoUsuario;

    public Usuario(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Usuario(int id, String nome, String login, String senha, String email, boolean status, LocalDate dataCriacao, String descricao, TipoUsuario tipoUsuario) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.email = email;
        this.dataCriacao = dataCriacao;
        this.descricao = descricao;
        this.tipoUsuario = tipoUsuario;
        this.status = status;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String isAtivo() {
        return status ? "Ativo" : "Inativo";
    }

    @Override
    public String toString() {
        return nome;
    }

}

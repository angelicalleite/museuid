package br.com.museuid.model;

import java.time.LocalDate;

public class Auditoria {

    private int id;
    private String acao;
    private String descricao;
    private LocalDate data;
    private Usuario user;

    public Auditoria(int id, String acao, LocalDate data, String descricao, Usuario user) {
        this.id = id;
        this.acao = acao;
        this.descricao = descricao;
        this.data = data;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

}

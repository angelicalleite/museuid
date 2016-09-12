package br.com.museuid.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name="Auditoria")
public class Auditoria implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String acao;
    private String descricao;
    private LocalDate data;
    @OneToOne(cascade = CascadeType.ALL, optional = true, fetch = FetchType.EAGER)
    private Usuario user;

    public Auditoria() {
    }

    public Auditoria(Integer id, String acao, LocalDate data, String descricao, Usuario user) {
        this.id = id;
        this.acao = acao;
        this.descricao = descricao;
        this.data = data;
        this.user = user;
    }

    public Auditoria(String acao, LocalDate data, String descricao, Usuario user) {

        this.acao = acao;
        this.descricao = descricao;
        this.data = data;
        this.user = user;
    }

    public Auditoria(Integer id) {
        this.id = id;
    }

    public Auditoria(Usuario user) {
        this.user = user;
    }

    public Auditoria(Integer i, String acao, String descricao, Usuario user) {
        this.id = id;
        this.acao = acao;
        this.descricao = descricao;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

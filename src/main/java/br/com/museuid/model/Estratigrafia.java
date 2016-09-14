package br.com.museuid.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Estratigrafia")
public class Estratigrafia implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String formacao;
    private String grupo;
    private String descricao;

    public Estratigrafia() {
    }

    public Estratigrafia(Integer id) {
        this.id = id;
    }

    public Estratigrafia(Integer id, String formacao) {
        this.id = id;
        this.formacao = formacao;
    }

    public Estratigrafia(Integer id, String formacao, String grupo, String descricao) {
        this.id = id;
        this.formacao = formacao;
        this.grupo = grupo;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFormacao() {
        return formacao;
    }

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return formacao;
    }

}

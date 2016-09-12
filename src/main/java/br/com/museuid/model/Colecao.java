package br.com.museuid.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Colecao")
public class Colecao  implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String descricao;

    public Colecao(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Colecao(Integer id) {
        this.id = id;
    }

    public Colecao(String nome) {
        this.nome = nome;
    }

    public Colecao(Integer id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Colecao() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return nome;
    }

}

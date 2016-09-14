package br.com.museuid.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Local")
public class Local implements Serializable{
    @Id

    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String nome;
    private String descricao;
    @OneToOne(optional = true)
    private Setor setor;

    public Local() {
    }

    public Local(int id) {
        this.id = id;
    }

    public Local(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Local(int id, String nome, String descricao, Setor setor) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.setor = setor;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    @Override
    public String toString() {
        return nome;
    }

}

package br.com.museuid.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Designacao")
public class Designacao  implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String genero;
    private String especie;
    private String familia;
    private String ordem;
    private String classe;
    private String descricao;

    public Designacao(Integer id, String genero) {
        this.id = id;
        this.genero = genero;
    }

    public Designacao(Integer id) {
        this.id = id;
    }

    public Designacao(String genero) {
        this.genero = genero;
    }

    public Designacao(Integer id, String genero, String especie, String familia, String ordem, String classe, String descricao) {
        this.id = id;
        this.genero = genero;
        this.especie = especie;
        this.familia = familia;
        this.ordem = ordem;
        this.classe = classe;
        this.descricao = descricao;
    }

    public Designacao() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return genero;
    }
}

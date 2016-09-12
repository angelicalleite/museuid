package br.com.museuid.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Localizacao")
public class Localizacao implements Serializable {
    @Id

    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @OneToOne(cascade = CascadeType.ALL, optional = true, fetch = FetchType.EAGER)
    private Local local;
    @OneToOne(cascade = CascadeType.ALL, optional = true, fetch = FetchType.EAGER)
    private Catalogacao catalogacao;

    public Localizacao(int id, Local local, Catalogacao catalogacao) {
        this.id = id;
        this.local = local;
        this.catalogacao = catalogacao;
    }

    public Localizacao(Local local, Catalogacao catalogacao) {
        this.local = local;
        this.catalogacao = catalogacao;
    }

    public Localizacao() {

    }

    public Localizacao(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public Catalogacao getCatalogacao() {
        return catalogacao;
    }

    public void setCatalogacao(Catalogacao catalogacao) {
        this.catalogacao = catalogacao;
    }
}
package br.com.museuid.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Identificacao")
public class Identificacao  implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL, optional = true, fetch = FetchType.EAGER)
    private Catalogacao catalogacao;
    @OneToOne(cascade = CascadeType.ALL, optional = true, fetch = FetchType.EAGER)
    private Estratigrafia estratigrafia;
    @OneToOne(cascade = CascadeType.ALL, optional = true, fetch = FetchType.EAGER)
    private Colecao colecao;
    @OneToOne(cascade = CascadeType.ALL, optional = true, fetch = FetchType.EAGER)
    private Local localizacao;
    @OneToOne(cascade = CascadeType.ALL, optional = true, fetch = FetchType.EAGER)
    private Designacao designacao;
    @OneToOne(cascade = CascadeType.ALL, optional = true, fetch = FetchType.EAGER)
    private Emprestimo emprestimo;

    public Identificacao() {
    }

    public Identificacao(Catalogacao catalogacao, Estratigrafia estratigrafia, Colecao colecao, Local localizacao, Designacao designacao, Emprestimo emprestimo) {
        this.catalogacao = catalogacao;
        this.estratigrafia = estratigrafia;
        this.colecao = colecao;
        this.localizacao = localizacao;
        this.designacao = designacao;
        this.emprestimo = emprestimo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Catalogacao getCatalogacao() {
        return catalogacao;
    }

    public void setCatalogacao(Catalogacao catalogacao) {
        this.catalogacao = catalogacao;
    }

    public Estratigrafia getEstratigrafia() {
        return estratigrafia;
    }

    public void setEstratigrafia(Estratigrafia estratigrafia) {
        this.estratigrafia = estratigrafia;
    }

    public Colecao getColecao() {
        return colecao;
    }

    public void setColecao(Colecao colecao) {
        this.colecao = colecao;
    }

    public Local getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Local localizacao) {
        this.localizacao = localizacao;
    }

    public Designacao getDesignacao() {
        return designacao;
    }

    public void setDesignacao(Designacao designacao) {
        this.designacao = designacao;
    }

    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }
}

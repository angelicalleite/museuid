package br.com.museuid.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="EmprestimoItem")
public class EmprestimoItem  implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String conservacao;
    @OneToOne(optional = true)
    private Emprestimo emprestimo;
    @OneToOne(optional = true)
    private Catalogacao catalogacao;

    public EmprestimoItem() {
    }

    public EmprestimoItem(Integer id, String conservacao) {
        this.id = id;
        this.conservacao = conservacao;
    }

    public EmprestimoItem(String conservacao) {
        this.conservacao = conservacao;
    }

    public EmprestimoItem(Integer id, String conservacao, Emprestimo emprestimo, Catalogacao catalogacao) {
        this.id = id;
        this.conservacao = conservacao;
        this.emprestimo = emprestimo;
        this.catalogacao = catalogacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConservacao() {
        return conservacao;
    }

    public void setConservacao(String conservacao) {
        this.conservacao = conservacao;
    }

    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }

    public Catalogacao getCatalogacao() {
        return catalogacao;
    }

    public void setCatalogacao(Catalogacao catalogacao) {
        this.catalogacao = catalogacao;
    }
}

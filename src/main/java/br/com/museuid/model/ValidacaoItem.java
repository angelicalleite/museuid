package br.com.museuid.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="ValidacaoItem")
public class ValidacaoItem implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private boolean status;
    @OneToOne(optional = true)
    private Catalogacao catalogacao;
    @OneToOne(optional = true)
    private Validacao validacao;

    public ValidacaoItem(){
    }

    public ValidacaoItem(Integer id, boolean status, Catalogacao catalogacao, Validacao validacao) {
        this.id = id;
        this.status = status;
        this.catalogacao = catalogacao;
        this.validacao = validacao;
    }

    public ValidacaoItem(Integer id,boolean status, Catalogacao catalogacao) {
        this.id = id;
        this.status = status;
        this.catalogacao = catalogacao;
    }

    public ValidacaoItem(boolean status, Catalogacao catalogacao) {
        this.status = status;
        this.catalogacao = catalogacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Catalogacao getCatalogacao() {
        return catalogacao;
    }

    public void setCatalogacao(Catalogacao catalogacao) {
        this.catalogacao = catalogacao;
    }

    public Validacao getValidacao() {
        return validacao;
    }

    public void setValidacao(Validacao validacao) {
        this.validacao = validacao;
    }
}

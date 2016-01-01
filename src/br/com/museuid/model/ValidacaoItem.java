package br.com.museuid.model;

public class ValidacaoItem {

    private int id;
    private boolean status;
    private Catalogacao catalogacao;
    private Validacao validacao;

    public ValidacaoItem(boolean status, Catalogacao catalogacao) {
        this.status = status;
        this.catalogacao = catalogacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

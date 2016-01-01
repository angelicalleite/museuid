package br.com.museuid.model;

public class Localizacao {

    private int id;
    private Local local;
    private Catalogacao catalogacao;

    public Localizacao(int id, Local local, Catalogacao catalogacao) {
        this.id = id;
        this.local = local;
        this.catalogacao = catalogacao;
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
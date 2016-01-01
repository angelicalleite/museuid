package br.com.museuid.model;

public class Estratigrafia {

    private int id;
    private String formacao;
    private String grupo;
    private String descricao;

    public Estratigrafia() {
    }

    public Estratigrafia(int id) {
        this.id = id;
    }

    public Estratigrafia(int id, String formacao) {
        this.id = id;
        this.formacao = formacao;
    }

    public Estratigrafia(int id, String formacao, String grupo, String descricao) {
        this.id = id;
        this.formacao = formacao;
        this.grupo = grupo;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

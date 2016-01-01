package br.com.museuid.model;

public class Setor {

    private int id;
    private String nome;
    private String descricao;
    private Organizacao orgao;

    public Setor() {
    }

    public Setor(int id) {
        this.id = id;
    }

    public Setor(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Setor(int id, String nome, String descricao, Organizacao orgao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.orgao = orgao;
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

    public Organizacao getOrganizacao() {
        return orgao;
    }

    public void setOrganizacao(Organizacao orgao) {
        this.orgao = orgao;
    }

    @Override
    public String toString() {
        return nome;
    }
}

package br.com.museuid.model;

public class Instituicao {

    private int id;
    private String nome;
    private String representante;
    private String telefone;
    private String cidade;
    private String estado;
    private String pais;
    private String descricao;

    public Instituicao(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Instituicao(int id, String nome, String representante, String telefone, String cidade, String estado, String pais, String descricao) {
        this.id = id;
        this.nome = nome;
        this.representante = representante;
        this.telefone = telefone;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
        this.descricao = descricao;
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

    public String getRepresentante() {
        return representante;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return nome;
    }
}

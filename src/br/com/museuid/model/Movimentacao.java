package br.com.museuid.model;

import java.time.LocalDate;

public class Movimentacao {

    private int id;
    private String objetos;
    private String responsavel;
    private String origem;
    private String destino;
    private String tipo;
    private LocalDate data;
    private String descricao;

    public Movimentacao(int id, String objetos, String responsavel, String origem, String destino, String tipo, String descricao, LocalDate data) {
        this.id = id;
        this.objetos = objetos;
        this.origem = origem;
        this.destino = destino;
        this.tipo = tipo;
        this.responsavel = responsavel;
        this.data = data;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObjetos() {
        return objetos;
    }

    public void setObjetos(String objetos) {
        this.objetos = objetos;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}

package br.com.museuid.model;

import java.time.LocalDate;
import java.util.List;

public class Validacao {

    private int id;
    private String categoria;
    private String subcategoria;
    private int total;
    private int validados;
    private int invalidados;
    private boolean status;
    private LocalDate data;
    private Usuario responsavel;
    private List<ValidacaoItem> itens;

    public Validacao(int id, String categoria, String subcategoria, int total, int validados, int invalidados, boolean status, LocalDate data, Usuario responsavel, List<ValidacaoItem> itens) {
        this.id = id;
        this.categoria = categoria;
        this.subcategoria = subcategoria;
        this.total = total;
        this.validados = validados;
        this.invalidados = invalidados;
        this.status = status;
        this.data = data;
        this.responsavel = responsavel;
        this.itens = itens;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getValidados() {
        return validados;
    }

    public void setValidados(int validados) {
        this.validados = validados;
    }

    public int getInvalidados() {
        return invalidados;
    }

    public void setInvalidados(int invalidados) {
        this.invalidados = invalidados;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Usuario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Usuario responsavel) {
        this.responsavel = responsavel;
    }

    public List<ValidacaoItem> getItens() {
        return itens;
    }

    public void setItens(List<ValidacaoItem> itens) {
        this.itens = itens;
    }
}

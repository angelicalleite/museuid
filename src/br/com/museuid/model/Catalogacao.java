package br.com.museuid.model;

import java.time.LocalDate;

public class Catalogacao {

    private int id;
    private int numeroPartes;
    private boolean emprestimo;
    private String numeroOrdem;
    private String etiquetaRFID;
    private String procedencia;
    private String detalhesProcedencia;
    private String dimensoes;
    private String localizacao;
    private String descricao;
    private String dataEntrada;
    private LocalDate dataCadastro;
    private Colecao colecao;
    private Designacao designacao;
    private Estratigrafia estratigrafia;

    public Catalogacao() {
    }

    public Catalogacao(int id) {
        this.id = id;
    }

    public Catalogacao(int id, String numeroOrdem) {
        this.id = id;
        this.numeroOrdem = numeroOrdem;
    }

    public Catalogacao(int id, String numeroOrdem, Designacao designacao, Estratigrafia estratigrafia, Colecao colecao) {
        this.id = id;
        this.numeroOrdem = numeroOrdem;
        this.designacao = designacao;
        this.estratigrafia = estratigrafia;
        this.colecao = colecao;
    }

    public Catalogacao(int id, String numeroOrdem, String etiquetaRFID, String procedencia, String detalhesProcedencia, String dimensoes, int numeroPartes, String localizacao, String descricao, String dataEntrada, boolean emprestimo, Designacao designacao, Estratigrafia estratigrafia, Colecao colecao) {
        this.id = id;
        this.numeroOrdem = numeroOrdem;
        this.etiquetaRFID = etiquetaRFID;
        this.procedencia = procedencia;
        this.detalhesProcedencia = detalhesProcedencia;
        this.dimensoes = dimensoes;
        this.numeroPartes = numeroPartes;
        this.localizacao = localizacao;
        this.descricao = descricao;
        this.dataEntrada = dataEntrada;
        this.emprestimo = emprestimo;
        this.designacao = designacao;
        this.estratigrafia = estratigrafia;
        this.colecao = colecao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroOrdem() {
        return numeroOrdem;
    }

    public void setNumeroOrdem(String numeroOrdem) {
        this.numeroOrdem = numeroOrdem;
    }

    public String getEtiquetaRFID() {
        return etiquetaRFID;
    }

    public void setEtiquetaRFID(String etiquetaRFID) {
        this.etiquetaRFID = etiquetaRFID;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public String getDetalhesProcedencia() {
        return detalhesProcedencia;
    }

    public void setDetalhesProcedencia(String detalhesProcedencia) {
        this.detalhesProcedencia = detalhesProcedencia;
    }

    public String getDimensoes() {
        return dimensoes;
    }

    public void setDimensoes(String dimensoes) {
        this.dimensoes = dimensoes;
    }

    public int getNumeroPartes() {
        return numeroPartes;
    }

    public void setNumeroPartes(int numeroPartes) {
        this.numeroPartes = numeroPartes;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(String dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public boolean isEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(boolean emprestimo) {
        this.emprestimo = emprestimo;
    }

    public Designacao getDesignacao() {
        return designacao;
    }

    public void setDesignacao(Designacao designacao) {
        this.designacao = designacao;
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

}

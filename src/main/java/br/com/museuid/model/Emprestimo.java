package br.com.museuid.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name="Emprestimo")
public class Emprestimo implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String numeroEmprestimo;
    private String solicitante;
    private String cpf;
    private String rg;
    private String contato;
    private String email;
    private String responsavel;
    private String status;
    private LocalDate dataEmprestimmo;
    private LocalDate dataDevolucao;
    private LocalDate dataEntrega;
    private String descricao;
    private String observacoes;
    @OneToOne(cascade = CascadeType.ALL, optional = true, fetch = FetchType.EAGER)
    private Instituicao instituicao;

    public Emprestimo(Integer id) {
        this.id = id;
    }

    public Emprestimo(Integer id, String numeroEmprestimo) {
        this.id = id;
        this.numeroEmprestimo = numeroEmprestimo;
    }

    public Emprestimo(String numeroEmprestimo) {
        this.numeroEmprestimo = numeroEmprestimo;
    }

    public Emprestimo(Integer id, String numeroEmprestimo, String solicitante, String cpf, String rg, String contato, String email, String responsavel, String status, LocalDate dataEmprestimmo, LocalDate dataDevolucao, String descricao, String observacoes, Instituicao instituicao) {
        this.id = id;
        this.numeroEmprestimo = numeroEmprestimo;
        this.solicitante = solicitante;
        this.cpf = cpf;
        this.rg = rg;
        this.contato = contato;
        this.email = email;
        this.responsavel = responsavel;
        this.status = status;
        this.dataEmprestimmo = dataEmprestimmo;
        this.dataDevolucao = dataDevolucao;
        this.descricao = descricao;
        this.instituicao = instituicao;
        this.observacoes = observacoes;
    }

    public Emprestimo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeroEmprestimo() {
        return numeroEmprestimo;
    }

    public void setNumeroEmprestimo(String numeroEmprestimo) {
        this.numeroEmprestimo = numeroEmprestimo;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataEmprestimmo() {
        return dataEmprestimmo;
    }

    public void setDataEmprestimmo(LocalDate dataEmprestimmo) {
        this.dataEmprestimmo = dataEmprestimmo;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Instituicao getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(Instituicao instituicao) {
        this.instituicao = instituicao;
    }

    @Override
    public String toString() {
        return numeroEmprestimo;
    }


}

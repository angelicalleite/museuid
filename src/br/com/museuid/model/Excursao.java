package br.com.museuid.model;

import java.time.LocalDate;

public class Excursao {

    private int id;
    private String curso;
    private String horario;
    private LocalDate data;
    private String responsavel;
    private int participantes;
    private String contato;
    private String guias;
    private boolean agendamento;
    private String statusAgendamento;
    private String descricao;
    private Instituicao instituicao;

    public Excursao(int id, String curso, int participantes, String responsavel, String contato, String guias, String horario, LocalDate data, String descricao, boolean agendamento, String statusAgendamento, Instituicao instituicao) {
        this.id = id;
        this.curso = curso;
        this.horario = horario;
        this.data = data;
        this.responsavel = responsavel;
        this.participantes = participantes;
        this.contato = contato;
        this.guias = guias;
        this.agendamento = agendamento;
        this.statusAgendamento = statusAgendamento;
        this.descricao = descricao;
        this.instituicao = instituicao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public int getParticipantes() {
        return participantes;
    }

    public void setParticipantes(int participantes) {
        this.participantes = participantes;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getGuias() {
        return guias;
    }

    public void setGuias(String guias) {
        this.guias = guias;
    }

    public boolean isAgendamento() {
        return agendamento;
    }

    public void setAgendamento(boolean agendamento) {
        this.agendamento = agendamento;
    }

    public String getStatusAgendamento() {
        return statusAgendamento;
    }

    public void setStatusAgendamento(String statusAgendamento) {
        this.statusAgendamento = statusAgendamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Instituicao getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(Instituicao instituicao) {
        this.instituicao = instituicao;
    }
}
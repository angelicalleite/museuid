package br.com.museuid.model;

public class EmprestimoItem {

    private int id;
    private String conservacao;
    private Emprestimo emprestimo;
    private Catalogacao catalogacao;

    public EmprestimoItem(int id, String conservacao) {
        this.id = id;
        this.conservacao = conservacao;
    }

    public EmprestimoItem(int id, String conservacao, Emprestimo emprestimo, Catalogacao catalogacao) {
        this.id = id;
        this.conservacao = conservacao;
        this.emprestimo = emprestimo;
        this.catalogacao = catalogacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConservacao() {
        return conservacao;
    }

    public void setConservacao(String conservacao) {
        this.conservacao = conservacao;
    }

    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }

    public Catalogacao getCatalogacao() {
        return catalogacao;
    }

    public void setCatalogacao(Catalogacao catalogacao) {
        this.catalogacao = catalogacao;
    }

}

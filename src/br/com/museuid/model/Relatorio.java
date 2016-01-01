package br.com.museuid.model;

import br.com.museuid.util.Tempo;

public class Relatorio {

    private String tipo;
    private String data;
    private int total;

    public Relatorio(String data, int total) {
        this.data = data;
        this.total = total;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFormatar() {
        try {
            return Tempo.mes(data);
        } catch (NumberFormatException ex) {
            return data;
        }
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}


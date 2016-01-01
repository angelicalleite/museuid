package br.com.museuid.banco.dao;

import br.com.museuid.banco.controle.ConexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Possui objetos necessários para implementar os CRUDs a partir da base de
 * dados, através do conector que indicará de qual classe serão chamadas as
 * consultas.
 *
 * @author Angelica Leite
 */
public class DAO {

    protected Connection conector = ConexaoBanco.instancia().getConnection();
    protected ResultSet rs;
    protected PreparedStatement stm;

    public DAO() {
    }
}

package br.com.museuid.banco.dao;

import br.com.museuid.banco.controle.jpa.ModelDAO;
import br.com.museuid.banco.controle.jpa.ModelSessionFactory;
import br.com.museuid.model.ValidacaoItem;
import br.com.museuid.model.Visitante;


/**
 * DAO responsável pela ações realizadas na base de dados referentes aos visitantes
 */
public class ValidacaoItemDAO {
     /**
     * Inserir visitante na base de dados
     */
    public void inserir(ValidacaoItem validacaoItem) {
        new ModelDAO<ValidacaoItem>().add(validacaoItem);
    }
}

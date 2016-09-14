package br.com.museuid.banco.dao;

import br.com.museuid.banco.controle.jpa.ModelDAO;
import br.com.museuid.model.ValidacaoItem;


/**
 * DAO responsável pela ações realizadas na base de dados referentes aos visitantes
 */
public class ValidacaoItemDAO {
     /**
     * Inserir visitante na base de dados
     */
    public ValidacaoItem inserir(ValidacaoItem validacaoItem) {
        return new ModelDAO<ValidacaoItem>().add(validacaoItem);
    }
}

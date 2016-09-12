package br.com.museuid.banco.dao;

import br.com.museuid.banco.controle.jpa.ModelDAO;
import br.com.museuid.banco.controle.jpa.ModelSessionFactory;
import br.com.museuid.model.EmprestimoItem;
import br.com.museuid.model.Validacao;

/**
 * DAO responsÃ¡vel pela aÃ§Ãµes realizadas na base de dados referentes aos validaÃ§Ã£o
 */
public class EmprestimoItemDAO {
    /**
     * Inserir validaÃ§Ã£o na base de dados e retornar seu identificador para cadastro de seus itens
     */
    public void inserir(EmprestimoItem emprestimoItem) {
        new ModelDAO<EmprestimoItem>().add(emprestimoItem);
    }
}

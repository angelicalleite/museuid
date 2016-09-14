package br.com.museuid.banco.dao;

import br.com.museuid.banco.controle.jpa.ModelDAO;
import br.com.museuid.model.TipoUsuario;

/**
 * DAO responsÃ¡vel pela aÃ§Ãµes realizadas na base de dados referentes aos validaÃ§Ã£o
 */
public class TipoUsuarioDAO {
    /**
     * Inserir validação na base de dados e retornar seu identificador para cadastro de seus itens
     */
    public TipoUsuario inserir(TipoUsuario tipoUsuario) {
        return new ModelDAO<TipoUsuario>().add(tipoUsuario);
    }
}

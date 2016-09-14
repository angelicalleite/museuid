package br.com.museuid.banco.dao;

import br.com.museuid.banco.controle.jpa.ModelDAO;
import br.com.museuid.banco.controle.jpa.ModelSessionFactory;
import br.com.museuid.model.Instituicao;
import br.com.museuid.model.Validacao;
import br.com.museuid.model.ValidacaoItem;
import br.com.museuid.util.Mensagem;
import br.com.museuid.util.Tempo;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsável pela ações realizadas na base de dados referentes aos validação
 */
public class ValidacaoDAO {

    /**
     * Inserir validação na base de dados e retornar seu identificador para cadastro de seus itens
     */
    public Validacao inserir(Validacao validacao) {
        return new ModelDAO<Validacao>().add(validacao);
    }

    /**
     * Inserir validação na base de dados e retornar seu identificador para cadastro de seus itens
     */
    public Validacao validacao(Validacao validacao) {
        return new ModelDAO<Validacao>().add(validacao);
        /*
        int id = 0;

        try {
            String sql = "INSERT INTO tb_validacao ( categoria, subcategoria, total, validados, invalidados, status, data, fk_responsavel )  VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            stm = conector.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stm.setString(1, validacao.getCategoria());
            stm.setString(2, validacao.getSubcategoria());
            stm.setInt(3, validacao.getTotal());
            stm.setInt(4, validacao.getValidados());
            stm.setInt(5, validacao.getInvalidados());
            stm.setInt(6, validacao.isStatus() ? 1 : 0);
            stm.setTimestamp(7, Tempo.toTimestamp(validacao.getData()));
            stm.setInt(8, 1);

            stm.executeUpdate();

            rs = stm.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao inserir validação na base de dados! \n" + ex);
        }

        return id;*/
    }

    /**
     * Inserir itens da validação informado na base de dados
     */
    public void item(int validacao, ValidacaoItem item) {
        List<ValidacaoItem> items = new ArrayList<>();
        items.add(item);
        Validacao validacao1 = new Validacao(validacao, items);
        new ModelDAO<Validacao>().add(validacao1);
        /*
        try {
            String sql = "INSERT INTO tb_validacao_item (status, fk_catalogacao, fk_validacao )  VALUES ( ?, ?, ?)";

            stm = conector.prepareStatement(sql);

            stm.setInt(1, item.isStatus() ? 1 : 0);
            stm.setInt(2, item.getCatalogacao().getId());
            stm.setInt(3, validacao);

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao inserir item da validação na base de dados! \n" + ex);
        }*/
    }
}

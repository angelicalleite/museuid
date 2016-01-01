package br.com.museuid.banco.dao;

import br.com.museuid.model.Designacao;
import br.com.museuid.util.Mensagem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsável pela ações realizadas na base de dados referentes as designações
 */
public class DesignacaoDAO extends DAO {

    public DesignacaoDAO() {
        super();
    }

    /**
     * Inserir desigcação na base de dados
     */
    public void inserir(Designacao designacao) {
        try {
            String sql = "INSERT INTO  tb_designacao (genero, especie, familia, classe, ordem, descricao) VALUES (?, ?, ?, ?, ?, ?)";

            stm = conector.prepareStatement(sql);

            stm.setString(1, designacao.getGenero());
            stm.setString(2, designacao.getEspecie());
            stm.setString(3, designacao.getFamilia());
            stm.setString(4, designacao.getClasse());
            stm.setString(5, designacao.getOrdem());
            stm.setString(6, designacao.getDescricao());

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao inserir designação na base de dados! \n" + ex);
        }
    }

    /**
     * Atualizar dados designação na base de dados
     */
    public void editar(Designacao designacao) {
        try {
            String sql = "UPDATE tb_designacao SET genero=?, especie=?, familia=?, classe=?, ordem=?, descricao=? WHERE id_designacao=? ";

            stm = conector.prepareStatement(sql);

            stm.setString(1, designacao.getGenero());
            stm.setString(2, designacao.getEspecie());
            stm.setString(3, designacao.getFamilia());
            stm.setString(4, designacao.getClasse());
            stm.setString(5, designacao.getOrdem());
            stm.setString(6, designacao.getDescricao());

            stm.setInt(7, designacao.getId());

            stm.executeUpdate();
            stm.close();
        } catch (SQLException ex) {
            Mensagem.erro("Erro ao atualizar designação na base de dados! \n" + ex);
            ex.printStackTrace();
        }
    }

    /**
     * Excluir designação da base de dados
     */
    public void excluir(int idDesignacao) {
        try {
            String sql = "DELETE FROM tb_designacao WHERE id_designacao=?";

            stm = conector.prepareStatement(sql);
            stm.setInt(1, idDesignacao);

            stm.execute();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao excluir designação na base de dados! \n" + ex);
        }
    }

    /**
     * Consultar todas designações cadastradas na base de dados
     */
    public List<Designacao> listar() {

        List<Designacao> dadosDesignacao = new ArrayList<>();

        try {
            String sql = "SELECT * FROM tb_designacao";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                Designacao designacao = new Designacao(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
                dadosDesignacao.add(designacao);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar designação na base de dados! \n" + ex);
        }

        return dadosDesignacao;
    }

    /**
     * Consultar designações cadastradas na base de dados para criação de combos de designações
     */
    public List<Designacao> combo() {

        List<Designacao> dadosDesignacao = new ArrayList<>();

        try {
            String sql = "SELECT id_designacao, genero FROM tb_designacao ORDER BY genero";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                Designacao designacao = new Designacao(rs.getInt(1), rs.getString(2));
                dadosDesignacao.add(designacao);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar designação na base de dados! \n" + ex);
        }

        return dadosDesignacao;
    }

    /**
     * Consultar se nome da designação já está cadastrado na base
     */
    public boolean isDesignacao(String nome, int id) {
        try {
            String sql = "SELECT genero FROM tb_designacao WHERE genero =? AND id_designacao !=? ";

            stm = conector.prepareStatement(sql);
            stm.setString(1, nome);
            stm.setInt(2, id);
            rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getString(1).toLowerCase().trim().equals(nome.toLowerCase().trim().toLowerCase());
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao validar genêro designação na base de dados! \n" + ex);
        }

        return false;
    }

    /**
     * Consultar total de designações cadastradas
     */
    public int total() {
        try {
            String sql = "SELECT COUNT(*) FROM tb_designacao";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar total de designações cadastradas na base de dados! \n" + ex);
        }

        return 0;
    }
}

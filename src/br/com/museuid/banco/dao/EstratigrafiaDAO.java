package br.com.museuid.banco.dao;

import br.com.museuid.model.Estratigrafia;
import br.com.museuid.util.Mensagem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsável pela ações realizadas na base de dados referentes as estratigrafias
 */
public class EstratigrafiaDAO extends DAO {

    public EstratigrafiaDAO() {
        super();
    }

    /**
     * Inserir estratigrafia na base de dados
     */
    public void inserir(Estratigrafia estratigrafia) {
        try {
            String sql = "INSERT INTO tb_estratigrafia (formacao, grupo, descricao) VALUES (?, ?, ?) ";

            stm = conector.prepareStatement(sql);

            stm.setString(1, estratigrafia.getFormacao());
            stm.setString(2, estratigrafia.getGrupo());
            stm.setString(3, estratigrafia.getDescricao());

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao inserir na base de dados estratigrafias! \n" + ex);
        }
    }

    /**
     * Atualizar dados estratigrafia na base de dados
     */
    public void editar(Estratigrafia estratigrafia) {
        try {
            String sql = "UPDATE tb_estratigrafia SET formacao=?, grupo=?, descricao=? WHERE id_estratigrafia=? ";

            stm = conector.prepareStatement(sql);

            stm.setString(1, estratigrafia.getFormacao());
            stm.setString(2, estratigrafia.getGrupo());
            stm.setString(3, estratigrafia.getDescricao());

            stm.setInt(4, estratigrafia.getId());

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao atualizar na base de dados estratigrafias! \n" + ex);
        }
    }

    /**
     * Excluir estratigrafia da base de dados
     */
    public void excluir(int idEstratigrafia) {
        try {
            String sql = "DELETE FROM tb_estratigrafia WHERE id_estratigrafia = ? ";

            stm = conector.prepareStatement(sql);
            stm.setInt(1, idEstratigrafia);

            stm.execute();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao excluir na base de dados estratigrafias! \n" + ex);
        }
    }

    /**
     * Consultar todas estratigrafia cadastradas na base de dados
     */
    public List<Estratigrafia> listar() {

        List<Estratigrafia> dadosEstratigrafia = new ArrayList<>();

        try {
            String sql = "SELECT * FROM tb_estratigrafia ";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                Estratigrafia estratigrafia = new Estratigrafia(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
                dadosEstratigrafia.add(estratigrafia);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar na base de dados estratigrafias! \n" + ex);
        }

        return dadosEstratigrafia;
    }

    /**
     * Consultar estratigrafias cadastradas na base de dados para criação de combos de estratigrafia
     */
    public List<Estratigrafia> combo() {

        List<Estratigrafia> dadosEstratigrafia = new ArrayList<>();

        try {
            String sql = "SELECT id_estratigrafia, formacao FROM tb_estratigrafia ORDER BY formacao ";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                Estratigrafia estratigrafia = new Estratigrafia(rs.getInt(1), rs.getString(2));
                dadosEstratigrafia.add(estratigrafia);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar na base de dados estratigrafias! \n" + ex);
        }

        return dadosEstratigrafia;
    }

    /**
     * Consultar se nome da estratigrafia já está cadastrado na base
     */
    public boolean isEstratigrafia(String formacao, int id) {
        try {
            String sql = "SELECT formacao FROM tb_estratigrafia WHERE formacao =? AND id_estratigrafia != ? ";

            stm = conector.prepareStatement(sql);
            stm.setString(1, formacao);
            stm.setInt(2, id);
            rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getString(1).toLowerCase().trim().equals(formacao.toLowerCase().trim().toLowerCase());
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao validar formação da estratigrafia na base de dados! \n" + ex);
        }

        return false;
    }

    /**
     * Consultar total de estratigrafias cadastradas
     */
    public int total() {
        try {
            String sql = "SELECT COUNT(*) FROM tb_estratigrafia";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar total de estratigrafias cadastradas na base de dados! \n" + ex);
        }

        return 0;
    }
}

package br.com.museuid.banco.dao;

import br.com.museuid.model.*;
import br.com.museuid.util.Mensagem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsável pela ações realizadas na base de dados referentes as localização
 */
public class LocalizacaoDAO extends DAO {

    public LocalizacaoDAO() {
        super();
    }

    /**
     * Inserir catalogação na base de dados
     */
    public void inserir(int local, int catalogacao) {
        try {
            String sql = "INSERT INTO tb_localizacao (fk_catalogacao, fk_local) VALUES (?, ?) ";

            stm = conector.prepareStatement(sql);

            stm.setInt(1, catalogacao);
            stm.setInt(2, local);

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao inserir catalogação na base de dados! \n" + ex);
        }
    }

    /**
     * Consultar informações de localização do local
     */
    public Local info(int catalogacao) {
        try {
            String sql = "SELECT tb_local.id_local, tb_local.nome, tb_setor.id_setor, tb_setor.nome, tb_organizacao.id_orgao, tb_organizacao.nome "
                    + "FROM tb_localizacao, tb_local, tb_setor, tb_organizacao WHERE tb_localizacao.fk_catalogacao = ? AND tb_localizacao.fk_local = tb_local.id_local "
                    + "AND tb_local.fk_setor = tb_setor.id_setor AND tb_setor.fk_orgao = tb_organizacao.id_orgao ";

            stm = conector.prepareStatement(sql);
            stm.setInt(1, catalogacao);
            rs = stm.executeQuery();

            if (rs.next()) {
                return new Local(rs.getInt(1), rs.getString(2), "", new Setor(rs.getInt(3), rs.getString(4), "", new Organizacao(rs.getInt(5), rs.getString(6))));
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar catalogação do local informado na base de dados! \n" + ex);
        }

        return null;
    }

    /**
     * Consultar se catalogacao já está cadastrado em alguma localização e obter identifiador da catalogacao cadastrada
     */
    public boolean isLocalizacao(int catalogacao) {

        try {
            String sql = "SELECT tb_localizacao.id_localizacao FROM tb_localizacao WHERE fk_catalogacao = ?";

            stm = conector.prepareStatement(sql);
            stm.setInt(1, catalogacao);
            rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) != 0;
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar catalogação do local informado na base de dados! \n" + ex);
        }

        return false;
    }

    /**
     * Listar todas catalogações de um determinado local
     */
    public List<Localizacao> localizacoes(int local) {

        List<Localizacao> dados = new ArrayList<>();

        try {
            String sql = "SELECT tb_catalogacao.id_catalogacao, tb_catalogacao.numero_ordem, tb_designacao.id_designacao, " +
                    "tb_designacao.genero, tb_estratigrafia.id_estratigrafia, tb_estratigrafia.formacao, tb_colecao.id_colecao,tb_colecao.nome, tb_localizacao.id_localizacao " +
                    "FROM tb_catalogacao, tb_localizacao, tb_designacao, tb_estratigrafia,tb_colecao WHERE tb_localizacao.fk_local = ? " +
                    "AND tb_localizacao.fk_catalogacao = tb_catalogacao.id_catalogacao " +
                    "AND tb_designacao.id_designacao = tb_catalogacao.fk_designacao " +
                    "AND tb_estratigrafia.id_estratigrafia = tb_catalogacao.fk_estratigrafia " +
                    "AND tb_colecao.id_colecao = tb_catalogacao.fk_colecao ";

            stm = conector.prepareStatement(sql);
            stm.setInt(1, local);
            rs = stm.executeQuery();

            while (rs.next()) {
                Catalogacao catalogacao = new Catalogacao(rs.getInt(1), rs.getString(2), new Designacao(rs.getInt(3), rs.getString(4)), new Estratigrafia(rs.getInt(5), rs.getString(6)), new Colecao(rs.getInt(7), rs.getString(8)));
                dados.add(new Localizacao(rs.getInt(9), new Local(local), catalogacao));
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar localizações das catalogações base de dados! \n" + ex);
        }

        return dados;
    }

    /**
     * Excluir localização
     */
    public void excluir(int localizacao) {
        try {
            String sql = "DELETE FROM tb_localizacao WHERE id_localizacao=? ";

            stm = conector.prepareStatement(sql);
            stm.setInt(1, localizacao);

            stm.execute();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao excluir na base de dados localização! \n" + ex);
        }
    }
}

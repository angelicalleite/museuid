package br.com.museuid.banco.dao;

import br.com.museuid.banco.controle.jpa.ModelDAO;
import br.com.museuid.banco.controle.jpa.ModelSessionFactory;
import br.com.museuid.model.*;
import br.com.museuid.util.Mensagem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsável pela ações realizadas na base de dados referentes as localização
 */
public class LocalizacaoDAO {

    /**
     * Inserir catalogação na base de dados
     */
    public Localizacao inserir(Localizacao localizacao) {
        return new ModelDAO<Localizacao>().add(localizacao);
    }

    /**
     * Inserir catalogação na base de dados
     */
    public void inserir(int id_localizacao, int id_catalogacao) {
        Localizacao localizacao = new Localizacao(id_localizacao);
        localizacao.setCatalogacao(new Catalogacao(id_catalogacao));
        new ModelDAO<Localizacao>().add(localizacao);
        /*
        try {
            String sql = "INSERT INTO tb_localizacao (fk_catalogacao, fk_local) VALUES (?, ?) ";

            stm = conector.prepareStatement(sql);

            stm.setInt(1, catalogacao);
            stm.setInt(2, local);

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao inserir catalogação na base de dados! \n" + ex);
        }*/
    }

    /**
     * Excluir localização
     */
    public void excluir(int idlocalizacao) {
        new ModelDAO<Instituicao>().delete(new Instituicao(idlocalizacao));
        /*
        try {
            String sql = "DELETE FROM tb_localizacao WHERE id_localizacao=? ";

            stm = conector.prepareStatement(sql);
            stm.setInt(1, localizacao);

            stm.execute();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao excluir na base de dados localização! \n" + ex);
        }*/
    }

    /**
     * Consultar informações de localização do local
     */
    public Local info(int catalogacao) {
        ModelDAO modelDAO = new ModelDAO<Local>();

        String sql = " FROM Localizacao l, Local lo, Setor s, Organizacao o " +
                  " WHERE l.catalogacao.id = "+catalogacao
                + " AND l.local.id = lo.id "
                + " AND lo.setor.id = s.id AND s.orgao.id = o.id ";

        List<Local> locals = (List) modelDAO.findSQL(sql);

        if(locals == null) {
            return null;
        } else {
            return locals.get(0);
        }
        /*
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

        return null;*/
    }

    /**
     * Consultar se catalogacao já está cadastrado em alguma localização e obter identifiador da catalogacao cadastrada
     */
    public boolean isLocalizacao(int catalogacao) {
        ModelDAO modelDAO = new ModelDAO<Local>();

        String sql = " FROM Localizacao l WHERE l.catalogacao.id = "+catalogacao;

        Local locals = (Local) modelDAO.findSQL(sql);

        if(locals == null) {
            return false;
        } else {
            return true;
        }
        /*

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

        return false;*/
    }

    /**
     * Listar todas catalogações de um determinado local
     */
    public List<Localizacao> localizacoes(int local) {
        ModelDAO modelDAO = new ModelDAO<Localizacao>();

        String sql = " FROM Catalogacao c, Localizacao l, Designacao d, Estratigrafia e, Colecao co" +
                    " WHERE l.local.id = " + local+
                    " AND l.catalogacao.id = c.id " +
                    " AND d.id = c.designacao.id " +
                    " AND e.id = c.estratigrafia.id " +
                    " AND co.id = c.colecao.id ";

        List<Localizacao> locals = (List) modelDAO.findSQL(sql);

        if(locals == null) {
            return null;
        } else {
            return locals;
        }
        /*
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

        return dados;*/
    }
}

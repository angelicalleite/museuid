package br.com.museuid.banco.dao;

import br.com.museuid.banco.controle.jpa.ModelDAO;
import br.com.museuid.banco.controle.jpa.ModelSessionFactory;
import br.com.museuid.model.*;
import br.com.museuid.util.Mensagem;

import java.sql.SQLException;
import java.util.List;

/**
 * DAO responsável pela ações realizadas na base de dados referentes as identificação dos fosseis
 */
public class IdentificacaoDAO {

    /**
     * Inserir catalogação na base de dados
     */
    public Identificacao inserir(Identificacao identificacao) {
        return new ModelDAO<Identificacao>().add(identificacao);
    }

    /**
     * Identificar através do numero de ordem ou etiqueta de rfid e consultar informações da catalogação(fossil)
     */
    public Catalogacao identificar(String identificador) {
        ModelDAO modelDAO = new ModelDAO<Catalogacao>();

        String sql =  " SELECT c FROM Catalogacao c, Designacao d, Estratigrafia e, Colecao col "
                    + " WHERE cat.designacao.id = d.id AND c.estratigrafia.id = e.id "
                    + " AND c.colecao.id = col.id AND c.numeroOrdem = "+identificador+" OR c.etiquetaRFID = "+identificador;

        List<Catalogacao> catalogacaos = (List<Catalogacao>) modelDAO.findSQL(sql);

        if(catalogacaos == null) {
            return null;
        } else {
            return catalogacaos.get(0);
        }
        /*
        Catalogacao catalogacao = null;

        try {
            String sql = "SELECT cat.id_catalogacao, cat.numero_ordem, cat.etiqueta_rfid, cat.procedencia, cat.descricao_procedencia, cat.dimensoes, cat.numero_partes, cat.localizacao, "
                    + "cat.descricao, cat.data_entrada,cat.data_cadastro, cat.status_emprestimo, des.*, est.*, col.* "
                    + "FROM tb_catalogacao AS cat, tb_designacao AS des, tb_estratigrafia AS est, tb_colecao AS col "
                    + "WHERE cat.fk_designacao = des.id_designacao AND cat.fk_estratigrafia = est.id_estratigrafia "
                    + "AND cat.fk_colecao = col.id_colecao AND cat.numero_ordem = ? OR cat.etiqueta_rfid = ? ";

            stm = conector.prepareStatement(sql);
            stm.setString(1, identificador);
            stm.setString(2, identificador);
            rs = stm.executeQuery();

            while (rs.next()) {
                Colecao colecao = new Colecao(rs.getInt(24), rs.getString(25), rs.getString(26));
                Estratigrafia estratigrafia = new Estratigrafia(rs.getInt(20), rs.getString(21), rs.getString(22), rs.getString(23));
                Designacao designacao = new Designacao(rs.getInt(13), rs.getString(14), rs.getString(15), rs.getString(16), rs.getString(17), rs.getString(18), rs.getString(19));

                catalogacao = new Catalogacao(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getString(10),
                        rs.getInt(12) == 1, designacao, estratigrafia, colecao);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao identificar fóssil na base de dados! \n" + ex);
        }

        return catalogacao;
        */
    }

    /**
     * Verificar se existe alguma catalogação cadastrada com número de ordem ou etiqueta rfid informada
     */
    public boolean validarIdentificador(String identificador) {
        ModelDAO modelDAO = new ModelDAO<Catalogacao>();

        String sql = "SELECT id FROM Catalogacao c WHERE c.numeroOrdem = "+identificador+" OR etiquetaRFID = "+identificador;

        List<Catalogacao> catalogacaos = (List<Catalogacao>) modelDAO.findSQL(sql);

        if(catalogacaos == null) {
            return false;
        } else {
            return true;
        }
        /*
        try {
            String sql = "SELECT id_catalogacao FROM tb_catalogacao WHERE numero_ordem = ? OR etiqueta_rfid = ? ";

            stm = conector.prepareStatement(sql);
            stm.setString(1, identificador);
            stm.setString(2, identificador);
            rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) != 0;
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao validar fóssil na base de dados! \n" + ex);
        }

        return false;
        */
    }

}

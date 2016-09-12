package br.com.museuid.banco.dao;

import br.com.museuid.banco.controle.jpa.ModelDAO;
import br.com.museuid.banco.controle.jpa.ModelSessionFactory;
import br.com.museuid.model.*;
import java.util.*;

/**
 * DAO responsável pela ações realizadas na base de dados referentes as catalogações
 */
public class CatalogacaoDAO {//extends DAO {
    /*
    public CatalogacaoDAO() {
        super();
    }
    */

    /**
     * Inserir catalogação na base de dados
     */
    public void inserir(Catalogacao catalogacao) {
        new ModelDAO<Catalogacao>().add(catalogacao);
        /*
        try {
            String sql = "INSERT INTO tb_catalogacao (numero_ordem, etiqueta_rfid, procedencia, descricao_procedencia, dimensoes, numero_partes, " +
                    "localizacao, descricao, data_entrada, data_cadastro, status_emprestimo, fk_designacao, fk_estratigrafia, fk_colecao) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, ?, ?, ?) ";

            stm = conector.prepareStatement(sql);

            stm.setString(1, catalogacao.getNumeroOrdem());
            stm.setString(2, catalogacao.getEtiquetaRFID());
            stm.setString(3, catalogacao.getProcedencia());
            stm.setString(4, catalogacao.getDetalhesProcedencia());
            stm.setString(5, catalogacao.getDimensoes());
            stm.setInt(6, catalogacao.getNumeroPartes());
            stm.setString(7, catalogacao.getLocalizacao());
            stm.setString(8, catalogacao.getDescricao());
            stm.setString(9, catalogacao.getDataEntrada());
            stm.setInt(10, catalogacao.isEmprestimo() ? 1 : 0);
            stm.setInt(11, catalogacao.getDesignacao().getId());
            stm.setInt(12, catalogacao.getEstratigrafia().getId());
            stm.setInt(13, catalogacao.getColecao().getId());

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao inserir catalogação na base de dados! \n" + ex);
        }
        */
    }

    /**
     * Atualizar dados da catalogação
     */
    public void editar(Catalogacao catalogacao) {
        new ModelDAO<Catalogacao>().update(catalogacao);
        /*
        try {
            String sql = "UPDATE tb_catalogacao SET numero_ordem=?, etiqueta_rfid=?, procedencia=?, descricao_procedencia=?, dimensoes=?, numero_partes=?, localizacao=?, descricao=?, "
                    + "data_entrada=?, status_emprestimo=?, fk_designacao=?, fk_estratigrafia=?, fk_colecao=? WHERE id_catalogacao=? ";

            stm = conector.prepareStatement(sql);

            stm.setString(1, catalogacao.getNumeroOrdem());
            stm.setString(2, catalogacao.getEtiquetaRFID());
            stm.setString(3, catalogacao.getProcedencia());
            stm.setString(4, catalogacao.getDetalhesProcedencia());
            stm.setString(5, catalogacao.getDimensoes());
            stm.setInt(6, catalogacao.getNumeroPartes());
            stm.setString(7, catalogacao.getLocalizacao());
            stm.setString(8, catalogacao.getDescricao());
            stm.setString(9, catalogacao.getDataEntrada());
            stm.setInt(10, catalogacao.isEmprestimo() ? 1 : 0);
            stm.setInt(11, catalogacao.getDesignacao().getId());
            stm.setInt(12, catalogacao.getEstratigrafia().getId());
            stm.setInt(13, catalogacao.getColecao().getId());

            stm.setInt(14, catalogacao.getId());

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao atualizar catalogação na base de dados! \n" + ex);
        }
         */
    }

    /**
     * Excluir catalogação de acordo com o identificador informado
     */
    public void excluir(int idCatalogacao) {
        new ModelDAO<Catalogacao>().delete(new Catalogacao(idCatalogacao));
        /*
        try {
            String sql = "DELETE FROM tb_catalogacao WHERE id_catalogacao =? ";

            stm = conector.prepareStatement(sql);
            stm.setInt(1, idCatalogacao);

            stm.execute();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao excluir catalogação na base de dados! \n" + ex);
        }
        */
    }

    /**
     * Verificar se numero de ordem da catalogação está cadastrado na base de dados
     */
    public boolean validarNumeroOrdem(String numOrdem, int idCatalogacao) {
        ModelDAO modelDAO = new ModelDAO<Catalogacao>();

        Catalogacao catalogacao1 = (Catalogacao) modelDAO.findSQL("from Catalogacao  c where c.numeroOrdem = "+numOrdem+" and c.id="+idCatalogacao);

        if(catalogacao1 == null) {
            return false;
        } else {
           return true;
        }
        /*
        try {
            String sql = "SELECT numero_ordem FROM tb_catalogacao WHERE numero_ordem =? AND id_catalogacao !=? ";

            stm = conector.prepareStatement(sql);
            stm.setString(1, numOrdem);
            stm.setInt(2, idCatalogacao);
            rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getString(1).toLowerCase().trim().equals(numOrdem.toLowerCase().trim());
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao validar número ordem da catalogação na base de dados! \n" + ex);
        }
        return false;
        */

    }

    /**
     * Verificar se etiqueta de rfid está cadastrada na base de dados
     */
    public boolean validarEtiquetaRFID(String etiqueta, int idCatalogacao) {
        ModelDAO modelDAO = new ModelDAO<Catalogacao>();

        Catalogacao catalogacao1 = (Catalogacao) modelDAO.findSQL("from Catalogacao  c where c.etiquetaRFID = "+etiqueta+" and c.id="+idCatalogacao);

        if(catalogacao1 == null) {
            return false;
        } else {
            return true;
        }
        /*
        try {
            String sql = "SELECT etiqueta_rfid FROM tb_catalogacao WHERE etiqueta_rfid = ? AND id_catalogacao != ? ";

            stm = conector.prepareStatement(sql);
            stm.setString(1, etiqueta);
            stm.setInt(2, idCatalogacao);
            rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getString(1).toLowerCase().trim().equals(etiqueta.toLowerCase().trim());
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao validar etiqueta RFID catalogação na base de dados! \n" + ex);
        }
        return false;
        */
    }

    /**
     * Consultar identificador da catalogação com possui o número de ordem informado
     */
    public int infoId(String numOrdem) {
        ModelDAO modelDAO = new ModelDAO<Catalogacao>();

        Catalogacao catalogacao1 = (Catalogacao) modelDAO.findSQL("from Catalogacao  c where c.numOrdem = "+numOrdem);

        if(catalogacao1 == null) {
            return 0;
        } else {
            return catalogacao1.getId();
        }

        /*
        int catalogacao = 0;

        try {
            String sql = "SELECT id_catalogacao FROM tb_catalogacao WHERE numero_ordem =? ";

            stm = conector.prepareStatement(sql);
            stm.setString(1, numOrdem);
            rs = stm.executeQuery();

            if (rs.next()) {
                catalogacao = rs.getInt(1);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar identificador da catalogação na base de dados! \n" + ex);
        }

        return catalogacao;
        */
    }

    /**
     * Consultar informações do emprestimo caso a catalogação(fossil) esteja emprestado
     */
    public String infoNumEmprestimo(int catalogacao) {
        ModelDAO modelDAO = new ModelDAO<Emprestimo>();

        Emprestimo emprestimo = (Emprestimo) modelDAO.findSQL(
                "SELECT e FROM Catalogacao c, EmprestimoItem i, Emprestimo e "
              + " WHERE  c.status = 1 AND c.id = "+catalogacao
              + " AND    c.id = i.catalogacao.id "
              + " AND    i.emprestimo.id = e.id ");

        if(emprestimo == null) {
            return null;
        } else {
            return emprestimo.getNumeroEmprestimo();
        }
        /*
        String numEmprestimo = "";

        try {
            String sql = "SELECT tb_emprestimo.numero_emprestimo FROM tb_catalogacao, tb_emprestimo_item, tb_emprestimo "
                    + "WHERE  tb_catalogacao.status_emprestimo = 1 AND tb_catalogacao.id_catalogacao = ? "
                    + "AND tb_catalogacao.id_catalogacao = tb_emprestimo_item.fk_catalogacao "
                    + "AND tb_emprestimo_item.fk_emprestimo = tb_emprestimo.id_emprestimo ";

            stm = conector.prepareStatement(sql);
            stm.setInt(1, catalogacao);
            rs = stm.executeQuery();

            while (rs.next()) {
                numEmprestimo = rs.getString(1);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar número empréstimo da catalogação na base de dados! \n" + ex);
        }

        return numEmprestimo;
        */
    }

    /**
     * Consultar informações da catalogação do número de ordem informado
     */
    public Catalogacao info(String numOrdem) {
        ModelDAO modelDAO = new ModelDAO<Catalogacao>();

        Catalogacao catalogacao1 = (Catalogacao) modelDAO.findSQL("from Catalogacao  c where c.numeroOrdem = "+numOrdem);

        if(catalogacao1 == null) {
            return null;
        } else {
            return catalogacao1;
        }
        /*
        Catalogacao catalogacao = new Catalogacao();

        try {
            String sql = "SELECT cat.id_catalogacao, cat.numero_ordem, cat.etiqueta_rfid, cat.dimensoes, cat.numero_partes, "
                    + "cat.localizacao, des.id_designacao, des.genero, est.id_estratigrafia, est.formacao, col.id_colecao, col.descricao "
                    + "FROM tb_catalogacao AS cat, tb_colecao AS col, tb_estratigrafia AS est, tb_designacao AS des "
                    + "WHERE numero_ordem =? AND cat.fk_designacao = des.id_designacao AND cat.fk_estratigrafia = est.id_estratigrafia AND cat.fk_colecao = col.id_colecao ";

            stm = conector.prepareStatement(sql);
            stm.setString(1, numOrdem);
            rs = stm.executeQuery();

            if (rs.next()) {
                catalogacao = new Catalogacao(rs.getInt(1), rs.getString(2), rs.getString(3), "", "", rs.getString(4), rs.getInt(5), rs.getString(6),
                        "", "", false, new Designacao(rs.getInt(7), rs.getString(8)), new Estratigrafia(rs.getInt(9), rs.getString(10)), new Colecao(rs.getInt(11), rs.getString(12)));
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar informações da catalogação na base de dados! \n" + ex);
        }

        return catalogacao;
        */
    }

    /**
     * Atualizar estado do empréstimo da catalogação informada
     */
    public void statusEmprestada(int catalogacao, boolean status) {
        Catalogacao catalogacao1 = new Catalogacao(catalogacao);
        catalogacao1.setEmprestimo(status);
        new ModelDAO<Catalogacao>().update(catalogacao1);

         /*
        try {
            String sql = "UPDATE tb_catalogacao SET status_emprestimo =? WHERE id_catalogacao=? ";

            stm = conector.prepareStatement(sql);
            stm.setInt(1, status ? 1 : 0);
            stm.setInt(2, catalogacao);

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao atualizar status catalogação na base de dados! \n" + ex);
        }
        */
    }

    /**
     * Consultar estado do empréstimo
     */
    public boolean isEmprestada(int catalogacao) {
        Catalogacao catalogacao1 = (Catalogacao) new ModelDAO<Catalogacao>().findSQL("FROM Catalogacao c WHERE c.id = "+catalogacao);

        if(catalogacao1 == null) {
            return false;
        } else {
            return true;
        }
         /*
        try {
            String sql = "SELECT status_emprestimo FROM tb_catalogacao WHERE id_catalogacao =? ";

            stm = conector.prepareStatement(sql);
            stm.setInt(1, catalogacao);
            rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) == 1;
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar na status empréstimo da catalogação base de dados! \n" + ex);
        }

        return false;
        */
    }

    /**
     * Consultar todas catalogações cadastradas na base de dados
     */
    public List<Catalogacao> listar() {
        List<Catalogacao> lists = (List<Catalogacao>) new ModelDAO<Catalogacao>().lists(new Catalogacao());

        if(lists == null) {
            return null;
        } else {
            return lists;
        }
        /*
        List<Catalogacao> dados = new ArrayList<>();

        try {
            String sql = "SELECT cat.id_catalogacao, cat.numero_ordem, cat.etiqueta_rfid, cat.procedencia, cat.descricao_procedencia, "
                    + "cat.dimensoes, cat.numero_partes, cat.localizacao, cat.localizacao, cat.descricao, "
                    + "cat.data_entrada,cat.data_cadastro, cat.status_emprestimo, cat.fk_designacao, des.genero, "
                    + "cat.fk_estratigrafia, est.formacao, cat.fk_colecao, col.nome "
                    + "FROM tb_catalogacao AS cat, tb_designacao AS des, tb_estratigrafia AS est, tb_colecao AS col "
                    + "WHERE cat.fk_designacao = des.id_designacao AND cat.fk_estratigrafia = est.id_estratigrafia "
                    + "AND cat.fk_colecao = col.id_colecao ";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                Colecao colecao = new Colecao(rs.getInt(18), rs.getString(19));
                Estratigrafia estratigrafia = new Estratigrafia(rs.getInt(16), rs.getString(17));
                Designacao designacao = new Designacao(rs.getInt(14), rs.getString(15));

                Catalogacao catalogacao = new Catalogacao(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(10),
                        rs.getString(11), rs.getInt(13) == 1, designacao, estratigrafia, colecao);

                dados.add(catalogacao);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar catalogação na base de dados! \n" + ex);
        }

        return dados;
        */
    }

    /**
     * Consultar total de visitantes cadastrados
     */
    public int total() {
        List<Catalogacao> lists = (List<Catalogacao>) new ModelDAO<Catalogacao>().lists(new Catalogacao());

        if(lists == null) {
            return 0;
        } else {
            return lists.size();
        }
        /*
        try {
            String sql = "SELECT count(id_catalogacao) FROM tb_catalogacao ";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery(sql);

            if (rs.next()) {
                return rs.getInt(1);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar total de catalogações na base de dados! \n" + ex);
        }
        */
    }

    /**
     * Consultar quantidade de designações por catalogações
     */
    public Map<String, String> designacoes() {
        List<Catalogacao> lists = (List<Catalogacao>) new ModelDAO<Catalogacao>().lists(new Catalogacao());

        Map<String, String> map = new TreeMap<>();

        for (Catalogacao c : lists) {
            map.put(c.getDesignacao().getGenero(), lists.size()+"");
        }

        if(lists == null) {
            return null;
        } else {
            return map;
        }
        /*
        Map<String, String> map = new TreeMap<>();

        try {
            String sql = "SELECT tb_designacao.genero AS designacao, (SELECT count(*) FROM tb_catalogacao " +
                    "WHERE tb_catalogacao.fk_designacao = tb_designacao.id_designacao) AS total FROM tb_designacao ORDER BY designacao";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                map.put(rs.getString(1), "" + rs.getInt(2));
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar quantidade de itens da catalogação por designação na base de dados! \n" + ex);
        }

        return map;
        */
    }

    /**
     * Consultar quantidade de coleções por catalogações
     */
    public Map<String, String> colecoes() {
        List<Catalogacao> lists = (List<Catalogacao>) new ModelDAO<Catalogacao>().lists(new Catalogacao());

        Map<String, String> map = new TreeMap<>();

        for (Catalogacao c : lists) {
            map.put(c.getDesignacao().getGenero(), lists.size()+"");
        }

        if(lists == null) {
            return null;
        } else {
            return map;
        }
        /*
        Map<String, String> map = new TreeMap<>();

        try {
            String sql = "SELECT tb_colecao.nome AS colecao, (SELECT count(*) FROM tb_catalogacao " +
                    "WHERE tb_catalogacao.fk_colecao = tb_colecao.id_colecao) AS total FROM tb_colecao ORDER BY colecao";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                map.put(rs.getString(1), "" + rs.getInt(2));
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar quantidade de itens da catalogação por coleção na base de dados! \n" + ex);
        }

        return map;
        */
    }

    /**
     * Consultar quantidade de designações por catalogações
     */
    public Map<String, String> estratigrafias() {
        List<Catalogacao> lists = (List<Catalogacao>) new ModelDAO<Catalogacao>().lists(new Catalogacao());

        Map<String, String> map = new TreeMap<>();

        for (Catalogacao c : lists) {
            map.put(c.getDesignacao().getGenero(), lists.size()+"");
        }

        if(lists == null) {
            return null;
        } else {
            return map;
        }
        /*
        Map<String, String> map = new TreeMap<>();

        try {
            String sql = "SELECT tb_estratigrafia.formacao AS estratigrafia, (SELECT count(*) FROM tb_catalogacao " +
                    "WHERE tb_catalogacao.fk_estratigrafia = tb_estratigrafia.id_estratigrafia) AS total FROM tb_estratigrafia ORDER BY estratigrafia";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                map.put(rs.getString(1), "" + rs.getInt(2));
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar quantidade de itens da catalogação por coleção na base de dados! \n" + ex);
        }

        return map;
        */
    }

    /**
     * Consultar catalogações cadastradas recentes
     */
    public Map<String, String> recentes() {
        List<Catalogacao> lists = (List<Catalogacao>) new ModelDAO<Catalogacao>().lists(new Catalogacao());

        Map<String, String> map = new TreeMap<>();

        for (Catalogacao c : lists) {
            map.put(c.getDesignacao().getGenero(), lists.size()+"");
        }

        if(lists == null) {
            return null;
        } else {
            return map;
        }
        /*
        Map<String, String> map = new TreeMap<>();

        try {
            String sql = "SELECT tb_catalogacao.numero_ordem, tb_designacao.genero FROM tb_catalogacao, tb_designacao "
                    + "WHERE  tb_catalogacao.fk_designacao = tb_designacao.id_designacao ORDER BY tb_catalogacao.data_cadastro DESC LIMIT 28 ";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                map.put(rs.getString(1), rs.getString(2));
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar quantidade de itens da catalogação recentementes na base de dados! \n" + ex);
        }

        return map;
        */
    }
}

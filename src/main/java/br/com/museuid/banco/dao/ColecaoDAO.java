package br.com.museuid.banco.dao;

import br.com.museuid.banco.controle.jpa.ModelDAO;
import br.com.museuid.model.Colecao;

import java.util.List;

/**
 * DAO responsável pela ações realizadas na base de dados referentes as coleções
 */
public class ColecaoDAO {

    /**
     * Inserir coleção na base de dados
     */
    public Colecao inserir(Colecao colecao) {
        return new ModelDAO<Colecao>().add(colecao);
        /*
        try {
            String sql = "INSERT INTO tb_colecao (nome, descricao) VALUES (?, ?)";

            stm = conector.prepareStatement(sql);

            stm.setString(1, colecao.getNome());
            stm.setString(2, colecao.getDescricao());

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao inserir coleções na base de dados! \n" + ex);
        }
        */
    }

    /**
     * Atualizar dados coleção na base de dados
     */
    public void editar(Colecao colecao) {
        new ModelDAO<Colecao>().update(colecao);
        /*
        try {
            String sql = "UPDATE tb_colecao SET nome=?, descricao=? WHERE id_colecao=?";

            stm = conector.prepareStatement(sql);

            stm.setString(1, colecao.getNome());
            stm.setString(2, colecao.getDescricao());
            stm.setInt(3, colecao.getId());

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao atualizar coleções na base de dados! \n" + ex);
        }
        */
    }

    /**
     * Excluir coleção da base de dados
     */
    public void excluir(int idColecao) {
        new ModelDAO<Colecao>().delete(new Colecao(idColecao));
        /*
        try {
            String sql = "DELETE FROM tb_colecao WHERE id_colecao=?";

            stm = conector.prepareStatement(sql);
            stm.setInt(1, idColecao);

            stm.execute();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao excluir coleções na base de dados! \n" + ex);
        }
        */
    }

    /**
     * Consultar todas coleções cadastrada na base de dados
     */
    public List<Colecao> listar() {
        return new ModelDAO<Colecao>().lists(new Colecao());

        /*
        List<Colecao> dados = new ArrayList<>();

        try {
            String sql = "SELECT * FROM tb_colecao ";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                Colecao colecao = new Colecao(rs.getInt(1), rs.getString(2), rs.getString(3));
                dados.add(colecao);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao atualizar coleções na base de dados! \n" + ex);
        }

        return dados;
        */
    }

    /**
     * Consultar coleções cadastradas na base de dados para criação de combos de coleções
     */
    public List<Colecao> combo() {
        return new ModelDAO<Colecao>().lists(new Colecao());
        /*
        List<Colecao> dadosColecoes = new ArrayList<>();

        try {
            String sql = "SELECT id_colecao, nome FROM tb_colecao ORDER BY nome";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                Colecao colecao = new Colecao(rs.getInt(1), rs.getString(2));
                dadosColecoes.add(colecao);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar coleções na base de dados! \n" + ex);
        }

        return dadosColecoes;
        */
    }

    /**
     * Consultar se nome da coleção já está cadastrado na base
     */
    public boolean isColecao(String nome, int id) {
        ModelDAO modelDAO = new ModelDAO<Colecao>();

        Colecao colecao = (Colecao) modelDAO.findSQL("from Colecao c where c.nome="+nome+" and c.id="+id);

        if(colecao == null) {
            return false;
        } else {
            return true;
        }

        /*
        try {
            String sql = "SELECT nome FROM tb_colecao WHERE nome =? AND id_colecao !=? ";

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
            Mensagem.erro("Erro ao validar nome da coleção na base de dados! \n" + ex);
        }

        return false;
        */
    }

    /**
     * Consultar total de estratigrafias cadastradas
     */
    public int total() {
        ModelDAO modelDAO = new ModelDAO<Colecao>();

        List<Colecao> colecaos = (List<Colecao>) modelDAO.lists(new Colecao());

        if(colecaos == null) {
            return 0;
        } else {
            return colecaos.size();
        }
        /*
        try {
            String sql = "SELECT COUNT(*) FROM tb_colecao";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar total de coleções cadastradas na base de dados! \n" + ex);
        }

        return 0;
        */
    }
}

package br.com.museuid.banco.dao;

import br.com.museuid.model.Organizacao;
import br.com.museuid.model.Setor;
import br.com.museuid.util.Mensagem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsável pela ações realizadas na base de dados referentes aos setores da localização
 */
public class SetorDAO extends DAO {

    public SetorDAO() {
        super();
    }

    /**
     * Inserir setor na base de dados
     */
    public void inserir(Setor setor) {
        try {
            String sql = "INSERT INTO  tb_setor (nome, descricao, fk_orgao) VALUES (?, ?, ?) ";

            stm = conector.prepareStatement(sql);

            stm.setString(1, setor.getNome());
            stm.setString(2, setor.getDescricao());
            stm.setInt(3, setor.getOrganizacao().getId());

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao inserir setor na base de dados! \n" + ex);
        }
    }

    /**
     * Atualizar dados setor na base de dados
     */
    public void editar(Setor setor) {
        try {
            String sql = "UPDATE  tb_setor SET nome=?, descricao=?, fk_orgao =? WHERE id_setor=? ";

            stm = conector.prepareStatement(sql);

            stm.setString(1, setor.getNome());
            stm.setString(2, setor.getDescricao());
            stm.setInt(3, setor.getOrganizacao().getId());

            stm.setInt(4, setor.getId());

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao atualizar setor na base de dados! \n" + ex);
        }
    }

    /**
     * Excluir setor na base de dados
     */
    public void excluir(int idSetor) {
        try {
            String sql = "DELETE FROM tb_setor WHERE id_setor=?";

            stm = conector.prepareStatement(sql);
            stm.setInt(1, idSetor);

            stm.execute();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao excluir setor na base de dados! \n" + ex);
        }
    }

    /**
     * Consultar todos setores cadastrados na base de dados
     */
    public List<Setor> listar() {

        List<Setor> dados = new ArrayList<>();

        try {
            String sql = "SELECT setor.*, org.nome FROM tb_setor AS setor, tb_organizacao AS org "
                    + "WHERE  setor.fk_orgao = org.id_orgao ";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                Organizacao org = new Organizacao(rs.getInt(4), rs.getString(5));
                Setor setor = new Setor(rs.getInt(1), rs.getString(2), rs.getString(3), org);
                dados.add(setor);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar setor na base de dados! \n" + ex);
        }

        return dados;
    }

    /**
     * Consultar todos setores cadastrados na base de dados exibição do combo de determinada organização
     */
    public List<Setor> combo(int organizacao) {

        List<Setor> dados = new ArrayList<>();

        try {
            String sql = "SELECT id_setor, nome FROM tb_setor WHERE fk_orgao = ? ORDER BY nome ";

            stm = conector.prepareStatement(sql);
            stm.setInt(1, organizacao);
            rs = stm.executeQuery();

            while (rs.next()) {
                Setor setor = new Setor(rs.getInt(1), rs.getString(2), "", new Organizacao(organizacao, ""));
                dados.add(setor);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar setor na base de dados! \n" + ex);
            ex.printStackTrace();
        }

        return dados;
    }

    /**
     * Consultar todos setores cadastrados na base de dados exibição do combo
     */
    public List<Setor> combo() {

        List<Setor> dados = new ArrayList<>();

        try {
            String sql = "SELECT id_setor, nome FROM tb_setor ORDER BY nome ";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                Setor setor = new Setor(rs.getInt(1), rs.getString(2), "", null);
                dados.add(setor);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar setor na base de dados! \n" + ex);
        }

        return dados;
    }
}

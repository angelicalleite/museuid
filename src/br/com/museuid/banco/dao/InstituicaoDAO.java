package br.com.museuid.banco.dao;

import br.com.museuid.model.Instituicao;
import br.com.museuid.util.Mensagem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsável pela ações realizadas na base de dados referentes as instituições cadastradas
 */
public class InstituicaoDAO extends DAO {

    public InstituicaoDAO() {
        super();
    }

    /**
     * Inserir instituição na base de dados
     */
    public void inserir(Instituicao instituicao) {
        try {
            String sql = "INSERT INTO tb_instituicao (nome, representante, telefone, cidade, estado, pais, descricao) VALUES (?, ?, ?, ?, ?, ?, ?)";

            stm = conector.prepareStatement(sql);

            stm.setString(1, instituicao.getNome());
            stm.setString(2, instituicao.getRepresentante());
            stm.setString(3, instituicao.getTelefone());
            stm.setString(4, instituicao.getCidade());
            stm.setString(5, instituicao.getEstado());
            stm.setString(6, instituicao.getPais());
            stm.setString(7, instituicao.getDescricao());

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao inserir instituição na base de dados! \n" + ex);
        }
    }

    /**
     * Atualizar dados instituição na base de dados
     */
    public void editar(Instituicao instituicao) {
        try {
            String sql = "UPDATE tb_instituicao SET nome=?, representante=?, telefone=?, cidade=?, estado=?, pais=?, descricao=? WHERE id_instituicao=? ";

            stm = conector.prepareStatement(sql);

            stm.setString(1, instituicao.getNome());
            stm.setString(2, instituicao.getRepresentante());
            stm.setString(3, instituicao.getTelefone());
            stm.setString(4, instituicao.getCidade());
            stm.setString(5, instituicao.getEstado());
            stm.setString(6, instituicao.getPais());
            stm.setString(7, instituicao.getDescricao());

            stm.setInt(8, instituicao.getId());

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao atualizar instituição na base de dados! \n" + ex);
        }
    }

    /**
     * Excluir instituição na base de dados
     */
    public void excluir(int idInstituicao) {
        try {
            String sql = "DELETE FROM tb_instituicao WHERE id_instituicao = ?";

            stm = conector.prepareStatement(sql);
            stm.setInt(1, idInstituicao);

            stm.execute();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao excluir instituição na base de dados! \n" + ex);
        }
    }

    /**
     * Listar todas instituições cadastradas na base de dados
     */
    public List<Instituicao> listar() {

        List<Instituicao> dadosInstituicao = new ArrayList<>();
        try {
            String sql = "SELECT * FROM tb_instituicao";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                Instituicao instituicao = new Instituicao(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
                dadosInstituicao.add(instituicao);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar instituições na base de dados! \n" + ex);
        }

        return dadosInstituicao;
    }

    /**
     * Listar todas instituições cadastradas na base de dados para exibição no combo
     */
    public List<Instituicao> combo() {

        List<Instituicao> dadosInstituicao = new ArrayList<>();

        try {
            String sql = "SELECT id_instituicao, nome FROM tb_instituicao ORDER BY nome";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                Instituicao instituicao = new Instituicao(rs.getInt(1), rs.getString(2));
                dadosInstituicao.add(instituicao);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar instituições na base de dados! \n" + ex);
        }

        return dadosInstituicao;
    }

    /**
     * Verificar se nome da instituição ja se encontra cadastrado
     */
    public boolean isInstituicao(String instituicao) {

        List<String> instituicoes = new ArrayList<>();

        try {
            String sql = "SELECT nome FROM tb_instituicao";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                String nome = rs.getString(1);
                instituicoes.add(nome);
            }

            for (String inst : instituicoes) {
                if (inst.toLowerCase().trim().equals(instituicao.toLowerCase().trim())) {
                    return true;
                }
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao verificar se instituição existe na base de dados! \n" + ex);
        }

        return false;
    }
}

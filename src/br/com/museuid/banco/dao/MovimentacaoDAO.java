package br.com.museuid.banco.dao;

import br.com.museuid.model.Movimentacao;
import br.com.museuid.util.Mensagem;
import br.com.museuid.util.Tempo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsável pela ações realizadas na base de dados referentes as movimentacao
 */
public class MovimentacaoDAO extends DAO {

    public MovimentacaoDAO() {
        super();
    }

    /**
     * Inserir movimentação na base de dados
     */
    public void inserir(Movimentacao movimentacao) {
        try {
            String sql = "INSERT INTO tb_movimentacao (objetos, responsavel, local_origem, local_atual, tipo, descricao, data_criacao) VALUES ( ?, ?, ?, ?, ?, ?, ?)";

            stm = conector.prepareStatement(sql);

            stm.setString(1, movimentacao.getObjetos());
            stm.setString(2, movimentacao.getResponsavel());
            stm.setString(3, movimentacao.getOrigem());
            stm.setString(4, movimentacao.getDestino());
            stm.setString(5, movimentacao.getTipo());
            stm.setString(6, movimentacao.getDescricao());
            stm.setTimestamp(7, Tempo.toTimestamp(movimentacao.getData()));

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao inserir movimentação na base de dados! \n" + ex);
        }
    }

    /**
     * Atualizar dados movimentação na base de dados
     */
    public void editar(Movimentacao movimentacao) {
        try {
            String sql = "UPDATE tb_movimentacao SET objetos = ? , responsavel = ? , local_origem = ? , local_atual = ? , tipo = ? , descricao = ? , data_criacao = ?  WHERE id_movimentacao = ? ";

            stm = conector.prepareStatement(sql);

            stm.setString(1, movimentacao.getObjetos());
            stm.setString(2, movimentacao.getResponsavel());
            stm.setString(3, movimentacao.getOrigem());
            stm.setString(4, movimentacao.getDestino());
            stm.setString(5, movimentacao.getTipo());
            stm.setString(6, movimentacao.getDescricao());
            stm.setTimestamp(7, Tempo.toTimestamp(movimentacao.getData()));

            stm.setInt(8, movimentacao.getId());

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao atualizar movimentação na base de dados! \n" + ex);
        }
    }

    /**
     * Excluir movimentação na base de dados
     */
    public void excluir(int idMovimentacao) {
        try {
            String sql = "DELETE FROM tb_movimentacao WHERE id_movimentacao = ? ";

            stm = conector.prepareStatement(sql);
            stm.setInt(1, idMovimentacao);

            stm.execute();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao excluir movimentação na base de dados! \n" + ex);
        }
    }

    /**
     * Consultar todas movimentações cadastradas na base de dados
     */
    public List<Movimentacao> listar() {

        List<Movimentacao> dados = new ArrayList<>();

        try {
            String sql = "SELECT * FROM tb_movimentacao ";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                Movimentacao movimentacao = new Movimentacao(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), Tempo.toDate(rs.getTimestamp(8)));
                dados.add(movimentacao);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar movimentação na base de dados! \n" + ex);
        }

        return dados;
    }

}

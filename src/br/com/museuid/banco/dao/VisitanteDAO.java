package br.com.museuid.banco.dao;

import br.com.museuid.model.Visitante;
import br.com.museuid.util.Mensagem;
import br.com.museuid.util.Tempo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsável pela ações realizadas na base de dados referentes aos visitantes
 */
public class VisitanteDAO extends DAO {

    public VisitanteDAO() {
        super();
    }

    /**
     * Inserir visitante na base de dados
     */
    public void inserir(Visitante visitante) {
        try {
            String sql = "INSERT INTO tb_visitantes (nome, funcao, cidade, estado, pais, data_visita, descricao, tipo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            stm = conector.prepareStatement(sql);

            stm.setString(1, visitante.getNome());
            stm.setString(2, visitante.getFuncao());
            stm.setString(3, visitante.getCidade());
            stm.setString(4, visitante.getEstado());
            stm.setString(5, visitante.getPais());
            stm.setTimestamp(6, Tempo.toTimestamp(visitante.getDataVisita()));
            stm.setString(7, visitante.getDescricao());
            stm.setString(8, visitante.getTipo());

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao inserir visitante na base de dados! \n" + ex);
        }
    }

    /**
     * Atualizar dados de visitante na base de dados
     */
    public void editar(Visitante visitante) {
        try {
            String sql = "UPDATE tb_visitantes SET nome=?, funcao=?, cidade=?, estado=?, pais=?, data_visita=?, descricao=?, tipo=? WHERE id_visitante=? ";

            stm = conector.prepareStatement(sql);

            stm.setString(1, visitante.getNome());
            stm.setString(2, visitante.getFuncao());
            stm.setString(3, visitante.getCidade());
            stm.setString(4, visitante.getEstado());
            stm.setString(5, visitante.getPais());
            stm.setTimestamp(6, Tempo.toTimestamp(visitante.getDataVisita()));
            stm.setString(7, visitante.getDescricao());
            stm.setString(8, visitante.getTipo());

            stm.setInt(9, visitante.getId());

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao atualizar visitante na base de dados! \n" + ex);
        }
    }

    /**
     * Excluir visitante na base de dados
     */
    public void excluir(int idVisitante) {
        try {
            String sql = "DELETE FROM tb_visitantes WHERE id_visitante=?";

            stm = conector.prepareStatement(sql);
            stm.setInt(1, idVisitante);

            stm.execute();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao excluir visitante na base de dados! \n" + ex);
        }
    }

    /**
     * Consultar todos visitantes cadastrdos na base de dados
     */
    public List<Visitante> listar() {

        List<Visitante> dados = new ArrayList<>();
        try {
            String sql = "SELECT tb_visitantes.* FROM tb_visitantes ";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                dados.add(new Visitante(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), Tempo.toDate(rs.getTimestamp(7)), rs.getString(8), rs.getString(9)));
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar visitantes na base de dados! \n" + ex);
        }

        return dados;
    }

    /**
     * Consultar total de visitantes cadastrados
     */
    public int total() {
        try {
            String sql = "SELECT count(id_visitante) FROM tb_visitantes ";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery(sql);

            if (rs.next()) {
                return rs.getInt(1);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar total de visitantes na base de dados! \n" + ex);
        }

        return 0;
    }
}

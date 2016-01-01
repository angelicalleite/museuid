package br.com.museuid.banco.dao;

import br.com.museuid.model.Excursao;
import br.com.museuid.model.Instituicao;
import br.com.museuid.util.Mensagem;
import br.com.museuid.util.Tempo;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsável pela ações realizadas na base de dados referentes as excursões
 */
public class ExcursaoDAO extends DAO {

    public ExcursaoDAO() {
        super();
    }

    /**
     * Inserir excursão na base de dados
     */
    public void inserir(Excursao excursao) {
        try {
            String sql = "INSERT INTO tb_excursao (curso, participantes, responsavel, contato, guias, horario, data_visita, descricao, agendar, status_agenda, fk_instituticao) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

            stm = conector.prepareStatement(sql);

            stm.setString(1, excursao.getCurso());
            stm.setInt(2, excursao.getParticipantes());
            stm.setString(3, excursao.getResponsavel());
            stm.setString(4, excursao.getContato());
            stm.setString(5, excursao.getGuias());
            stm.setString(6, excursao.getHorario());
            stm.setTimestamp(7, Tempo.toTimestamp(excursao.getData()));
            stm.setString(8, excursao.getDescricao());
            stm.setInt(9, excursao.isAgendamento() ? 1 : 0);
            stm.setString(10, excursao.getStatusAgendamento());
            stm.setInt(11, excursao.getInstituicao().getId());

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao inserir excursão na base de dados! \n" + ex);
        }
    }

    /**
     * Atualizar dados excursão na base de dados
     */
    public void editar(Excursao excursao) {
        try {
            String sql = "UPDATE  tb_excursao SET curso= ?, participantes= ?, responsavel= ?, contato= ?, guias= ?, horario= ?, data_visita= ?, "
                    + "descricao= ?, agendar= ?, status_agenda= ?, fk_instituticao= ? WHERE id_excursao= ? ";

            stm = conector.prepareStatement(sql);

            stm.setString(1, excursao.getCurso());
            stm.setInt(2, excursao.getParticipantes());
            stm.setString(3, excursao.getResponsavel());
            stm.setString(4, excursao.getContato());
            stm.setString(5, excursao.getGuias());
            stm.setString(6, excursao.getHorario());
            stm.setTimestamp(7, Tempo.toTimestamp(excursao.getData()));
            stm.setString(8, excursao.getDescricao());
            stm.setInt(9, excursao.isAgendamento() ? 1 : 0);
            stm.setString(10, excursao.getStatusAgendamento());
            stm.setInt(11, excursao.getInstituicao().getId());

            stm.setInt(12, excursao.getId());

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao editar excursão na base de dados! \n" + ex);
        }
    }

    /**
     * Excluir excursão na base de dados
     */
    public void excluir(int idExcursao) {
        try {
            String sql = "DELETE FROM tb_excursao WHERE id_excursao=?";

            stm = conector.prepareStatement(sql);
            stm.setInt(1, idExcursao);

            stm.execute();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao excluir excursão na base de dados! \n" + ex);
        }
    }

    /**
     * Consultar todas excursões cadastrdas
     */
    public List<Excursao> listar() {

        List<Excursao> dados = new ArrayList<>();

        try {
            String sql = "SELECT excursao.*, instituicao.nome FROM tb_excursao AS excursao, tb_instituicao AS instituicao "
                    + "WHERE excursao.fk_instituticao = instituicao.id_instituicao ";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                Instituicao instituicao = new Instituicao(rs.getInt(12), rs.getString(13));
                Excursao excursao = new Excursao(rs.getInt(1), rs.getString(2), rs.getInt(3),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
                        Tempo.toDate(rs.getTimestamp(8)), rs.getString(9), rs.getInt(10) == 1, rs.getString(11), instituicao);

                dados.add(excursao);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar excursões na base de dados! \n" + ex);
        }

        return dados;
    }

    /**
     * Consultar todas excursões cadastrdas
     */
    public List<Excursao> acompanhamento(Timestamp data) {

        List<Excursao> dados = new ArrayList<>();

        try {
            String sql = "SELECT e.id_excursao, e.curso, e.participantes, e.responsavel, e.horario,  e.data_visita, e.agendar, "
                    + "e.status_agenda, i.id_instituicao, i.nome FROM tb_excursao AS e, tb_instituicao AS i "
                    + "WHERE e.fk_instituticao = i.id_instituicao ORDER BY e.data_visita LIMIT 5 ";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                Excursao excursao = new Excursao(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), "", "", rs.getString(5),
                        Tempo.toDate(rs.getTimestamp(6)), "", rs.getInt(7) == 1, rs.getString(8), new Instituicao(rs.getInt(9), rs.getString(10)));

                dados.add(excursao);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar excursões para acompanhamento na base de dados! \n" + ex);
        }

        return dados;
    }
}

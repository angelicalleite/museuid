package br.com.museuid.banco.dao;

import br.com.museuid.model.Auditoria;
import br.com.museuid.model.Usuario;
import br.com.museuid.util.Mensagem;
import br.com.museuid.util.Tempo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO respons�vel pela a��es realizadas na base de dados referentes aos logs da aplica��o
 */
public class AuditoriaDAO extends DAO {

    public AuditoriaDAO() {
        super();
    }

    /**
     * Inserir nova ação(log) realizada pelo usu�rio
     */
    public void inserir(Auditoria log) {
        try {
            String sql = "INSERT INTO tb_auditoria (acao, data, descricao, fk_usuario) VALUES (?, ?, ?, ?);";

            stm = conector.prepareStatement(sql);

            stm.setString(1, log.getAcao());
            stm.setTimestamp(2, Tempo.toTimestamp(log.getData()));
            stm.setString(3, log.getDescricao());
            stm.setInt(4, log.getUser().getId());

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao inserir logs na base de dados! \n" + ex);
        }
    }

    /**
     * Excluir log de acordo com o identificador informado
     */
    public void excluir(int id) {
        try {
            String sql = "DELETE FROM tb_auditoria WHERE id_auditoria = ?";

            stm = conector.prepareStatement(sql);
            stm.setInt(1, id);

            stm.execute();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao excluir logs na base de dados! \n" + ex);
        }
    }

    /**
     * Listar todos os logs de um determinado usuario
     */
    public List<Auditoria> logsUsuario(int id) {

        List<Auditoria> dados = new ArrayList<>();

        try {
            String sql = "SELECT log.id_auditoria, log.acao, log.data, log.descricao, usuario.nome FROM tb_auditoria AS log, tb_usuario AS usuario " +
                    "WHERE log.fk_usuario = usuario.id_usuario AND log.fk_usuario = ? ";

            stm = conector.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                Auditoria logs = new Auditoria(rs.getInt(1), rs.getString(2), Tempo.toDate(rs.getTimestamp(3)), rs.getString(4), null);
                logs.setUser(new Usuario(rs.getInt(5), rs.getString(6)));
                dados.add(logs);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar logs na base de dados! \n" + ex);
        }

        return dados;
    }

    /**
     * Listar todos os logs cadastrados
     */
    public List<Auditoria> logs() {

        List<Auditoria> dados = new ArrayList<>();
        try {
            String sql = "SELECT log.id_auditoria, log.acao, log.data, log.descricao , usuario.nome "
                    + "FROM tb_auditoria AS log, tb_usuario AS usuario WHERE log.fk_usuario = usuario.id_usuario ";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                Auditoria logs = new Auditoria(rs.getInt(1), rs.getString(2), Tempo.toDate(rs.getTimestamp(3)), rs.getString(4), null);
                logs.setUser(new Usuario(rs.getInt(5), rs.getString(6)));
                dados.add(logs);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar logs na base de dados! \n" + ex);
        }

        return dados;
    }
}

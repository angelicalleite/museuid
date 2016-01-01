package br.com.museuid.banco.dao;

import br.com.museuid.model.TipoUsuario;
import br.com.museuid.model.Usuario;
import br.com.museuid.util.Criptografia;
import br.com.museuid.util.Mensagem;
import br.com.museuid.util.Tempo;

import java.sql.SQLException;

/**
 * DAO responsável pela ações realizadas na base de dados referentes as login do usuário
 */
public class LoginDAO extends DAO {

    public LoginDAO() {
        super();
    }

    /**
     * Autenticar e validar nome do usuário informado
     */
    public boolean autenticarUsername(String nome) {

        try {
            String sql = "SELECT login FROM tb_usuario WHERE login=? AND status = 1 ";

            stm = conector.prepareStatement(sql);
            stm.setString(1, nome);
            rs = stm.executeQuery();

            if (rs.next()) {
                return nome.equals(rs.getString(1));
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao autenticar nome usuário na base de dados! \n" + ex);
        }

        return false;
    }

    /**
     * Autenticar e validar senha do usuário informada
     */
    public boolean autenticarSenha(String nome, String senha) {

        String chave = Criptografia.converter(senha);

        try {
            String sql = "SELECT login, senha FROM tb_usuario WHERE login=? AND senha=? ";

            stm = conector.prepareStatement(sql);
            stm.setString(1, nome);
            stm.setString(2, chave);
            rs = stm.executeQuery();

            while (rs.next()) {
                return rs.getString(1).equals(nome) && rs.getString(2).equals(chave);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao autenticar senha usuário na base de dados! \n" + ex);
        }

        return false;
    }

    /**
     * Consultar informações do usuário logado na base de dados
     */
    public Usuario usuarioLogado(String login) {

        Usuario user = null;

        try {
            String sql = "SELECT usuario.id_usuario, usuario.nome, usuario.login, usuario.senha, usuario.email, usuario.status, usuario.data_criacao, usuario.descricao, tipo.id_tipo_usuario, tipo.nome "
                    + "FROM tb_usuario AS usuario , tb_tipo_usuario AS tipo "
                    + "WHERE usuario.login=? "
                    + "AND tipo.id_tipo_usuario = usuario.fk_tipo_usuario";

            stm = conector.prepareStatement(sql);
            stm.setString(1, login);
            rs = stm.executeQuery();

            while (rs.next()) {
                TipoUsuario tipo = new TipoUsuario(rs.getInt(9), rs.getString(10));
                user = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6) == 1 ? true : false, null, rs.getString(8), tipo);
                user.setDataCriacao(Tempo.toDate(rs.getTimestamp(7)));
            }
            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar usuário logado na base de dados! \n" + ex);
        }

        return user;
    }
}

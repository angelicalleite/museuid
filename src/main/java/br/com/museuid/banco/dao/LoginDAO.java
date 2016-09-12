package br.com.museuid.banco.dao;

import br.com.museuid.banco.controle.jpa.ModelDAO;
import br.com.museuid.banco.controle.jpa.ModelSessionFactory;
import br.com.museuid.model.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * DAO responsável pela ações realizadas na base de dados referentes as login do usuário
 */
public class LoginDAO {

    private static final Logger log = LoggerFactory.getLogger(LoginDAO.class);

    /**
     * Autenticar e validar nome do usuário informado
     */
    public boolean autenticarUsername(String nome) {
        ModelDAO modelDAO = new ModelDAO<Usuario>();
        EntityManager session = modelDAO.getSession();
        List<Usuario> usuarios = null;
        try {
            usuarios = (List<Usuario>) session.createQuery(
                        "SELECT u FROM Usuario u WHERE u.nome= :nome AND status =:status ")
                        .setParameter("nome", nome)
                        .setParameter("status", true)
                        .getResultList();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            session.close();
        }

        if(usuarios == null) {
            return false;
        } else {
            return true;
        }
        /*
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
        */
    }

    /**
     * Autenticar e validar senha do usuário informada
     */
    public boolean autenticarSenha(String nome, String senha) {
        ModelDAO modelDAO = new ModelDAO<Usuario>();
        EntityManager session = modelDAO.getSession();
        Usuario usuario  = null;

        try {
            usuario = (Usuario) session.createQuery(
                    "SELECT u FROM Usuario u WHERE u.nome = :nome AND u.senha = :senha ")
                    .setParameter("nome", nome)
                    .setParameter("senha", senha)
                    .getSingleResult();

        } catch (NoResultException ex) {
            log.info(ex.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            session.close();
        }

        if(usuario == null) {
            return false;
        } else {
            return true;
        }
        /*
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
        */
    }

    /**
     * Consultar informações do usuário logado na base de dados
     */
    public Usuario usuarioLogado(String login) {
        ModelDAO modelDAO = new ModelDAO<Usuario>();
        EntityManager session = modelDAO.getSession();
        Usuario usuario  = null;

        try {
            usuario = (Usuario) session.createQuery(
                    "SELECT u FROM Usuario u WHERE u.login= :login ")
                    .setParameter("login", login)
                    .getSingleResult();

        } catch (NoResultException ex) {
            log.info(ex.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            session.close();
        }

        return usuario;

        /*
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
        */
    }
}

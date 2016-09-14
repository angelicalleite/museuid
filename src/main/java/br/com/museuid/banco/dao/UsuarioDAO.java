package br.com.museuid.banco.dao;

import br.com.museuid.banco.controle.jpa.ModelDAO;
import br.com.museuid.banco.controle.jpa.ModelSessionFactory;
import br.com.museuid.model.Instituicao;
import br.com.museuid.model.TipoUsuario;
import br.com.museuid.model.Usuario;
import br.com.museuid.util.Mensagem;
import br.com.museuid.util.Tempo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsável pela ações realizadas na base de dados referentes aos usuários
 */
public class UsuarioDAO {

    /**
     * Inserir usuário na base de dados
     */
    public Usuario inserir(Usuario usuario) {
         return new ModelDAO<Usuario>().add(usuario);
        /*
        try {
            String sql = "INSERT INTO tb_usuario ( nome, login, senha, email, status, descricao, data_criacao, fk_tipo_usuario ) VALUES (?, ?, ?, ?, ?, ?, now(),?)";

            stm = conector.prepareStatement(sql);

            stm.setString(1, usuario.getNome());
            stm.setString(2, usuario.getLogin());
            stm.setString(3, usuario.getSenha());
            stm.setString(4, usuario.getEmail());
            stm.setInt(5, usuario.isStatus() ? 1 : 0);
            stm.setString(6, usuario.getDescricao());
            stm.setInt(7, usuario.getTipoUsuario().getId());

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao inserir usuário na base de dados! \n" + ex);
        }*/
    }

    /**
     * Atualizar dados usuário na base de dados
     */
    public void editar(Usuario usuario) {
        new ModelDAO<Usuario>().update(usuario);
        /*
        try {
            String sql = "UPDATE tb_usuario SET nome =?, login =?, senha =?, email =?, status =?, descricao =?, fk_tipo_usuario =? WHERE id_usuario =?";

            stm = conector.prepareStatement(sql);

            stm.setString(1, usuario.getNome());
            stm.setString(2, usuario.getLogin());
            stm.setString(3, usuario.getSenha());
            stm.setString(4, usuario.getEmail());
            stm.setInt(5, usuario.isStatus() ? 1 : 0);
            stm.setString(6, usuario.getDescricao());
            stm.setInt(7, usuario.getTipoUsuario().getId());

            stm.setInt(8, usuario.getId());

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao atualizar usuário na base de dados! \n" + ex);
        }*/
    }

    /**
     * Excluir usuário na base de dados
     */
    public void excluir(int idUsuario) {
        new ModelDAO<Usuario>().delete(new Usuario(idUsuario));
        /*
        try {
            String sql = "DELETE FROM tb_usuario WHERE id_usuario=?";

            stm = conector.prepareStatement(sql);

            stm.setInt(1, idUsuario);
            stm.execute();

            stm.close();
        } catch (SQLException ex) {
            Mensagem.erro("Erro ao excluir usuário na base de dados! \n" + ex);
        }*/
    }

    /**
     * Consultar todos usuários cadastrados na base de dados
     */
    public List<Usuario> listar() {
        ModelDAO modelDAO = new ModelDAO<Usuario>();

        String sql = " FROM Usuario u, TipoUsuario t "
                + "WHERE u.tipoUsuario.id = t.id ";

        List<Usuario> usuarios = (List) modelDAO.findSQL(sql);

        if(usuarios == null) {
            return null;
        } else {
            return usuarios;
        }
        /*
        List<Usuario> usuarios = new ArrayList<>();

        try {
            String sql = "SELECT tb_usuario.*, tb_tipo_usuario.nome FROM tb_usuario, tb_tipo_usuario "
                    + "WHERE tb_usuario.fk_tipo_usuario = tb_tipo_usuario.id_tipo_usuario ";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                TipoUsuario tipo = new TipoUsuario(rs.getInt(9), rs.getString(10));
                Usuario user = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6) == 1 ? true : false, Tempo.toDate(rs.getTimestamp(8)), rs.getString(7), tipo);

                usuarios.add(user);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar usuários na base de dados! \n" + ex);
        }

        return usuarios;*/
    }

    /**
     * Consultar todos usuários cadastrados de um determinado tipo
     */
    public List<TipoUsuario> usuariosTipo() {
        ModelDAO modelDAO = new ModelDAO<TipoUsuario>();

        String sql = " FROM TipoUsuario ";

        List<TipoUsuario> tipoUsuarios = (List) modelDAO.findSQL(sql);

        if(tipoUsuarios == null) {
            return null;
        } else {
            return tipoUsuarios;
        }
        /*
        List<TipoUsuario> tipos = new ArrayList<>();

        try {
            String sql = "SELECT * FROM tb_tipo_usuario ";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                TipoUsuario tipo = new TipoUsuario(rs.getInt(1), rs.getString(2));
                tipos.add(tipo);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar usuários na base de dados! \n" + ex);
        }

        return tipos;*/
    }

    /**
     * Verificar se usuário já está registrado na base de dados atravês do login
     */
    public boolean isUsuario(int id, String nome) {
        ModelDAO modelDAO = new ModelDAO<TipoUsuario>();

        String sql = " FROM Usuario u WHERE u.login = "+nome+" AND u.id != "+id;

        List<TipoUsuario> tipoUsuarios = (List) modelDAO.findSQL(sql);

        if(tipoUsuarios == null) {
            return false;
        } else {
            return true;
        }
        /*
        String login = nome.replaceAll(" ", "").trim().toLowerCase();

        try {
            String sql = "SELECT login FROM tb_usuario WHERE login = ? AND id_usuario != ? ";

            stm = conector.prepareStatement(sql);
            stm.setString(1, nome);
            stm.setInt(2, id);
            rs = stm.executeQuery();

            if (rs.next()) {
                String usuario = rs.getString(1);
                return usuario.trim().equalsIgnoreCase(login);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao verificar na base de dados login usuário \n" + ex);
        }

        return false;*/
    }
}

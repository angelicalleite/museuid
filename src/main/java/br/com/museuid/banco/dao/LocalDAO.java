package br.com.museuid.banco.dao;

import br.com.museuid.banco.controle.jpa.ModelDAO;
import br.com.museuid.model.Local;

import java.util.List;

/**
 * DAO responsável pela ações realizadas na base de dados referentes as local da localização
 */
public class LocalDAO  {

    /**
     * Inserir local na base de dados
     */
    public Local inserir(Local local) {
        return new ModelDAO<Local>().add(local);
        /*
        try {
            String sql = "INSERT INTO tb_local (nome, descricao, fk_setor) VALUES (?, ?, ?) ";

            stm = conector.prepareStatement(sql);

            stm.setString(1, local.getNome());
            stm.setString(2, local.getDescricao());
            stm.setInt(3, local.getSetor().getId());

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao inserir local na base de dados! \n" + ex);
        }*/
    }

    /**
     * Atualizar dados local na base de dados
     */
    public void editar(Local local) {
        new ModelDAO<Local>().update(local);
        /*
        try {
            String sql = "UPDATE tb_local SET nome=?, descricao=?, fk_setor=? WHERE id_local=? ";

            stm = conector.prepareStatement(sql);

            stm.setString(1, local.getNome());
            stm.setString(2, local.getDescricao());
            stm.setInt(3, local.getSetor().getId());

            stm.setInt(4, local.getId());

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao atualizar local na base de dados! \n" + ex);
        }*/
    }

    /**
     * Excluir local na base de dados
     */
    public void excluir(int idLocal) {
        new ModelDAO<Local>().delete(new Local(idLocal));
        /*
        try {
            String sql = "DELETE FROM tb_local WHERE id_local=?";

            stm = conector.prepareStatement(sql);
            stm.setInt(1, idLocal);

            stm.execute();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao excluir local na base de dados! \n" + ex);
        }*/
    }

    /**
     * Consultar todos locais cadastrados na base de dados
     */
    public List<Local> listar() {
        ModelDAO modelDAO = new ModelDAO<Local>();

        String sql = " FROM Local, Setor s WHERE  local.setor.id =  s.id ";

        List<Local> catalogacaos = (List) modelDAO.findSQL(sql);

        if(catalogacaos == null) {
            return null;
        } else {
            return catalogacaos;
        }
        /*
        List<Local> dados = new ArrayList<>();

        try {
            String sql = "SELECT local.*, setor.nome FROM tb_local AS local, tb_setor AS setor WHERE  local.fk_setor =  setor.id_setor ";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                Setor setor = new Setor(rs.getInt(4), rs.getString(5));
                Local local = new Local(rs.getInt(1), rs.getString(2), rs.getString(3), setor);
                dados.add(local);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar local na base de dados! \n" + ex);
        }

        return dados;*/
    }

    /**
     * Consultar todos locais cadastrados na base de dados para exibição no combo de um determinado setor
     */
    public List<Local> combo(int setor) {
        ModelDAO modelDAO = new ModelDAO<Local>();

        String sql = " FROM Local l WHERE l.setor.id = "+setor+" ORDER BY l.nome";

        List<Local> catalogacaos = (List) modelDAO.findSQL(sql);

        if(catalogacaos == null) {
            return null;
        } else {
            return catalogacaos;
        }
        /*
        List<Local> dados = new ArrayList<>();

        try {
            String sql = "SELECT id_local, nome FROM tb_local WHERE fk_setor = ? ORDER BY nome";

            stm = conector.prepareStatement(sql);
            stm.setInt(1, setor);
            rs = stm.executeQuery();

            while (rs.next()) {
                Local local = new Local(rs.getInt(1), rs.getString(2), "", new Setor(setor));
                dados.add(local);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar local na base de dados! \n" + ex);
        }

        return dados;*/
    }
}

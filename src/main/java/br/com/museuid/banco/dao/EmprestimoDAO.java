package br.com.museuid.banco.dao;

import br.com.museuid.banco.controle.jpa.ModelDAO;
import br.com.museuid.model.*;

import java.time.LocalDate;
import java.util.List;

/**
 * DAO responsável pela ações realizadas na base de dados referentes a empréstimos
 */
public class EmprestimoDAO {

    /**
     * Inserir empréstimo
     */
    public Emprestimo inserir(Emprestimo emprestimo) {
        return new ModelDAO<Emprestimo>().add(emprestimo);
        /*
        try {
            String sql = "INSERT INTO tb_emprestimo (numero_emprestimo, solicitante, cpf, rg, telefone, email, responsavel, status_emprestimo, data_emprestimo, data_devolucao, descricao, fk_instituicao) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            stm = conector.prepareStatement(sql);

            stm.setString(1, emprestimo.getNumeroEmprestimo());
            stm.setString(2, emprestimo.getSolicitante());
            stm.setString(3, emprestimo.getCpf());
            stm.setString(4, emprestimo.getRg());
            stm.setString(5, emprestimo.getContato());
            stm.setString(6, emprestimo.getEmail());
            stm.setString(7, emprestimo.getResponsavel());
            stm.setString(8, emprestimo.getStatus());
            stm.setTimestamp(9, Tempo.toTimestamp(emprestimo.getDataEmprestimmo()));
            stm.setTimestamp(10, Tempo.toTimestamp(emprestimo.getDataDevolucao()));
            stm.setString(11, emprestimo.getDescricao());
            stm.setInt(12, emprestimo.getInstituicao().getId());

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao inserir na base de dados empréstimos! \n" + ex);
        }
        */
    }

    /**
     * Atualizar dados empréstimo
     */
    public void editar(Emprestimo emprestimo) {
        new ModelDAO<Emprestimo>().update(emprestimo);
        /*
        try {
            String sql = "UPDATE tb_emprestimo SET numero_emprestimo= ?, solicitante= ?, cpf= ?, rg= ?, telefone= ?, email= ?, "
                    + "responsavel= ?, status_emprestimo= ?, data_emprestimo= ?, data_devolucao= ?, descricao= ?, fk_instituicao= ?  WHERE id_emprestimo= ? ";

            stm = conector.prepareStatement(sql);

            stm.setString(1, emprestimo.getNumeroEmprestimo());
            stm.setString(2, emprestimo.getSolicitante());
            stm.setString(3, emprestimo.getCpf());
            stm.setString(4, emprestimo.getRg());
            stm.setString(5, emprestimo.getContato());
            stm.setString(6, emprestimo.getEmail());
            stm.setString(7, emprestimo.getResponsavel());
            stm.setString(8, emprestimo.getStatus());
            stm.setTimestamp(9, Tempo.toTimestamp(emprestimo.getDataEmprestimmo()));
            stm.setTimestamp(10, Tempo.toTimestamp(emprestimo.getDataDevolucao()));
            stm.setString(11, emprestimo.getDescricao());
            stm.setInt(12, emprestimo.getInstituicao().getId());

            stm.setInt(13, emprestimo.getId());

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao atualizar na base de dados empréstimos! \n" + ex);
        }
        */
    }

    /**
     * Excluir emprestimo informado
     */
    public void excluir(int id) {
        new ModelDAO<Emprestimo>().delete(new Emprestimo(id));
        /*
        try {
            String sql = "DELETE FROM tb_emprestimo WHERE id_emprestimo=? ";

            stm = conector.prepareStatement(sql);
            stm.setInt(1, id);

            stm.execute();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao excluir na base de dados empréstimos! \n" + ex);
        }
        */
    }

    /**
     * Verificar se número de empréstimo já esta cadastrado na base de dados
     */
    public boolean isEmprestimo(String numero, int id) {
        ModelDAO modelDAO = new ModelDAO<Emprestimo>();

        Emprestimo emprestimo = (Emprestimo) modelDAO.findSQL("from Emprestimo e where e.numero="+numero+" and e.id="+id);

        if(emprestimo == null) {
            return false;
        } else {
            return true;
        }
        /*
        try {
            String sql = "SELECT numero_emprestimo FROM tb_emprestimo WHERE numero_emprestimo = ? AND id_emprestimo != ? ";

            stm = conector.prepareStatement(sql);
            stm.setString(1, numero);
            stm.setInt(2, id);
            rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getString(1).toLowerCase().trim().equals(numero.toLowerCase().trim());
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao validar número empréstimo na base de dados empréstimos! \n" + ex);
        }

        return false;
        */
    }

    /**
     * Atualiza dados do empréstimo na base de dados para realização da devolução mudando suas data de entrega, estado e observações
     */
    public void devolucao(int emprestimo, LocalDate dataEntrega, String observacoes) {
        Emprestimo  emprestimo1 = new Emprestimo();
        emprestimo1.setId(emprestimo);
        emprestimo1.setDataEntrega(dataEntrega);
        emprestimo1.setObservacoes(observacoes);
        new ModelDAO<Emprestimo>().update(emprestimo1);
        /*
        try {
            String sql = "UPDATE tb_emprestimo SET data_entregue =?, observacoes =? , status_emprestimo ='Entregue' WHERE id_emprestimo=? ";

            stm = conector.prepareStatement(sql);
            stm.setTimestamp(1, Tempo.toTimestamp(dataEntrega));
            stm.setString(2, observacoes);
            stm.setInt(3, emprestimo);

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao atualizar status catalogação na base de dados! \n" + ex);
        }
        */
    }

    /**
     * Adicionar item ao empréstimo
     */
    public void addItem(EmprestimoItem item) {
        new ModelDAO<EmprestimoItem>().add(item);
        /*
        try {
            String sql = "INSERT INTO tb_emprestimo_item (conservacao, fk_catalogacao, fk_emprestimo) VALUES (?, ?, ?)";

            stm = conector.prepareStatement(sql);

            stm.setString(1, item.getConservacao());
            stm.setInt(2, item.getCatalogacao().getId());
            stm.setInt(3, item.getEmprestimo().getId());

            stm.executeUpdate();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao adicionar item empréstimo na base de dados! \n" + ex);
        }
        */
    }

    /**
     * Excluir item do empréstimo
     */
    public void excluirItem(int item) {
        new ModelDAO<Emprestimo>().add(new Emprestimo(item));
        /*
        try {
            String sql = "DELETE FROM tb_emprestimo_item WHERE id_emprestimo_item=? ";

            stm = conector.prepareStatement(sql);
            stm.setInt(1, item);

            stm.execute();
            stm.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao excluir item empréstimo na base de dados! \n" + ex);
        }
        */
    }

    /**
     * Consultar empréstimo já concluídos
     */
    public List<Emprestimo> historico() {
        ModelDAO modelDAO = new ModelDAO<Emprestimo>();

        List<Emprestimo> emprestimos = (List<Emprestimo>) modelDAO.findSQL("FROM Emprestimo e where e.instituicao.id == e.instituicao.id and e.status='Entregue'");

        if(emprestimos == null) {
            return null;
        } else {
            return emprestimos;
        }
        /*
        List<Emprestimo> dados = new ArrayList<>();

        try {
            String sql = "SELECT tb_emprestimo.*, tb_instituicao.nome FROM tb_emprestimo, tb_instituicao "
                    + "WHERE tb_emprestimo.fk_instituicao = tb_instituicao.id_instituicao "
                    + "AND tb_emprestimo.status_emprestimo = 'Entregue' ";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                Emprestimo emprestimmo = new Emprestimo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), Tempo.toDate(rs.getTimestamp(10)),
                        Tempo.toDate(rs.getTimestamp(11)), rs.getString(13), rs.getString(14), new Instituicao(rs.getInt(15), rs.getString(16)));

                dados.add(emprestimmo);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar na base de dados empréstimos! \n" + ex);
        }

        return dados;
        */
    }

    /**
     * Consultar itens de determinado empréstimo
     */
    public List<EmprestimoItem> listarItens(int emprestimo) {
        ModelDAO modelDAO = new ModelDAO<EmprestimoItem>();

        String sql = " FROM EmprestimoItem i, Emprestimo e, Catalogacao c, Estratigrafia est, Designacao des "
                    + "WHERE i.emprestimo.id = "+emprestimo+" AND i.emprestimo.id = e.id "
                    + "AND i.catalogacao.id = c.id AND c.designacao.id = des.id "
                    + "AND c.estratigrafia.id = est.id ";

        List<EmprestimoItem> emprestimoitems = (List<EmprestimoItem>) modelDAO.findSQL(sql);

        if(emprestimoitems == null) {
            return null;
        } else {
            return emprestimoitems;
        }
        /*
        List<EmprestimoItem> dados = new ArrayList<>();

        try {
            String sql = "SELECT item.id_emprestimo_item, item.conservacao, emp.id_emprestimo, emp.numero_emprestimo, "
                    + "cat.id_catalogacao, cat.numero_ordem, des.id_designacao, des.genero, est.id_estratigrafia, est.formacao  "
                    + "FROM tb_emprestimo_item AS item, tb_emprestimo AS emp, tb_catalogacao AS cat, tb_estratigrafia AS est, tb_designacao AS des "
                    + "WHERE item.fk_emprestimo =? AND item.fk_emprestimo = emp.id_emprestimo "
                    + "AND item.fk_catalogacao = cat.id_catalogacao AND cat.fk_designacao = des.id_designacao "
                    + "AND cat.fk_estratigrafia = est.id_estratigrafia ";

            stm = conector.prepareStatement(sql);
            stm.setInt(1, emprestimo);
            rs = stm.executeQuery();

            while (rs.next()) {
                Catalogacao catalogacao = new Catalogacao(rs.getInt(5), rs.getString(6), new Designacao(rs.getInt(7), rs.getString(8)), new Estratigrafia(rs.getInt(9), rs.getString(10)), null);
                EmprestimoItem item = new EmprestimoItem(rs.getInt(1), rs.getString(2), new Emprestimo(rs.getInt(3), rs.getString(4)), catalogacao);

                dados.add(item);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar na base de dados empréstimos! \n" + ex);
        }

        return dados;
        */
    }

    /**
     * Consultar todos empréstimos cadastrados
     */
    public List<Emprestimo> listar() {
        ModelDAO modelDAO = new ModelDAO<Emprestimo>();

        String sql = " FROM Emprestimo e, Instituicao i"
                    +" WHERE e.instituicao.id = i.id ";

        List<Emprestimo> emprestimos = (List<Emprestimo>) modelDAO.findSQL(sql);

        if(emprestimos == null) {
            return null;
        } else {
            return emprestimos;
        }
        /*
        List<Emprestimo> dados = new ArrayList<>();

        try {
            String sql = "SELECT tb_emprestimo.*, tb_instituicao.nome FROM tb_emprestimo, tb_instituicao "
                    + "WHERE tb_emprestimo.fk_instituicao = tb_instituicao.id_instituicao ";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                Emprestimo emprestimmo = new Emprestimo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
                        Tempo.toDate(rs.getTimestamp(10)), Tempo.toDate(rs.getTimestamp(11)), rs.getString(13),
                        rs.getString(14), new Instituicao(rs.getInt(15), rs.getString(16)));

                dados.add(emprestimmo);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar na base de dados empréstimos! \n" + ex);
        }

        return dados;
        */
    }

    /**
     * Consultar empréstimos que já foram entregues e exibir no combo
     */
    public List<Emprestimo> comboDevolucao() {
        ModelDAO modelDAO = new ModelDAO<Emprestimo>();

        String sql = " FROM Emprestimo e WHERE e.status != 'Entregue' ";

        List<Emprestimo> emprestimos = (List<Emprestimo>) modelDAO.findSQL(sql);

        if(emprestimos == null) {
            return null;
        } else {
            return emprestimos;
        }
        /*
        List<Emprestimo> dados = new ArrayList<>();

        try {
            String sql = "SELECT  id_emprestimo, numero_emprestimo FROM tb_emprestimo WHERE status_emprestimo != 'Entregue' ";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                Emprestimo emprestimmo = new Emprestimo(rs.getInt(1), rs.getString(2));
                dados.add(emprestimmo);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar na base de dados empréstimos! \n" + ex);
        }

        return dados;
        */
    }

    /**
     * Consultar empréstimos que estão em aberto para exibição no combo
     */
    public List<Emprestimo> comboItens() {
        ModelDAO modelDAO = new ModelDAO<Emprestimo>();

        String sql = " FROM Emprestimo e WHERE e.status != 'Entregue' ";

        List<Emprestimo> emprestimos = (List<Emprestimo>) modelDAO.findSQL(sql);

        if(emprestimos == null) {
            return null;
        } else {
            return emprestimos;
        }
        /*
        List<Emprestimo> dados = new ArrayList<>();

        try {
            String sql = "SELECT  id_emprestimo, numero_emprestimo FROM tb_emprestimo WHERE status_emprestimo = 'Aberto' ";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                Emprestimo emprestimmo = new Emprestimo(rs.getInt(1), rs.getString(2));
                dados.add(emprestimmo);
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar na base de dados empréstimos! \n" + ex);
        }

        return dados;
        */
    }

    /**
     * Consultar catalogações de um empréstimo
     */
    public List<Catalogacao> itensEmprestimo(int emprestimo) {
        return null;
        /*
        List<Catalogacao> dados = new ArrayList<>();

        try {
            String sql = "SELECT fk_catalogacao FROM tb_emprestimo_item WHERE fk_emprestimo =? ";

            stm = conector.prepareStatement(sql);
            stm.setInt(1, emprestimo);
            rs = stm.executeQuery();

            while (rs.next()) {
                dados.add(new Catalogacao(rs.getInt(1)));
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar na base de dados empréstimos! \n" + ex);
        }

        return dados;
        */
    }

    /**
     * Consultar empréstimo para acompanhamento no dashboard
     */
    public List<Emprestimo> acompanhamento() {
        ModelDAO modelDAO = new ModelDAO<Emprestimo>();

        String sql = "FROM Emprestimo e, Instituicao i " +
                " WHERE e.instituicao.id = i.id and e.status != 'Entregue' ORDER BY e.dataDevolucao LIMIT 5 ";

        List<Emprestimo> emprestimos = (List<Emprestimo>) modelDAO.findSQL(sql);

        if(emprestimos == null) {
            return null;
        } else {
            return emprestimos;
        }
        /*

        List<Emprestimo> dados = new ArrayList<>();

        try {
            String sql = "SELECT e.id_emprestimo, e.numero_emprestimo, e.solicitante, e.status_emprestimo, e.data_devolucao, i.id_instituicao, i.nome "
                    + "FROM tb_emprestimo as e, tb_instituicao as i WHERE e.fk_instituicao = i.id_instituicao and e.status_emprestimo != 'Entregue' ORDER BY e.data_devolucao LIMIT 5 ";

            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                dados.add(new Emprestimo(rs.getInt(1), rs.getString(2), rs.getString(3), "", "", "", "", "",
                        rs.getString(4), null, Tempo.toDate(rs.getTimestamp(5)), "", "", new Instituicao(rs.getInt(6), rs.getString(7))));
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar na base de dados empréstimos para acompanhamento no dashboard! \n" + ex);
        }

        return dados;
        */
    }
}

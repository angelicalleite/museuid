package br.com.museuid.util;

import br.com.museuid.banco.controle.ControleDAO;
import br.com.museuid.model.Relatorio;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Conusltas na base de dados para geração dos relatorios estão padronizadas para montagem de dados em modelos de
 * hashmap, essa classe auxilia na geração das funções  e querys para geração de relatorios na base de dados e
 * melhorar e minimizar os daos gerados
 */
public class Consultas {

    static Map<String, List<Relatorio>> map = new HashMap<>();

    private Consultas() {
    }

    /**
     * Consultar dados para geração de relatorios de catalogações estratigrafico
     */
    public static Map<String, List<Relatorio>> estratigrafia() {
        String sql = "SELECT 'Estratigrafias', tb_estratigrafia.formacao, count(fk_estratigrafia) AS total  FROM tb_catalogacao, tb_estratigrafia "
                + "WHERE tb_catalogacao.fk_estratigrafia = tb_estratigrafia.id_estratigrafia GROUP BY fk_estratigrafia";
        return ControleDAO.getBanco().getRelatorioDAO().consultar(sql);
    }

    /**
     * Consultar dados para geração de relatorios de catalogações de acordo com suas coleções
     */
    public static Map<String, List<Relatorio>> colecao() {
        String sql = "SELECT 'Coleções', tb_colecao.nome, count(fk_colecao) AS total  FROM tb_catalogacao, tb_colecao WHERE tb_catalogacao.fk_colecao = tb_colecao.id_colecao GROUP BY fk_colecao";
        return ControleDAO.getBanco().getRelatorioDAO().consultar(sql);
    }

    /**
     * Consultar dados para geração de relatorios de catalogações de acordo com suas designações
     */
    public static Map<String, List<Relatorio>> designacao() {
        String sql = "SELECT 'Designações', tb_designacao.genero, count(fk_designacao) AS total FROM tb_catalogacao, tb_designacao " +
                "WHERE tb_catalogacao.fk_designacao = tb_designacao.id_designacao GROUP BY fk_designacao";
        return ControleDAO.getBanco().getRelatorioDAO().consultar(sql);
    }

    /**
     * Consultar dados para geração de relatorios das catalogações de acordo com sua localização
     */
    public static Map<String, List<Relatorio>> localizacao() {
        String sql = "SELECT localizacao, 'Localização', count(localizacao) AS total  FROM tb_catalogacao  GROUP BY localizacao";
        return ControleDAO.getBanco().getRelatorioDAO().consultar(sql);
    }

    /**
     * Consultar dados para geração de relatorios das catalogações de acordo com sua quantidade de partes
     */
    public static Map<String, List<Relatorio>> numeroPartes() {
        String sql = "SELECT numero_partes, 'N° Partes', count(numero_partes) AS total  FROM tb_catalogacao  GROUP BY numero_partes";
        return ControleDAO.getBanco().getRelatorioDAO().consultar(sql);
    }

    /**
     * Consultar dados para geração de relatorios das catalogações de acordo com suas dimensão
     */
    public static Map<String, List<Relatorio>> dimensoes() {
        String sql = "SELECT dimensoes, 'Dimensões',count(dimensoes) AS total  FROM tb_catalogacao  GROUP BY dimensoes";
        return ControleDAO.getBanco().getRelatorioDAO().consultar(sql);
    }

    /**
     * Consultar dados para geração de relatorios das catalogações de acordo com suas procedência
     */
    public static Map<String, List<Relatorio>> procedencia() {
        String sql = "SELECT procedencia, 'Procedência', count(procedencia) AS total  FROM tb_catalogacao  GROUP BY procedencia";
        return ControleDAO.getBanco().getRelatorioDAO().consultar(sql);
    }


    /**
     * Consultar dados para geração de relatorios de catalogações diarios
     */
    public static Map<String, List<Relatorio>> catalogacaoDiaria(LocalDate data) {
        return ControleDAO.getBanco().getRelatorioDAO().catalogacaoDiaria(data);
    }

    /**
     * Consultar dados para geração de relatorios de catalogações mensais
     */
    public static Map<String, List<Relatorio>> catalogacaoMensal(LocalDate data) {
        String sql = "SELECT 'Data Catalogação', day(data_cadastro) AS dia, count(data_cadastro) AS total  FROM tb_catalogacao "
                + "WHERE (SELECT EXTRACT(YEAR_MONTH FROM data_cadastro)) = (SELECT EXTRACT(YEAR_MONTH FROM ?)) GROUP BY dia ";
        return ControleDAO.getBanco().getRelatorioDAO().periodico(sql, data);
    }

    /**
     * Consultar dados para geração de relatorios de catalogações anuais
     */
    public static Map<String, List<Relatorio>> catalogacaoAnual(LocalDate data) {
        String sql = "SELECT 'Data Catalogação', month(data_cadastro) AS mes, count(data_cadastro)  AS total  FROM tb_catalogacao "
                + "WHERE year(data_cadastro) = year(?) GROUP BY  mes ";
        return ControleDAO.getBanco().getRelatorioDAO().periodico(sql, data);
    }

    /**
     * Consultar dados para geração de relatorios de empréstimos diarios
     */
    public static Map<String, List<Relatorio>> emprestimoDiaria(LocalDate data) {
        String sql = "SELECT status_emprestimo,'Empréstimo', count(status_emprestimo) AS total  "
                + "FROM tb_emprestimo WHERE data_emprestimo = ? GROUP BY status_emprestimo ";
        return ControleDAO.getBanco().getRelatorioDAO().periodico(sql, data);
    }

    /**
     * Consultar dados para geração de relatorios de empréstimos mensais
     */
    public static Map<String, List<Relatorio>> emprestimoMensal(LocalDate data) {
        String sql = "SELECT status_emprestimo AS status, day(data_emprestimo) AS dia, count(data_emprestimo) AS total  FROM tb_emprestimo "
                + "WHERE (SELECT EXTRACT(YEAR_MONTH FROM data_emprestimo)) = (SELECT EXTRACT(YEAR_MONTH FROM ?)) GROUP BY status, dia ";
        return ControleDAO.getBanco().getRelatorioDAO().periodico(sql, data);
    }

    /**
     * Consultar dados para geração de relatorios de empréstimos anuais
     */
    public static Map<String, List<Relatorio>> emprestimoAnual(LocalDate data) {
        String sql = "SELECT status_emprestimo AS status, month(data_emprestimo) AS mes, count(data_emprestimo)  AS total  "
                + "FROM tb_emprestimo WHERE year(data_emprestimo) = year(?) GROUP BY status, mes ";
        return ControleDAO.getBanco().getRelatorioDAO().periodico(sql, data);
    }

    /**
     * Consultar dados para geração de relatorios de movimentacões diarios
     */
    public static Map<String, List<Relatorio>> movimentacaoDiaria(LocalDate data) {
        String sql = "SELECT tipo, 'Empréstimo', count(tipo) AS total  FROM tb_movimentacao WHERE data_criacao = ? GROUP BY tipo ";
        return ControleDAO.getBanco().getRelatorioDAO().periodico(sql, data);
    }

    /**
     * Consultar dados para geração de relatorios de movimentacões mensais
     */
    public static Map<String, List<Relatorio>> movimentacaoMensal(LocalDate data) {
        String sql = "SELECT tipo, day(data_criacao) AS dia, count(data_criacao) AS total  FROM tb_movimentacao "
                + "WHERE (SELECT EXTRACT(YEAR_MONTH FROM data_criacao)) = (SELECT EXTRACT(YEAR_MONTH FROM ?)) GROUP BY tipo, dia ";
        return ControleDAO.getBanco().getRelatorioDAO().periodico(sql, data);
    }

    /**
     * Consultar dados para geração de relatorios de movimentacões anuais
     */
    public static Map<String, List<Relatorio>> movimentacaoAnual(LocalDate data) {
        String sql = "SELECT tipo, month(data_criacao) AS mes, count(data_criacao)  AS total  "
                + "FROM tb_movimentacao WHERE year(data_criacao) = year(?) GROUP BY tipo, mes ";
        return ControleDAO.getBanco().getRelatorioDAO().periodico(sql, data);
    }

    /**
     * Consultar dados para geração de relatorios de visitas diarios
     */
    public static Map<String, List<Relatorio>> visitasDiaria(LocalDate data) {
        String sql = "SELECT tipo, 'Visitas', count(tipo) AS total  FROM tb_visitantes WHERE data_visita = ? GROUP BY tipo ";
        return ControleDAO.getBanco().getRelatorioDAO().periodico(sql, data);
    }

    /**
     * Consultar dados para geração de relatorios de visitas mensais
     */
    public static Map<String, List<Relatorio>> visitasMensal(LocalDate data) {
        String sql = "SELECT tipo , day(data_visita) AS dia, count(data_visita) AS total  FROM tb_visitantes "
                + "WHERE (SELECT EXTRACT(YEAR_MONTH FROM data_visita)) = (SELECT EXTRACT(YEAR_MONTH FROM ?)) GROUP BY tipo, dia ";
        return ControleDAO.getBanco().getRelatorioDAO().periodico(sql, data);
    }

    /**
     * Consultar dados para geração de relatorios de visitas anuais
     */
    public static Map<String, List<Relatorio>> visitasAnual(LocalDate data) {
        String sql = "SELECT tipo , month(data_visita) AS mes, count(data_visita)  AS total  FROM tb_visitantes "
                + "WHERE year(data_visita) = year(?) GROUP BY tipo, mes ";
        return ControleDAO.getBanco().getRelatorioDAO().periodico(sql, data);
    }

    /**
     * Consultar dados para geração de relatorios de visitas diarios
     */
    public static Map<String, List<Relatorio>> validacaoDiaria(LocalDate data) {
        String sql = "SELECT tipo, 'Visitas', count(tipo) AS total  FROM tb_visitantes WHERE data_visita = ? GROUP BY tipo ";
        return ControleDAO.getBanco().getRelatorioDAO().periodico(sql, data);
    }

    /**
     * Consultar dados para geração de relatorios de visitas mensais
     */
    public static Map<String, List<Relatorio>> validacaoMensal(LocalDate data) {
        String sql = "SELECT tipo , day(data_visita) AS dia, count(data_visita) AS total  FROM tb_visitantes "
                + "WHERE (SELECT EXTRACT(YEAR_MONTH FROM data_visita)) = (SELECT EXTRACT(YEAR_MONTH FROM ?)) GROUP BY tipo, dia ";
        return ControleDAO.getBanco().getRelatorioDAO().periodico(sql, data);
    }

    /**
     * Consultar dados para geração de relatorios de visitas anuais
     */
    public static Map<String, List<Relatorio>> validacaoAnual(LocalDate data) {
        String sql = "SELECT tipo , month(data_visita) AS mes, count(data_visita)  AS total  FROM tb_visitantes "
                + "WHERE year(data_visita) = year(?) GROUP BY tipo, mes ";
        return ControleDAO.getBanco().getRelatorioDAO().periodico(sql, data);
    }

}

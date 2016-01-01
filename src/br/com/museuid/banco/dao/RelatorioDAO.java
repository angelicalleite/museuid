package br.com.museuid.banco.dao;

import br.com.museuid.model.Relatorio;
import br.com.museuid.util.Mensagem;
import br.com.museuid.util.Tempo;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DAO responsável pela ações realizadas na base de dados referentes aos relatorios
 */
public class RelatorioDAO extends DAO {

    public RelatorioDAO() {
        super();
    }


    /**
     * Consultar todas catalogações cadastradas em determinado dia
     */
    public Map<String, List<Relatorio>> catalogacaoDiaria(LocalDate data) {

        Map<String, List<Relatorio>> map = new HashMap<>();

        try {
            String sql = "SELECT data_cadastro, count(data_cadastro) FROM tb_catalogacao WHERE data_cadastro = ? GROUP BY data_cadastro ";

            stm = conector.prepareStatement(sql);
            stm.setTimestamp(1, Tempo.toTimestamp(data));
            rs = stm.executeQuery();

            while (rs.next()) {
                String chave = Tempo.toString(rs.getTimestamp(1));

                if (map.get(chave) == null) {
                    map.put(chave, new ArrayList<>());
                }

                map.get(chave).add(new Relatorio("", rs.getInt(2)));
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao gerar relatórios catalogações diárias na base de dados! \n" + ex);
        }

        return map;
    }

    /**
     * Consultar dados de relatorios periodicos
     */
    public Map<String, List<Relatorio>> periodico(String sql, LocalDate data) {

        Map<String, List<Relatorio>> map = new HashMap<>();

        try {
            stm = conector.prepareStatement(sql);
            stm.setTimestamp(1, Tempo.toTimestamp(data));
            rs = stm.executeQuery();

            while (rs.next()) {
                String chave = rs.getString(1);

                if (map.get(chave) == null) {
                    map.put(chave, new ArrayList<>());
                }

                map.get(chave).add(new Relatorio(rs.getString(2), rs.getInt(3)));
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar dados na base para geração dos relátorios períodicos! \n" + ex);
        }

        return map;
    }

    /**
     * Consultar dados na base de dados de acordo com a sql informada, devido a padronização dos valores retornados npara
     * hashmap, no qual a chave representa a descrição do item do gráfico e o valor o dado quantitativo obtido.
     * <p>
     * Todas sql informadas atravês desse metodo deve sempre retornar o mesmo padrão de dados para criação do hashmap.
     */
    public Map<String, List<Relatorio>> consultar(String sql) {

        Map<String, List<Relatorio>> map = new HashMap<>();

        try {
            stm = conector.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                String chave = rs.getString(1);

                if (map.get(chave) == null) {
                    map.put(chave, new ArrayList<>());
                }

                map.get(chave).add(new Relatorio(rs.getString(2), rs.getInt(3)));
            }

            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Mensagem.erro("Erro ao consultar dados na base para geração dos relátorios! \n" + ex);
        }

        return map;
    }
}

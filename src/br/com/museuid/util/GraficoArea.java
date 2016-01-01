package br.com.museuid.util;

import br.com.museuid.model.Relatorio;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.List;
import java.util.Map;

/**
 * Criar gráficos de area
 */
public class GraficoArea {

    private static CategoryAxis eixoX;
    private static NumberAxis eixoY;
    private static AreaChart<String, Number> grafico;

    /**
     * Criar grafico de area e inserir dados das series, datas e valores apartir do map informado
     */
    public static AreaChart criar(Map<String, List<Relatorio>> map, String titulo) {

        config(titulo);

        eixoX = new CategoryAxis();
        eixoY = new NumberAxis();
        grafico = new AreaChart<>(eixoX, eixoY);

        for (String chave : map.keySet()) {
            XYChart.Series<String, Number> serie = new XYChart.Series<>();
            serie.setName(chave);

            List<Relatorio> relatorios = map.get(chave);
            for (Relatorio relatorio : relatorios) {
                serie.getData().add(new XYChart.Data<String, Number>(relatorio.getData(), relatorio.getTotal()));
            }

            grafico.getData().add(serie);
        }

        return grafico;
    }

    /**
     * Configurar titulo do graficos e seus eixos X e Y
     */
    public static void config(String titulo) {
        grafico.getData().clear();
        grafico.setTitle(titulo);
        grafico.setVerticalGridLinesVisible(false);

        eixoX.setLabel("Data");
    }
}

package br.com.museuid.util;

import br.com.museuid.model.Relatorio;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.util.List;
import java.util.Map;

/**
 * Criar grafico de linha
 */
public class GraficoLine {

    private static CategoryAxis eixoX;
    private static NumberAxis eixoY;
    private static LineChart<String, Number> grafico;

    /**
     * Criar grafico de linha e inserir dados das series, datas e valores apartir do map informado
     */
    public static LineChart criar(String titulo, String eixo, Map<String, List<Relatorio>> map) {

        config(titulo);

        eixoX = new CategoryAxis();
        eixoY = new NumberAxis();
        grafico = new LineChart<>(eixoX, eixoY);

        for (String chave : map.keySet()) {
            XYChart.Series<String, Number> serie = new XYChart.Series<>();
            serie.setName(chave);

            List<Relatorio> relatorios = map.get(chave);
            for (Relatorio relatorio : relatorios) {
                XYChart.Data dado = new XYChart.Data(relatorio.getData(), relatorio.getTotal());
                dado.setNode(info(relatorio.getTotal()));
                serie.getData().add(dado);
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
        grafico.setVerticalGridLinesVisible(false);

        eixoX.setLabel("Dia");
    }

    /**
     * Ao passar o mouse pelas series exibir valor atingido
     */
    private static StackPane info(double value) {

        StackPane stack = new StackPane();
        Label label = new Label(value + "");
        label.getStyleClass().add("chart-line-conteudo");

        stack.setOnMouseEntered((MouseEvent mouseEvent) -> {
            stack.getChildren().setAll(label);
            stack.toFront();
        });

        stack.setOnMouseExited((MouseEvent mouseEvent) -> {
            stack.getChildren().clear();
        });

        return stack;
    }
}

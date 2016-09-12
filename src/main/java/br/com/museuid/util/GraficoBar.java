package br.com.museuid.util;

import br.com.museuid.model.Relatorio;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Map;

/**
 * Criar gráficos de barra
 */
public class GraficoBar {

    private static CategoryAxis eixoX;
    private static NumberAxis eixoY;
    private static BarChart<String, Number> grafico;

    private GraficoBar() {
    }

    /**
     * Criar grafico de barra e inserir dados das series, datas e valores apartir do map informado
     */
    public static BarChart criar(String titulo, String eixo, Map<String, List<Relatorio>> mapa) {

        eixoX = new CategoryAxis();
        eixoY = new NumberAxis();
        grafico = new BarChart<>(eixoX, eixoY);
        config(titulo, eixo);

        for (String chave : mapa.keySet()) {
            XYChart.Series<String, Number> serie = new XYChart.Series<>();
            serie.setName(chave);

            List<Relatorio> relatorios = mapa.get(chave);
            for (Relatorio relatorio : relatorios) {
                XYChart.Data<String, Number> dado = new XYChart.Data<>(relatorio.getFormatar(), relatorio.getTotal());

                dado.nodeProperty().addListener((ObservableValue<? extends Node> obs, Node old, Node novo) -> {
                    if (novo != null) {
                        info(dado);
                    }
                });

                serie.getData().add(dado);
            }
            grafico.getData().add(serie);
        }

        eixoY.setUpperBound(eixoY.getUpperBound() + 10);

        return grafico;
    }

    /**
     * Exibir acima da barra valor atingido
     */
    private static void info(XYChart.Data<String, Number> data) {
        Text texto = new Text(data.getYValue().toString());
        texto.setStyle("-fx-fill: #555; -fx-font-size: 11px;");

        data.getNode().parentProperty().addListener((ObservableValue<? extends Parent> obs, Parent old, Parent novo) -> {
            Platform.runLater(() -> {
                if (novo != null) {
                    Group grupo = (Group) novo;
                    grupo.getChildren().add(texto);
                }
            });
        });

        data.getNode().boundsInParentProperty().addListener((ObservableValue<? extends Bounds> obs, Bounds old, Bounds novo) -> {
            texto.setLayoutX(Math.round(novo.getMinX() + novo.getWidth() / 2 - texto.prefWidth(-1) / 2));
            texto.setLayoutY(Math.round(novo.getMinY() - texto.prefHeight(-1) * 0.5));
        });

    }

    /**
     * Configurar elementos do grafico eixos, titulos, legendas
     */
    public static void config(String titulo, String eixo) {
        grafico.getData().clear();
        eixoX.setLabel(eixo);
        grafico.setLegendVisible(false);
    }
}

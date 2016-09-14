package br.com.museuid.util;

import br.com.museuid.model.Relatorio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * Criar grafico de pizza
 */
public class GraficoPie {

    static PieChart grafico = new PieChart();

    private GraficoPie() {
    }

    /**
     * Criar grafico de pizza e exibir dados aparitr do map informado
     */
    public static PieChart criar(String titulo, Map<String, List<Relatorio>> map) {

        double total = 0;
        grafico.setTitle(titulo);

        ObservableList<PieChart.Data> dados = FXCollections.observableArrayList();

        for (String chave : map.keySet()) {
            List<Relatorio> relatorios = map.get(chave);

            for (Relatorio relatorio : relatorios) {
                dados.add(new PieChart.Data(chave, relatorio.getTotal()));
                total += relatorio.getTotal();
            }
        }

        grafico.dataProperty().set(dados);
        info(grafico, total);

        return grafico;
    }

    /**
     * Exibir nome e porcentagem atingida da fatia do grafico ao passar o mouse sobre as fatias
     */
    public static void info(PieChart grafico, double total) {
        for (PieChart.Data data : grafico.getData()) {
            final Tooltip tip = new Tooltip(data.getName() + " : " + new DecimalFormat("#.##").format((data.getPieValue() / total) * 100) + " %");

            data.getNode().setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent me) {
                    if (me.getSource() instanceof Node) {
                        Node sender = (Node) me.getSource();
                        Tooltip.install(sender, tip);
                        sender.setEffect(new Glow(0.2));
                    }
                }
            });
            data.getNode().setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent me) {
                    if (me.getSource() instanceof Node) {
                        Node sender = (Node) me.getSource();
                        sender.setEffect(null);
                        tip.hide();
                    }
                }
            });
        }
    }
}



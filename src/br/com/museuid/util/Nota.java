package br.com.museuid.util;

import br.com.museuid.view.app.AppController;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Criar Notas informativas, simplicação sistema de mensagem
 */
public class Nota {

    private static int qtNotas = 0;

    private Nota() {
    }

    /**
     * Cria a nota e adicionar ao box passado
     */
    private static void nota(VBox box, String mensagem, String tipo) {

        HBox nota = new HBox(label(mensagem, tipo));
        nota.getStylesheets().add("br/com/museuid/css/dialog.css");

        nota.getChildren().add(close(box, nota));
        nota.getStyleClass().add("box-nota");
        Animacao.fade(nota, 0.5, 1, 1);
        ++qtNotas;

        if (qtNotas >= 11) {
            box.getChildren().remove(box.getChildren().size() - 1);
            --qtNotas;
        }

        box.getChildren().add(nota);
    }

    /**
     * Adicionar ação fechar nota
     */
    private static Button close(VBox box, HBox nota) {

        Button acao = new Button();
        acao.getStyleClass().add("bt-close-nota");

        acao.setOnAction((ActionEvent e) -> {
            box.getChildren().remove(nota);
            --qtNotas;
        });

        return acao;
    }

    /**
     * Formatar mensagem da nota
     */
    private static Label label(String texto, String tipo) {

        Label mensagem = new Label(texto);
        mensagem.getStyleClass().add("nota-texto");
        HBox.setHgrow(mensagem, Priority.ALWAYS);
        mensagem.setMaxWidth(Double.MAX_VALUE);
        icone(tipo, mensagem);

        return mensagem;
    }

    /**
     * Conforme o tipo da nota exibir seu respectivo icone
     */
    private static void icone(String tipo, Label mensagem) {
        switch (tipo) {
            case "INFO":
                mensagem.getStyleClass().add("nota-info");
                break;
            case "ERRO":
                mensagem.getStyleClass().add("nota-erro");
                break;
            case "ALERTA":
                mensagem.getStyleClass().add("nota-alerta");
                break;
            case "CONFIRMAR":
                mensagem.getStyleClass().add("nota-confirma");
                break;
            default:
                mensagem.getStyleClass().add("nota-info");
                break;
        }
    }

    public static void alerta(String mensagem) {
        nota(AppController.getInstance().boxNotas(), mensagem, "ALERTA");
    }

    public static void info(String mensagem) {
        nota(AppController.getInstance().boxNotas(), mensagem, "INFO");
    }

    public static void erro(String mensagem) {
        nota(AppController.getInstance().boxNotas(), mensagem, "ERRO");
    }

    public static void confirma(String mensagem) {
        nota(AppController.getInstance().boxNotas(), mensagem, "CONFIRMAR");
    }
}

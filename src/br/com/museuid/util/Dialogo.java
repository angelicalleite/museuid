package br.com.museuid.util;

import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Criar janelas de dialogos
 */
public class Dialogo {

    private static final Screen screen = Screen.getPrimary();
    private static final Rectangle2D windows = screen.getVisualBounds();
    static Dialog dialogo;
    private static Resposta resposta = Resposta.CANCEL;

    private Dialogo() {
    }

    /**
     * Conforme o tipo da mensagem exibir seu respectivo icone
     */
    public static Label icone(String tipo) {

        Label label = new Label();

        switch (tipo) {
            case "INFO":
                label.getStyleClass().add("img-dialog");
                break;
            case "ERRO":
                label.getStyleClass().add("img-dialog-erro");
                break;
            case "ALERTA":
                label.getStyleClass().add("img-dialog-alert");
                break;
            case "CONFIRMAR":
                label.getStyleClass().add("img-dialog-confirmar");
                break;
            default:
                label.getStyleClass().add("img-dialog");
                break;
        }
        return label;
    }

    /**
     * Formata texto (titulo e descrição) da mensagem
     */
    public static VBox texto(String title, String msg) {
        VBox box = new VBox();

        Label titulo = new Label(title);
        titulo.getStyleClass().add("titulo-dialogs");

        Label mensagem = new Label(msg);
        mensagem.getStyleClass().add("mensagem-dialogs");

        box.getChildren().addAll(titulo, mensagem);
        box.getStyleClass().add("caixa-mensagem");

        return box;
    }

    /**
     * Adiciona ações como Sim, Não, Cancelar, Ok
     */
    public static HBox acoes() {
        HBox box = new HBox();
        box.getStyleClass().add("box-acao-dialog");

        Button ok = new Button("OK");
        ok.setOnAction((ActionEvent e) -> {
            dialogo.close();
        });

        ok.getStyleClass().add("bt-ok");
        box.getChildren().addAll(ok);

        return box;
    }

    /**
     * Adiciona titulo e descriçao do alerta ao box de mensagem
     */
    public static void mensagens(String tipo, String titulo, String mensagem) {
        box(icone(tipo), texto(titulo, mensagem), acoes());
    }

    /**
     * Permite que o usuário retorne uma resposta da mensagem de acordo com o
     * tipo da mensagem como: OK, SIM, NÃO e CANCELAR
     */
    public static Resposta mensageConfirmar(String titulo, String mensagem) {
        HBox box = new HBox();
        box.getStyleClass().add("box-acao-dialog");

        Button yes = new Button("SIM");
        yes.setOnAction((ActionEvent e) -> {
            dialogo.close();
            resposta = Resposta.YES;
        });
        yes.getStyleClass().add("bt-sim");

        Button no = new Button("NÃO");
        no.setOnAction((ActionEvent e) -> {
            dialogo.close();
            resposta = Resposta.NO;
        });
        no.getStyleClass().add("bt-nao");
        box.getChildren().addAll(yes, no);

        box(icone("CONFIRMAR"), texto(titulo, mensagem), box);

        return resposta;
    }

    /**
     * Box principal que adiciona e formata o icone, mensagem e ação ao box de
     * mensagem
     */
    public static void box(Label icon, VBox mensagem, HBox acoes) {
        GridPane grid = new GridPane();
        grid.add(icon, 0, 0);
        grid.add(mensagem, 1, 0);
        grid.add(acoes, 1, 1);
        grid.getStyleClass().add("box-grid");
        grid.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        HBox boxCentral = new HBox(grid);
        boxCentral.getStyleClass().add("box-msg");
        Resize.margin(boxCentral, 0);

        AnchorPane boxPrincipal = new AnchorPane(boxCentral);
        boxPrincipal.setStyle("-fx-background-color: rgba(0, 0, 0, 0.0);");
        Resize.margin(boxPrincipal, 0);

        boxDialogo(boxPrincipal);
    }

    /**
     * Box principal da mensagem e adicionado a tela principal
     */
    public static void boxDialogo(AnchorPane pane) {
        Scene scene = new Scene(pane);
        scene.getStylesheets().add("br/com/museuid/css/dialog.css");
        scene.setFill(Color.TRANSPARENT);

        dialogo = new Dialog(new Stage(), scene);
        dialogo.exibir();
    }

    public enum Resposta {
        NO, YES, OK, CANCEL
    }

    /**
     * Cria e formata a stage principal que sera exibido a mensagem de dialogo
     */
    static class Dialog extends Stage {

        public Dialog(Stage stage, Scene scene) {
            initStyle(StageStyle.TRANSPARENT);
            initModality(Modality.APPLICATION_MODAL);
            initOwner(stage);
            setX(windows.getMinX());
            setY(windows.getMinY());
            setWidth(windows.getWidth());
            setHeight(windows.getHeight());
            setScene(scene);
        }

        public void exibir() {
            centerOnScreen();
            showAndWait();
        }
    }

}

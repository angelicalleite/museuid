package br.com.museuid.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Inicialização tela de login
 *
 * @author angelicalleite
 */
public class Login extends Application {

    public static Stage palco;
    private Scene cena;
    private AnchorPane page;

    private Screen screen = Screen.getPrimary();
    private Rectangle2D windows = screen.getVisualBounds();

    @Override
    public void start(final Stage stage) {
        try {
            palco = stage;
            page = FXMLLoader.load(Login.class.getResource("../view/login/login.fxml"));
            cena = new Scene(page);

            stage.initStyle(StageStyle.UNDECORATED);

            stage.setX(windows.getMinX());
            stage.setY(windows.getMinY());
            stage.setWidth(windows.getWidth());
            stage.setHeight(windows.getHeight());

            stage.getIcons().addAll(new Image(Login.class.getResourceAsStream("icone.png")));

            stage.setScene(cena);
            stage.show();

        } catch (Exception ex) {
            System.out.println("Erro ao inicializar aplicação!" + ex);
        }
    }

    public static void main(String[] args) {
        Application.launch(Login.class, (java.lang.String[]) null);
    }
}

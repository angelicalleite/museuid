package br.com.museuid.util;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Utilitario com animações predefinidas
 */
public class Animacao {

    private static FadeTransition fade;

    private Animacao() {
    }

    //criar animação fade
    public static void fade(Node no) {
        fade = new FadeTransition(Duration.seconds(1), no);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setCycleCount(1);
        fade.setAutoReverse(true);
        fade.play();
    }

    //criar animação fade definindo opacidade de origem, destino e duração da animação
    public static void fade(Node no, double inicio, double fim, int tempo) {
        fade = new FadeTransition(Duration.seconds(tempo), no);
        fade.setFromValue(inicio);
        fade.setToValue(fim);
        fade.setCycleCount(1);
        fade.setAutoReverse(true);
        fade.play();
    }

    //criar animação fade definindo opacidade de origem, destino e duração da animação
    public static void stopfade() {
        fade.stop();
    }
}

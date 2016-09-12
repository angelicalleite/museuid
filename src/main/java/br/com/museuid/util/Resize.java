package br.com.museuid.util;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 * Redimensionamento dos componentes de acordo com seu parente
 * permitindo dimensionar o tamanho de distancia entreo o nó e seu parent
 */
public class Resize {

    private Resize() {
    }

    /**
     * Função para facilitar o redimensionamento dos nós para seu tamanho de
     * acordo com a distancia do seu parente
     *
     * @param no     node parente do componente a ser redimensionado
     * @param top    distancia do topo com o nó parente
     * @param right  distancia da direita com o nó parente
     * @param bottom distancia do fundi com o nó parente
     * @param left   distancia sa esquerda com o nó parente
     */
    public static void margin(Node no, double top, double right, double bottom, double left) {
        AnchorPane.setTopAnchor(no, top);
        AnchorPane.setRightAnchor(no, right);
        AnchorPane.setBottomAnchor(no, bottom);
        AnchorPane.setLeftAnchor(no, left);
    }

    /**
     * Função para facilitar o redimensionamento dos nós para seu tamanho de
     * acordo com a distancia do seu parente
     *
     * @param no    node parente do componente a ser redimensionado
     * @param valor valores para todos os nodes
     */
    public static void margin(Node no, double valor) {
        AnchorPane.setTopAnchor(no, valor);
        AnchorPane.setRightAnchor(no, valor);
        AnchorPane.setBottomAnchor(no, valor);
        AnchorPane.setLeftAnchor(no, valor);
    }

    /**
     * Defenir valores de margen topo, direita e esquerda em relação ao parente
     */
    public static void margin(Node no, double top, double right, double left) {
        AnchorPane.setTopAnchor(no, top);
        AnchorPane.setRightAnchor(no, right);
        AnchorPane.setLeftAnchor(no, left);
    }

    /**
     * Defenir valores de margen direita e esquerda em relação ao parente
     */
    public static void margin(Node no, double right, double left) {
        AnchorPane.setRightAnchor(no, right);
        AnchorPane.setLeftAnchor(no, left);
    }

    /**
     * Defenir valores de margen do topo
     */
    public static void marginTop(Node no, double top) {
        AnchorPane.setTopAnchor(no, top);
    }

    /**
     * Defenir valores de margen direita
     */
    public static void marginRight(Node no, double right) {
        AnchorPane.setRightAnchor(no, right);
    }

    /**
     * Defenir valores de margen rodape
     */
    public static void marginBottom(Node no, double bottom) {
        AnchorPane.setBottomAnchor(no, bottom);
    }

    /**
     * Defenir valores de margen esquerda
     */
    public static void marginLeft(Node no, double left) {
        AnchorPane.setLeftAnchor(no, left);
    }
}

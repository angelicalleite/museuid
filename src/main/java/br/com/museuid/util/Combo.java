package br.com.museuid.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.util.List;

/**
 * Classe auxiliar na população de combobox
 *
 * @author Angelica Leite
 */
public class Combo {

    private Combo() {
    }

    /**
     * Popular combos generico atravês de uma coleção do tipo List
     */
    public static void popular(ComboBox combo, List lista) {
        dados(combo, FXCollections.observableArrayList(lista));
    }

    /**
     * Popular combos generico atravês um array de strings passados
     */
    public static void popular(ComboBox combo, String... itens) {
        dados(combo, FXCollections.observableArrayList(itens));
    }

    /**
     * Popular combos com dados informados ao combo
     */
    private static void dados(ComboBox combo, ObservableList dados) {
        if (dados.isEmpty() || dados == null) {
            limpar(combo);
        } else {
            combo.setItems(dados);
            combo.getSelectionModel().selectFirst();
        }
    }

    /**
     * Limpar combo informado
     */
    private static void limpar(ComboBox<Object> combo) {
        combo.getItems().clear();
        combo.setPromptText("-- Registros não encontrados --");
    }
}
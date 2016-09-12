package br.com.museuid.util;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * Criar algumas mascaras para campos de texto
 */
public class Mascara {

    private Mascara() {
    }

    /**
     * Digitar apenas numeros no campo de texto passado
     */
    public static void numerico(TextField campo) {
        campo.lengthProperty().addListener((ObservableValue<? extends Number> ov, Number t, Number t1) -> {
            if (t1.intValue() > t.intValue()) {
                char ch = campo.getText().charAt(t.intValue());
                if (!(ch >= '0' && ch <= '9')) {
                    campo.setText(campo.getText().substring(0, campo.getText().length() - 1));
                    Campo.erro(campo, null);
                }
            }
        });
    }

    /**
     * Digitar apenas numeros e decimais com ponto(.) no campo de texto passado
     */
    public static void decimal(TextField campo) {
        campo.lengthProperty().addListener((ObservableValue<? extends Number> obs, Number old, Number novo) -> {
            if (novo.intValue() > old.intValue()) {
                char ch = campo.getText().charAt(old.intValue());
                if (!(ch >= '0' && ch <= '9' || ch == '.')) {
                    campo.setText(campo.getText().substring(0, campo.getText().length() - 1));
                    Campo.erro(campo, null);
                }
            }
        });
    }

    /**
     * Digitar apenas letras no campo de texto passado
     */
    public static void alfabeto(TextField campo) {
        campo.lengthProperty().addListener((ObservableValue<? extends Number> obs, Number old, Number novo) -> {
            if (novo.intValue() > old.intValue()) {
                char ch = campo.getText().charAt(old.intValue());
                if (!(ch >= 'a' && ch <= 'z')) {
                    campo.setText(campo.getText().substring(0, campo.getText().length() - 1));
                    Campo.erro(campo, null);
                }
            }
        });
    }

    /**
     * Limitar o quantidade de caractres do campo de texto
     */
    public static void max(TextField campo, int maxLength) {
        campo.textProperty().addListener((final ObservableValue<? extends String> obs, final String old, final String novo) -> {
            if (campo.getText().length() > maxLength) {
                campo.setText(campo.getText().substring(0, maxLength));
            }
        });
    }
}

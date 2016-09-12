package br.com.museuid.util;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * Tratar, formatar e clacular datas, auxiliando principalmente na conversão de datas da nova api do java LocalDate
 * para Timestamp para inserção na base de dados
 *
 * @author Angelica
 */
public class Tempo {

    private Tempo() {
    }

    /**
     * Converte um LocalDate para Timestamp com os horarios zerados, ou seja
     * meia noite
     */
    public static Timestamp toTimestamp(LocalDate data) {
        return Timestamp.valueOf(data.atStartOfDay());
    }

    /**
     * Converte Local Date Time para Timestamp
     */
    public static Timestamp toTimestamp(LocalDateTime data) {
        return Timestamp.valueOf(data);
    }

    /**
     * Converte String em Timestamp
     */
    public static Timestamp toTimestamp(String data) {
        return Timestamp.valueOf(LocalDateTime.parse(data, formatter("yyyy-MM-dd HH:mm.ss")));
    }

    /**
     * Converte String em Timestamp indicando o formato
     */
    public static Timestamp toTimestamp(String data, String modelo) {
        return Timestamp.valueOf(LocalDateTime.parse(data, formatter(modelo)));
    }

    /**
     * Converte Timestamp para Local Date Time
     */
    public static LocalDateTime toDateTime(Timestamp time) {
        return time.toLocalDateTime();
    }

    /**
     * Converte Timestamp para Local Date Time
     */
    public static LocalDate toDate(Timestamp time) {
        return time.toLocalDateTime().toLocalDate();
    }

    /**
     * Converte String em LocalDate
     */
    public static LocalDate toDate(String data) {
        return LocalDate.parse(data, formatter("yyyy-MM-dd"));
    }

    /**
     * Converte String em LocalDate indicando o formato
     */
    public static LocalDate toDate(String data, String modelo) {
        return LocalDate.parse(data, formatter(modelo));
    }

    /**
     * Tranforma Timestamp em String
     */
    public static String toString(Timestamp data) {
        return data == null ? "" : data.toLocalDateTime().format(formatter("dd/MM/yyyy"));
    }

    /**
     * Tranforma Timestamp em String indicado formato
     */
    public static String toString(Timestamp data, String modelo) {
        return data == null ? "" : data.toLocalDateTime().format(formatter(modelo));
    }

    /**
     * Converte LocalDateTime para String no formato dd/MM/yyyy
     */
    public static String toString(LocalDate data) {
        return data == null ? "" : data.format(formatter("dd/MM/yyyy"));
    }

    /**
     * Converte LocalDateTime para String indicando o formato da toString
     */
    public static String toString(LocalDate data, String modelo) {
        return data == null ? "" : data.format(formatter(modelo));
    }

    /**
     * Converte LocalDateTime para String indicando o formato da toString
     */
    public static String toString(LocalDateTime data, String modelo) {
        return data == null ? "" : data.format(formatter(modelo));
    }

    /**
     * Informa a data do mês númerico e retorna seu nome abreviado em string Ex: 1->JAN, 2->FEV, 3->MAR ... 12->DEZ
     */
    public static String mes(String data) {
        return Month.of(Integer.parseInt(data)).getDisplayName(TextStyle.SHORT, new Locale("pt")).toUpperCase();
    }

    /**
     * Retorna data atual no formato timestamp
     */
    public static Timestamp atual() {
        return toTimestamp(LocalDate.now());
    }

    /**
     * Gerar um formatter para fomatação da toDate em string no formato indicado
     */
    private static DateTimeFormatter formatter(String modelo) {
        return DateTimeFormatter.ofPattern(modelo);
    }

    /**
     * Bloquear dias dos DatePickers anteriores ao informado deixando em vermelhos datas não selecionaveis
     */
    public static void blockDataAnterior(LocalDate data, DatePicker calendarario) {

        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {

                        super.updateItem(item, empty);

                        if (item.isBefore(data.plusDays(1))) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc8c3;");
                        }
                    }
                };
            }
        };

        calendarario.setDayCellFactory(dayCellFactory);
        calendarario.setValue(data.plusDays(1));
    }
}

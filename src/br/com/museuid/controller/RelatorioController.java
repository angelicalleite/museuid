package br.com.museuid.controller;

import br.com.museuid.util.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.time.LocalDate;

public class RelatorioController extends AnchorPane {

    private int periodo = 0;
    private int relatorio;

    @FXML
    private AnchorPane boxGrafico;
    @FXML
    private HBox boxPeriodo;
    @FXML
    private Button btRelatorio;
    @FXML
    private Label lbPrincipal;
    @FXML
    private DatePicker data;
    @FXML
    private ToggleGroup menuPeriodo;
    @FXML
    private Label lbTitulo;
    @FXML
    private ToggleGroup menu;
    @FXML
    private HBox boxLegenda;
    @FXML
    private ComboBox<String> cbTipoRelatorio;

    public RelatorioController() {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("../view/relatorio.fxml"));

            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();

        } catch (IOException ex) {
            Mensagem.erro("Erro ao carregar tela de relatórios! \n" + ex);
            ex.printStackTrace();
        }
    }

    @FXML
    void catalogacao(ActionEvent event) {
        config("Relatório Catalogação", 1);
        combo("Data Catalogação", "Procêdencia", "Dimensões", "N° Partes", "Localização", "Designação", "Estratigrafia", "Coleção");
    }

    @FXML
    void visitas(ActionEvent event) {
        config("Relatório Visitas", 2);
        combo("Visitas");
    }

    @FXML
    void emprestimo(ActionEvent event) {
        config("Relatório Empréstimo", 3);
        combo("Empréstimo");
    }

    @FXML
    void validacao(ActionEvent event) {
        config("Relatório Validação", 4);
        combo("Validação");
    }

    @FXML
    void movimentacao(ActionEvent event) {
        config("Relatório Movimentação", 5);
        combo("Movimentação");
    }

    @FXML
    void relatorio(ActionEvent event) {
        if (data == null) {
            Nota.alerta("Data não informada!");
        } else if (cbTipoRelatorio.getItems().isEmpty()) {
            Nota.alerta("Tipo relatório não informado!");
        } else if (periodo == 0) {
            Nota.alerta("Selecione o periodo para geração do relatório!");
        } else {
            Relatorios.criar(boxGrafico, cbTipoRelatorio.getValue(), periodo, data.getValue());
        }
    }

    @FXML
    void initialize() {
        Grupo.notEmpty(menu);

        data.setValue(LocalDate.now());
        periodo();
        desabilitarPeriodo();

        movimentacao(null);
    }

    /**
     * Bloquear seleção de periodo para relatorios que não são periodicos
     */
    private void desabilitarPeriodo() {
        cbTipoRelatorio.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue obs, String old, String novo) {
                Platform.runLater(() -> {
                    data.setDisable(!"Data Catalogação".equals(novo) && relatorio == 1);
                    boxPeriodo.setDisable(!"Data Catalogação".equals(novo) && relatorio == 1);
                    periodo = !"Data Catalogação".equals(novo) && relatorio == 1 ? 1 : menuPeriodo.getToggles().indexOf(menuPeriodo.getSelectedToggle()) + 1;
                });
            }
        });

    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void config(String tituloTela, int grupoMenu) {
        lbTitulo.setText(tituloTela);
        relatorio = grupoMenu;
        menu.selectToggle(menu.getToggles().get(grupoMenu - 1));
    }

    /**
     * Preencher combobox principal com itens de acordo com o tipo de relatorio
     */
    private void combo(String... itens) {
        Combo.popular(cbTipoRelatorio, itens);
    }

    /**
     * Adionar escutador ao grupo de menus periodo para saber qual periodo esta ativo
     */
    private void periodo() {
        menuPeriodo.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> obs, Toggle old, Toggle novo) {
                periodo = novo != null ? menuPeriodo.getToggles().indexOf(menuPeriodo.getSelectedToggle()) + 1 : 0;
            }
        });
    }
}

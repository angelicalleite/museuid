package br.com.museuid.controller;

import br.com.museuid.banco.controle.ControleDAO;
import br.com.museuid.model.Catalogacao;
import br.com.museuid.model.Emprestimo;
import br.com.museuid.model.EmprestimoItem;
import br.com.museuid.util.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class DevolucaoController extends AnchorPane {

    private List<EmprestimoItem> listaEmprestimoItens;

    @FXML
    private GridPane telaCadastro;
    @FXML
    private TextField txtObservacao;
    @FXML
    private Label legenda;
    @FXML
    private TableColumn colEstadoObservacao;
    @FXML
    private TableColumn colNumOrdem;
    @FXML
    private ToggleGroup menu;
    @FXML
    private ComboBox<Emprestimo> cbEmprestimo;
    @FXML
    private TableColumn colDesignacao;
    @FXML
    private TableView<EmprestimoItem> tbEmprestimo;
    @FXML
    private Button btDevolucao;
    @FXML
    private Label lbTitulo;
    @FXML
    private DatePicker dtEntrega;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colEstratigrafia;

    public DevolucaoController() {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("../view/devolucao.fxml"));

            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();

        } catch (IOException ex) {
            Mensagem.erro("Erro ao carregar tela devolução de empréstimo! \n" + ex);
            ex.printStackTrace();
        }
    }

    @FXML
    void telaDevolucao(ActionEvent event) {
        config("Devolução Empréstimo", "Campos obrigatórios", 0);
    }

    @FXML
    void devolucao(ActionEvent event) {

        boolean vazio = Campo.noEmpty(txtObservacao);

        Emprestimo emprestimo = cbEmprestimo.getValue();
        String observacao = txtObservacao.getText();
        LocalDate dataEntrega = dtEntrega.getValue();

        if (vazio) {
            Nota.alerta("Preencher campos vazios!");
        } else if (cbEmprestimo.getItems().isEmpty()) {
            Nota.alerta("Empréstimo não encontrados!");
        } else {

            List<Catalogacao> itens = ControleDAO.getBanco().getEmprestimoDAO().itensEmprestimo(emprestimo.getId());

            if (itens.isEmpty()) {
                Nota.alerta("Empréstimo não contém itens para devolução!");
            } else {
                ControleDAO.getBanco().getEmprestimoDAO().devolucao(emprestimo.getId(), dataEntrega, observacao);//relizar devolução item

                for (Catalogacao catalogacao : itens) {//mudar status de itens para inativo
                    ControleDAO.getBanco().getCatalogacaoDAO().statusEmprestada(catalogacao.getId(), false);
                }

                Nota.alerta("Devolução dp empréstimo feita cm sucesso!");
            }

            sincronizarBase(emprestimo.getId());
            tabela();
            limpar();
            combos();
        }
    }

    @FXML
    void initialize() {
        Grupo.notEmpty(menu);

        dtEntrega.setValue(LocalDate.now());
        combos();
        sincronizarBase(cbEmprestimo.getValue() != null ? cbEmprestimo.getValue().getId() : 0);
        tabela();

        cbEmprestimo.valueProperty().addListener(new ChangeListener<Emprestimo>() {
            @Override
            public void changed(ObservableValue ov, Emprestimo old, Emprestimo novo) {
                sincronizarBase(novo == null ? 0 : novo.getId());
                tabela();
            }
        });

        telaDevolucao(null);
    }

    /**
     * Preencher combos estaticos e dinamicos
     */
    public void combos() {
        Combo.popular(cbEmprestimo, ControleDAO.getBanco().getEmprestimoDAO().comboDevolucao());
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void config(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);

        legenda.setText(msg);
        tbEmprestimo.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));
    }

    /**
     * Sincronizar dados com banco de dados
     */
    private void sincronizarBase(int emprestimo) {
        listaEmprestimoItens = ControleDAO.getBanco().getEmprestimoDAO().listarItens(emprestimo);
    }

    /**
     * Mapear dados objetos para inserção dos dados na tabela
     */
    private void tabela() {
        ObservableList data = FXCollections.observableArrayList(listaEmprestimoItens);

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEstadoObservacao.setCellValueFactory(new PropertyValueFactory<>("conservacao"));

        colNumOrdem.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<EmprestimoItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<EmprestimoItem, String> obj) {
                return new SimpleStringProperty(obj.getValue().getCatalogacao().getNumeroOrdem());
            }
        });
        colDesignacao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<EmprestimoItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<EmprestimoItem, String> obj) {
                return new SimpleStringProperty(obj.getValue().getCatalogacao().getDesignacao().getGenero());
            }
        });
        colEstratigrafia.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<EmprestimoItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<EmprestimoItem, String> obj) {
                return new SimpleStringProperty(obj.getValue().getCatalogacao().getEstratigrafia().getFormacao());
            }
        });

        tbEmprestimo.setItems(data);
    }

    /**
     * Limpar campos tela
     */
    private void limpar() {
        Campo.limpar(txtObservacao);
    }
}
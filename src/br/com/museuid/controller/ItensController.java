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
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

public class ItensController extends AnchorPane {

    private List<EmprestimoItem> listaEmprestimoItens;

    @FXML
    private TextField txtNumOrdem;
    @FXML
    private TableView<EmprestimoItem> tbEmprestimoItens;
    @FXML
    private TextField txtEstadoConservacao;
    @FXML
    private ComboBox<Emprestimo> cbEmprestimo;
    @FXML
    private TableColumn colNumOrdem;
    @FXML
    private Label lbTitulo;
    @FXML
    private Label legenda;
    @FXML
    private ToggleGroup menu;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colDesignacao;
    @FXML
    private TableColumn colEstratigrafia;
    @FXML
    private TableColumn colEstadoObservacao;

    public ItensController() {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("../view/itens.fxml"));

            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();

        } catch (IOException ex) {
            Mensagem.erro("Erro ao carregar tela itens dos empréstimos! \n" + ex);
        }
    }

    @FXML
    void telaItens(ActionEvent event) {
        config("Itens Empréstimo", "Campos obrigatórios", 0);
    }

    @FXML
    void adicionar(ActionEvent event) {

        boolean vazio = Campo.noEmpty(txtNumOrdem);

        Emprestimo emprestimo = cbEmprestimo.getValue();
        String conservacao = txtEstadoConservacao.getText();
        String ordem = txtNumOrdem.getText();

        if (vazio) {
            Nota.alerta("Preencher campos vazios!");
        } else if (cbEmprestimo.getItems().isEmpty()) {
            Nota.alerta("Empréstimo não encontrados!");
        } else {
            int catalogacao = ControleDAO.getBanco().getCatalogacaoDAO().infoId(ordem);//caso numero de ordem não seja encontrado retornar 0

            if (catalogacao == 0) {
                Nota.alerta("Catalogação não encontrada!");
            } else if (ControleDAO.getBanco().getCatalogacaoDAO().isEmprestada(catalogacao)) {
                Nota.alerta("Catalogação já encontra-se emprestada!");//verificar se catalogação já está emprestada
            } else {
                EmprestimoItem item = new EmprestimoItem(0, conservacao, emprestimo, new Catalogacao(catalogacao));//criar item
                ControleDAO.getBanco().getEmprestimoDAO().addItem(item);//adiciona item ao emprestimo
                ControleDAO.getBanco().getCatalogacaoDAO().statusEmprestada(catalogacao, true);//atualiza status de emprestimo da catalogacao para ativo = 1
            }

            sincronizarBase(emprestimo.getId());
            tabela();
            limpar();
        }
    }

    @FXML
    void remover(ActionEvent event) {
        try {
            EmprestimoItem item = tbEmprestimoItens.getSelectionModel().getSelectedItem();
            item.getClass();

            int catalogacao = item.getCatalogacao().getId();

            ControleDAO.getBanco().getEmprestimoDAO().excluirItem(item.getId());//adiciona item ao emprestimo
            ControleDAO.getBanco().getCatalogacaoDAO().statusEmprestada(catalogacao, false);//desativar status emprestimo catalogacao

            sincronizarBase(item.getEmprestimo().getId());
            tabela();
        } catch (NullPointerException ex) {
            Nota.alerta("Selecione o item do empréstimo na tabela que deseja remover!");
        }
    }

    @FXML
    void initialize() {
        Grupo.notEmpty(menu);

        combos();
        sincronizarBase(cbEmprestimo.getValue() != null ? cbEmprestimo.getValue().getId() : 0);
        tabela();

        cbEmprestimo.valueProperty().addListener(new ChangeListener<Emprestimo>() {
            @Override
            public void changed(ObservableValue ov, Emprestimo old, Emprestimo novo) {
                sincronizarBase(cbEmprestimo.getValue().getId());
                tabela();
            }
        });

        telaItens(null);
    }

    /**
     * Preencher combos estaticos e dinamicos
     */
    public void combos() {
        Combo.popular(cbEmprestimo, ControleDAO.getBanco().getEmprestimoDAO().comboItens());
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void config(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);

        legenda.setText(msg);
        tbEmprestimoItens.getSelectionModel().clearSelection();
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

        tbEmprestimoItens.setItems(data);
    }

    private void limpar() {
        Campo.limpar(txtEstadoConservacao, txtNumOrdem);
    }
}
package br.com.museuid.controller;

import br.com.museuid.banco.controle.ControleDAO;
import br.com.museuid.model.Emprestimo;
import br.com.museuid.util.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

public class HistoricoController extends AnchorPane {

    private List<Emprestimo> listaEmprestimo;

    @FXML
    private Button btHistorico;
    @FXML
    private TableColumn colDataDevolucao;
    @FXML
    private Label legenda;
    @FXML
    private TableColumn colNumEmprestimo;
    @FXML
    private TableColumn colCPF;
    @FXML
    private TableColumn colDataEntrega;
    @FXML
    private TableColumn colDataEmprestimo;
    @FXML
    private TableColumn colContato;
    @FXML
    private ToggleGroup menu;
    @FXML
    private TableColumn colEmail;
    @FXML
    private TableColumn colResponsavel;
    @FXML
    private TableColumn colStatus;
    @FXML
    private TableColumn colRG;
    @FXML
    private TableView<Emprestimo> tbEmprestimo;
    @FXML
    private TableColumn colSolicitante;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private TableColumn colInstituicao;
    @FXML
    private Label lbTitulo;
    @FXML
    private TableColumn colDescricao;
    @FXML
    private TableColumn colId;

    public HistoricoController() {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("../view/historico.fxml"));

            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();

        } catch (IOException ex) {
            Mensagem.erro("Erro ao carregar tela históricos dos empréstimos! \n" + ex);
        }
    }

    @FXML
    void telaHistorico(ActionEvent event) {
        config("Histórico Empréstimo", "Quantidade de empréstimos encontrados", 0);
        Modulo.visualizacao(true, btHistorico, txtPesquisar);
        tabela();
    }

    @FXML
    void historico(ActionEvent event) {
        sincronizarBase();
        tabela();
    }

    @FXML
    void initialize() {
        Grupo.notEmpty(menu);
        sincronizarBase();
        tabela();

        txtPesquisar.textProperty().addListener((obs, old, novo) -> {
            filtro(novo, FXCollections.observableArrayList(listaEmprestimo));
        });

        telaHistorico(null);
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void config(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);
        Modulo.visualizacao(false, btHistorico, txtPesquisar);

        legenda.setText(msg);
        tbEmprestimo.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));
    }

    /**
     * Sincronizar dados com banco de dados
     */
    private void sincronizarBase() {
        listaEmprestimo = ControleDAO.getBanco().getEmprestimoDAO().historico();
    }

    /**
     * Mapear dados objetos para inserção dos dados na tabela
     */
    private void tabela() {
        ObservableList data = FXCollections.observableArrayList(listaEmprestimo);

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colContato.setCellValueFactory(new PropertyValueFactory<>("contato"));
        colCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colNumEmprestimo.setCellValueFactory(new PropertyValueFactory<>("numeroEmprestimo"));
        colRG.setCellValueFactory(new PropertyValueFactory<>("rg"));
        colSolicitante.setCellValueFactory(new PropertyValueFactory<>("solicitante"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colResponsavel.setCellValueFactory(new PropertyValueFactory<>("responsavel"));

        colDataDevolucao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Emprestimo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Emprestimo, String> obj) {
                return new SimpleStringProperty(Tempo.toString(obj.getValue().getDataDevolucao()));
            }
        });
        colDataEmprestimo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Emprestimo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Emprestimo, String> obj) {
                return new SimpleStringProperty(Tempo.toString(obj.getValue().getDataEmprestimmo()));
            }
        });
        colDataEntrega.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Emprestimo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Emprestimo, String> obj) {
                return new SimpleStringProperty(Tempo.toString(obj.getValue().getDataEmprestimmo()));
            }
        });
        colInstituicao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Emprestimo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Emprestimo, String> obj) {
                return new SimpleStringProperty(obj.getValue().getInstituicao().getNome());
            }
        });

        tbEmprestimo.setItems(data);
    }

    /**
     * Campo de pesquisar para filtrar dados na tabela
     */
    private void filtro(String valor, ObservableList<Emprestimo> listaEmprestimo) {

        FilteredList<Emprestimo> dadosFiltrados = new FilteredList<>(listaEmprestimo, emprestimo -> true);
        dadosFiltrados.setPredicate(emprestimo -> {

            if (valor == null || valor.isEmpty()) {
                return true;
            } else if (emprestimo.getContato().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (emprestimo.getCpf().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (emprestimo.getEmail().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (emprestimo.getInstituicao().getNome().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (emprestimo.getNumeroEmprestimo().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (emprestimo.getResponsavel().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (emprestimo.getRg().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (emprestimo.getSolicitante().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (emprestimo.getStatus().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            }

            return false;
        });

        SortedList<Emprestimo> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbEmprestimo.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de empréstimos encontradas");

        tbEmprestimo.setItems(dadosOrdenados);
    }
}




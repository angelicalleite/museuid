package br.com.museuid.controller;

import br.com.museuid.banco.controle.ControleDAO;
import br.com.museuid.model.Catalogacao;
import br.com.museuid.util.Filtro;
import br.com.museuid.util.Grupo;
import br.com.museuid.util.Mensagem;
import br.com.museuid.util.Modulo;
import br.com.museuid.view.app.AppController;
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

public class PesquisarController extends AnchorPane {

    private List<Catalogacao> listaCatalogacao;

    @FXML
    private Label legenda;
    @FXML
    private TableColumn colDetalheProcedencia;
    @FXML
    private TableColumn colObservacao;
    @FXML
    private TableColumn colDataEntrada;
    @FXML
    private Button btDetalhes;
    @FXML
    private ToggleGroup menu;
    @FXML
    private TableColumn colProcedencia;
    @FXML
    private TableColumn colDesignacao;
    @FXML
    private TableColumn colColecao;
    @FXML
    private TableColumn colNumeroOrdem;
    @FXML
    private TableColumn colEtiquetaRFID;
    @FXML
    private TableColumn colLocalizacao;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private Label lbTitulo;
    @FXML
    private TableColumn colDimensao;
    @FXML
    private TableView<Catalogacao> tbCatalogacao;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colNumeroPartes;
    @FXML
    private TableColumn colEstratigrafia;

    public PesquisarController() {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("../view/pesquisar.fxml"));

            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();

        } catch (IOException ex) {
            Mensagem.erro("Erro ao carregar tela pesquisas dos fósseis! \n" + ex);
            ex.printStackTrace();
        }
    }

    @FXML
    void telaPesquisa(ActionEvent event) {
        config("Pesquisar Acervo", "Quantidade de catalogações encontrados", 0);
        Modulo.visualizacao(true, btDetalhes, txtPesquisar, legenda);
    }

    @FXML
    void detalhes(ActionEvent event) {
        try {
            String identificador = tbCatalogacao.getSelectionModel().getSelectedItem().getNumeroOrdem();

            Modulo.getIdentificacao(AppController.getInstance().getBoxConteudo());//chamar modulo de identificação
            IdentificacaoController.getInstance().identificar(identificador);//informar identificador
        } catch (NullPointerException ex) {
            Mensagem.alerta("Selecione na tabela a catalogação que deseja obter mais informações");
        }

        tbCatalogacao.getSelectionModel().clearSelection();//limpar seleção na tabela
    }

    @FXML
    public void initialize() {
        Grupo.notEmpty(menu);
        sincronizarBase();
        tabela();

        txtPesquisar.textProperty().addListener((obs, old, novo) -> {
            filtro(novo, FXCollections.observableArrayList(listaCatalogacao));
        });

        telaPesquisa(null);
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void config(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);

        legenda.setText(msg);
        tbCatalogacao.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));
    }

    /**
     * Sincronizar dados com banco de dados
     */
    private void sincronizarBase() {
        listaCatalogacao = ControleDAO.getBanco().getCatalogacaoDAO().listar();
    }

    /**
     * Mapear dados objetos para inserção dos dados na tabela
     */
    private void tabela() {
        ObservableList data = FXCollections.observableArrayList(listaCatalogacao);

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNumeroOrdem.setCellValueFactory(new PropertyValueFactory<>("numeroOrdem"));
        colEtiquetaRFID.setCellValueFactory(new PropertyValueFactory<>("etiquetaRFID"));
        colProcedencia.setCellValueFactory(new PropertyValueFactory<>("procedencia"));
        colDetalheProcedencia.setCellValueFactory(new PropertyValueFactory<>("descricaoProcedencia"));
        colDimensao.setCellValueFactory(new PropertyValueFactory<>("dimensoes"));
        colNumeroPartes.setCellValueFactory(new PropertyValueFactory<>("numeroPartes"));
        colObservacao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colLocalizacao.setCellValueFactory(new PropertyValueFactory<>("localizacao"));
        colDataEntrada.setCellValueFactory(new PropertyValueFactory<>("dataEntrada"));
        colColecao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Catalogacao, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Catalogacao, String> dado) {
                return new SimpleStringProperty(dado.getValue().getColecao().getNome());
            }
        });
        colDesignacao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Catalogacao, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Catalogacao, String> dado) {
                return new SimpleStringProperty(dado.getValue().getDesignacao().getGenero());
            }
        });
        colEstratigrafia.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Catalogacao, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Catalogacao, String> dado) {
                return new SimpleStringProperty(dado.getValue().getEstratigrafia().getFormacao());
            }
        });

        tbCatalogacao.setItems(data);
    }

    /**
     * Campo de pesquisar para filtrar dados na tabela
     */
    private void filtro(String valor, ObservableList<Catalogacao> listaCatalogacao) {

        FilteredList<Catalogacao> dadosFiltrados = new FilteredList<>(listaCatalogacao, catalogacao -> true);
        dadosFiltrados.setPredicate(catalogacao -> {

            if (valor == null || valor.isEmpty()) {
                return true;
            }
            if (catalogacao.getProcedencia().toLowerCase().contains(valor.toLowerCase())) {
                return true;
            } else if (catalogacao.getNumeroOrdem().toLowerCase().contains(valor.toLowerCase())) {
                return true;
            } else if (catalogacao.getLocalizacao().toLowerCase().contains(valor.toLowerCase())) {
                return true;
            } else if (catalogacao.getEtiquetaRFID().toLowerCase().contains(valor.toLowerCase())) {
                return true;
            } else if (catalogacao.getDimensoes().toLowerCase().contains(valor.toLowerCase())) {
                return true;
            } else if (catalogacao.getColecao().getNome().toLowerCase().contains(valor.toLowerCase())) {
                return true;
            } else if (catalogacao.getDesignacao().getGenero().toLowerCase().contains(valor.toLowerCase())) {
                return true;
            } else if (catalogacao.getEstratigrafia().getFormacao().toLowerCase().contains(valor.toLowerCase())) {
                return true;
            }
            return false;
        });

        SortedList<Catalogacao> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbCatalogacao.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de catalogações encontradas");

        tbCatalogacao.setItems(dadosOrdenados);
    }
}

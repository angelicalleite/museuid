package br.com.museuid.controller;

import br.com.museuid.banco.controle.ControleDAO;
import br.com.museuid.model.Catalogacao;
import br.com.museuid.model.Colecao;
import br.com.museuid.model.Designacao;
import br.com.museuid.model.Estratigrafia;
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
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class CatalogarController extends AnchorPane {

    private List<Catalogacao> listaCatalogacao;
    private int idCatalogacao = 0;

    @FXML
    private GridPane telaCadastro;
    @FXML
    private TextField txtNumOrdem;
    @FXML
    private TableColumn colNumOrdem;
    @FXML
    private Button btSalvar;
    @FXML
    private TableColumn colProcedencia;
    @FXML
    private TableColumn colEtiquetaRFID;
    @FXML
    private ComboBox<String> cbDimensao;
    @FXML
    private TableColumn colNumPartes;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private ComboBox<Designacao> cbDesignacao;
    @FXML
    private TableColumn colDimensao;
    @FXML
    private TableColumn colDescricao;
    @FXML
    private TableColumn colProcedenciaDetalhes;
    @FXML
    private Button btEditar;
    @FXML
    private ComboBox<Colecao> cbColecao;
    @FXML
    private TableView<Catalogacao> tbCatalogacao;
    @FXML
    private TableColumn colId;
    @FXML
    private AnchorPane telaEdicao;
    @FXML
    private TableColumn colEstratigrafia;
    @FXML
    private Label legenda;
    @FXML
    private TextField txtNumPartes;
    @FXML
    private ComboBox<Estratigrafia> cbEstratigrafia;
    @FXML
    private TableColumn colDataEntrada;
    @FXML
    private Button btExcluir;
    @FXML
    private ComboBox<String> cbProcedencia;
    @FXML
    private ComboBox<String> cbLocalizacao;
    @FXML
    private TextArea txtDescricao;
    @FXML
    private ToggleGroup menu;
    @FXML
    private TableColumn colDesignacao;
    @FXML
    private TableColumn colColecao;
    @FXML
    private TableColumn colEmprestimo;
    @FXML
    private TableColumn colLocalizacao;
    @FXML
    private TextField txtEtiquetaRFID;
    @FXML
    private Label lbTitulo;
    @FXML
    private DatePicker dtEntrada;
    @FXML
    private TextField txtProcedenciaDetalhes;

    public CatalogarController() {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("../view/catalogar.fxml"));

            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();

        } catch (IOException ex) {
            Mensagem.erro("Erro ao carregar tela catalogação! \n" + ex);
        }
    }

    @FXML
    void telaCadastro(ActionEvent event) {
        config("Cadastrar Catalogação", "Campos obrigatórios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        limpar();
    }

    @FXML
    void telaEdicao(ActionEvent event) {
        config("Editar Catalogação", "Quantidade de catalogações encontradas", 1);
        Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
        tabela();
    }

    @FXML
    void telaExcluir(ActionEvent event) {
        config("Excluir Catalogação", "Quantidade de catalogações encontradas", 2);
        Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
        tabela();
    }

    @FXML
    void salvar(ActionEvent event) {
        boolean vazio = Campo.noEmpty(txtNumOrdem, txtNumPartes);

        String etiquetaRfid = txtEtiquetaRFID.getText().isEmpty() ? txtEtiquetaRFID.getText().trim().replace(" ", "") : "0";
        String numOrdem = txtNumOrdem.getText().trim().replace(" ", "");
        int numPartes = Integer.parseInt(txtNumPartes.getText().trim().isEmpty() ? "0" : txtNumPartes.getText());
        String procedenciaDetalhes = txtProcedenciaDetalhes.getText();
        String descricao = txtDescricao.getText();

        Colecao colecao = cbColecao.getValue();
        Designacao designacao = cbDesignacao.getValue();
        String dimensao = cbDimensao.getValue();
        Estratigrafia estratigrafia = cbEstratigrafia.getValue();
        String localizacao = cbLocalizacao.getValue();
        String procedencia = cbProcedencia.getValue();

        String data = dtEntrada.getValue() == null ? "" : dtEntrada.getValue().toString();

        if (vazio) {
            Nota.alerta("Preencher campos vazios!");
        } else if (ControleDAO.getBanco().getCatalogacaoDAO().validarNumeroOrdem(numOrdem, idCatalogacao)) {
            Nota.alerta("Ordem já cadastrada!");
        } else if (ControleDAO.getBanco().getCatalogacaoDAO().validarEtiquetaRFID(etiquetaRfid, idCatalogacao)) {
            Nota.alerta("Etiqueta RFID já cadastrada!");
        } else if (numPartes == 0) {
            Nota.alerta("N° de partes da catalogação deve ser maior que zero!");
        } else if (cbColecao.getValue() == null) {
            Nota.alerta("Coleção não encontrada!");
        } else if (cbEstratigrafia.getValue() == null) {
            Nota.alerta("Estratigrafia não encontrada!");
        } else if (cbDesignacao.getValue() == null) {
            Nota.alerta("Designação não encontrada!");
        } else {
            Catalogacao catalogacao = new Catalogacao(idCatalogacao, numOrdem, etiquetaRfid, procedencia, procedenciaDetalhes, dimensao, numPartes, localizacao, descricao, data, false, designacao, estratigrafia, colecao);

            if (idCatalogacao == 0) {
                ControleDAO.getBanco().getCatalogacaoDAO().inserir(catalogacao);
                Mensagem.info("Catalogação cadastrada com sucesso!");
            } else {
                ControleDAO.getBanco().getCatalogacaoDAO().editar(catalogacao);
                Mensagem.info("Catalogação atualizada com sucesso!");
            }

            telaCadastro(null);
            sincronizarBase();
        }
    }

    @FXML
    void editar(ActionEvent event) {
        try {
            Catalogacao catalogacao = tbCatalogacao.getSelectionModel().getSelectedItem();
            catalogacao.getClass();

            telaCadastro(null);

            txtEtiquetaRFID.setText(catalogacao.getEtiquetaRFID());
            txtNumOrdem.setText(catalogacao.getNumeroOrdem());
            txtNumPartes.setText("" + catalogacao.getNumeroPartes());
            txtProcedenciaDetalhes.setText(catalogacao.getDetalhesProcedencia());
            txtDescricao.setText(catalogacao.getDescricao());

            cbColecao.setValue(catalogacao.getColecao());
            cbDesignacao.setValue(catalogacao.getDesignacao());
            cbDimensao.setValue(catalogacao.getDimensoes());
            cbEstratigrafia.setValue(catalogacao.getEstratigrafia());
            cbLocalizacao.setValue(catalogacao.getLocalizacao());
            cbProcedencia.setValue(catalogacao.getProcedencia());
            dtEntrada.setValue(Tempo.toDate(catalogacao.getDataEntrada(), "yyyy-MM-dd"));

            lbTitulo.setText("Editar Catalogação");
            menu.selectToggle(menu.getToggles().get(1));

            idCatalogacao = catalogacao.getId();

        } catch (NullPointerException ex) {
            Nota.alerta("Selecione um catalogação na tabela para edição!");
        }
    }

    @FXML
    void excluir(ActionEvent event) {
        try {
            Catalogacao catalogacao = tbCatalogacao.getSelectionModel().getSelectedItem();

            Dialogo.Resposta response = Mensagem.confirmar("Excluir catalogação " + catalogacao.getNumeroOrdem() + " ?");

            if (response == Dialogo.Resposta.YES) {
                ControleDAO.getBanco().getCatalogacaoDAO().excluir(catalogacao.getId());
                sincronizarBase();
                tabela();
            }

            tbCatalogacao.getSelectionModel().clearSelection();

        } catch (NullPointerException ex) {
            Mensagem.alerta("Selecione catalogação na tabela para exclusão!");
        }
    }

    @FXML
    public void initialize() {
        telaCadastro(null);

        Grupo.notEmpty(menu);
        Mascara.numerico(txtNumPartes);

        sincronizarBase();
        combo();
        comboFixos();

        txtPesquisar.textProperty().addListener((obs, old, novo) -> {
            filtrar(novo, FXCollections.observableArrayList(listaCatalogacao));
        });
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void config(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);
        Modulo.visualizacao(false, btExcluir, btSalvar, btEditar, telaCadastro, telaEdicao, txtPesquisar);

        legenda.setText(msg);//mensagem legenda
        tbCatalogacao.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));

        idCatalogacao = 0;
    }

    /**
     * Sincronizar dados com banco de dados
     */
    public void sincronizarBase() {
        listaCatalogacao = ControleDAO.getBanco().getCatalogacaoDAO().listar();
    }

    /**
     * Popular comb box de tipos de catalogação
     */
    private void comboFixos() {
        Combo.popular(cbLocalizacao, "Exposição", "Reserva Técnica", "Emprestado", "Exposição e Reserva Técnica", "Outro");
        Combo.popular(cbProcedencia, "Doação", "Apreensão", "Coleta", "Escavação", "Desconhecida", "Outro");
        Combo.popular(cbDimensao, "1D", "2D", "3D", "Outro");
    }

    /**
     * Popular comb box de tipos de catalogação
     */
    public void combo() {
        Combo.popular(cbColecao, ControleDAO.getBanco().getColecaoDAO().combo());
        Combo.popular(cbEstratigrafia, ControleDAO.getBanco().getEstratigrafiaDAO().combo());
        Combo.popular(cbDesignacao, ControleDAO.getBanco().getDesignacaoDAO().combo());
    }

    /**
     * Mapear dados objetos para inserção dos dados na tabela
     */
    private void tabela() {
        ObservableList data = FXCollections.observableArrayList(listaCatalogacao);

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNumOrdem.setCellValueFactory(new PropertyValueFactory<>("numeroOrdem"));
        colEtiquetaRFID.setCellValueFactory(new PropertyValueFactory<>("etiquetaRFID"));
        colNumPartes.setCellValueFactory(new PropertyValueFactory<>("numeroPartes"));
        colDimensao.setCellValueFactory(new PropertyValueFactory<>("dimensoes"));
        colDataEntrada.setCellValueFactory(new PropertyValueFactory<>("dataEntrada"));
        colLocalizacao.setCellValueFactory(new PropertyValueFactory<>("localizacao"));
        colProcedencia.setCellValueFactory(new PropertyValueFactory<>("procedencia"));
        colProcedenciaDetalhes.setCellValueFactory(new PropertyValueFactory<>("detalhesProcedencia"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        colDataEntrada.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Catalogacao, LocalDate>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Catalogacao, LocalDate> obj) {
                return new SimpleStringProperty(obj.getValue().getDataEntrada());
            }
        });
        colEmprestimo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Catalogacao, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Catalogacao, String> obj) {
                return new SimpleStringProperty(obj.getValue().isEmprestimo() ? "Ativo" : "Inativo");
            }
        });
        colColecao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Catalogacao, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Catalogacao, String> obj) {
                return new SimpleStringProperty(obj.getValue().getColecao().getNome());
            }
        });
        colDesignacao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Catalogacao, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Catalogacao, String> obj) {
                return new SimpleStringProperty(obj.getValue().getDesignacao().getGenero());
            }
        });
        colEstratigrafia.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Catalogacao, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Catalogacao, String> obj) {
                return new SimpleStringProperty(obj.getValue().getEstratigrafia().getFormacao());
            }
        });

        tbCatalogacao.setItems(data);
    }

    /**
     * Campo de pesquisar para filtrar dados na tabela
     */
    private void filtrar(String valor, ObservableList<Catalogacao> listaCatalogacao) {

        FilteredList<Catalogacao> dadosFiltrados = new FilteredList<>(listaCatalogacao, catalogacao -> true);
        dadosFiltrados.setPredicate(catalogacao -> {

            if (valor == null || valor.isEmpty()) {
                return true;
            } else if (catalogacao.getColecao().getNome().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (catalogacao.getDesignacao().getGenero().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (catalogacao.getDimensoes().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (catalogacao.getEstratigrafia().getFormacao().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (catalogacao.getEtiquetaRFID().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (catalogacao.getLocalizacao().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (catalogacao.getNumeroOrdem().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (catalogacao.getProcedencia().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            }

            return false;
        });

        SortedList<Catalogacao> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbCatalogacao.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de catalogações encontradas");

        tbCatalogacao.setItems(dadosOrdenados);
    }

    /**
     * Limpar campos textfield cadastro de coleções
     */
    private void limpar() {
        Campo.limpar(txtEtiquetaRFID, txtNumOrdem, txtNumPartes, txtProcedenciaDetalhes);
        Campo.limpar(txtDescricao);
    }

}

package br.com.museuid.controller;

import br.com.museuid.banco.controle.ControleDAO;
import br.com.museuid.model.Visitante;
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

public class VisitanteController extends AnchorPane {

    private List<Visitante> listaVisitante;
    private int idVisitante = 0;

    @FXML
    private GridPane telaCadastro;
    @FXML
    private TableColumn colCidade;
    @FXML
    private TableColumn colFuncao;
    @FXML
    private TableColumn colEstado;
    @FXML
    private TextField txtNome;
    @FXML
    private Button btSalvar;
    @FXML
    private TextField txtPais;
    @FXML
    private TableColumn colDataVisitante;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private TableColumn colTipo;
    @FXML
    private TextField txtCidade;
    @FXML
    private TableColumn colDescricao;
    @FXML
    private Button btEditar;
    @FXML
    private TableView<Visitante> tbVisitante;
    @FXML
    private TableColumn colId;
    @FXML
    private TextField txtEstado;
    @FXML
    private AnchorPane telaEdicao;
    @FXML
    private DatePicker dtVisita;
    @FXML
    private Label legenda;
    @FXML
    private Button btExcluir;
    @FXML
    private TextArea txtDescricao;
    @FXML
    private ToggleGroup menu;
    @FXML
    private TableColumn colNome;
    @FXML
    private TableColumn colPais;
    @FXML
    private ComboBox<String> cbTipo;
    @FXML
    private Label lbTitulo;
    @FXML
    private TextField txtFuncao;

    public VisitanteController() {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("../view/visitante.fxml"));

            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();

        } catch (IOException ex) {
            Mensagem.erro("Erro ao carregar tela visitantes! \n" + ex);
            ex.printStackTrace();
        }
    }

    @FXML
    void telaCadastro(ActionEvent event) {
        configTela("Cadastrar Visitantes", "Campos obrigatórios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        limparCampos();
    }

    @FXML
    void telaEdicao(ActionEvent event) {
        configTela("Editar Visitantes", "Quantidade de visitantes encontrados", 1);
        Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
        tabela();
    }

    @FXML
    void telaExcluir(ActionEvent event) {
        configTela("Excluir Visitantes", "Quantidade de visitantes encontrados", 2);
        Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
        tabela();
    }

    @FXML
    void salvar(ActionEvent event) {

        boolean vazio = Campo.noEmpty(txtCidade, txtEstado, txtFuncao, txtNome, txtPais);

        String nome = txtNome.getText();
        String funcao = txtFuncao.getText();
        String cidade = txtCidade.getText();
        String estado = txtEstado.getText();
        String pais = txtPais.getText();
        String descricao = txtDescricao.getText();

        String tipo = cbTipo.getValue();
        LocalDate data = dtVisita.getValue();

        if (vazio) {
            Nota.alerta("Preencher campos vazios!");
        } else if (cbTipo.getValue() == null) {
            Nota.alerta("Tipo visitante não encontrado não encontrada!");
        } else {
            Visitante visitante = new Visitante(idVisitante, nome, funcao, cidade, estado, pais, data, descricao, tipo);

            if (idVisitante == 0) {
                ControleDAO.getBanco().getVisitanteDAO().inserir(visitante);
                Mensagem.info("Visitante cadastrado com sucesso!");
            } else {
                ControleDAO.getBanco().getVisitanteDAO().editar(visitante);
                Mensagem.info("Visitante atualizado com sucesso!");
            }

            telaCadastro(null);
            sincronizarBase();
        }
    }

    @FXML
    void editar(ActionEvent event) {
        try {
            Visitante visitante = tbVisitante.getSelectionModel().getSelectedItem();
            visitante.getClass();

            telaCadastro(null);

            txtNome.setText(visitante.getNome());
            txtCidade.setText(visitante.getCidade());
            txtPais.setText(visitante.getPais());
            txtEstado.setText(visitante.getEstado());
            txtFuncao.setText(visitante.getFuncao());
            txtDescricao.setText(visitante.getDescricao());

            cbTipo.setValue(visitante.getTipo());
            dtVisita.setValue(visitante.getDataVisita());

            lbTitulo.setText("Editar Visitante");
            menu.selectToggle(menu.getToggles().get(1));

            idVisitante = visitante.getId();

        } catch (NullPointerException ex) {
            Nota.alerta("Selecione um visitante na tabela para edição!");
        }
    }

    @FXML
    void excluir(ActionEvent event) {
        try {
            Visitante visitante = tbVisitante.getSelectionModel().getSelectedItem();

            Dialogo.Resposta response = Mensagem.confirmar("Excluir visitante " + visitante.getNome() + " ?");

            if (response == Dialogo.Resposta.YES) {
                ControleDAO.getBanco().getVisitanteDAO().excluir(visitante.getId());
                sincronizarBase();
                tabela();
            }

            tbVisitante.getSelectionModel().clearSelection();

        } catch (NullPointerException ex) {
            Mensagem.alerta("Selecione visitante na tabela para exclusão!");
        }
    }

    @FXML
    void initialize() {
        telaCadastro(null);

        Grupo.notEmpty(menu);
        sincronizarBase();
        combos();
        dtVisita.setValue(LocalDate.now());

        txtPesquisar.textProperty().addListener((obs, old, novo) -> {
            filtroPesquisa(novo, FXCollections.observableArrayList(listaVisitante));
        });
    }

    /**
     * Preencher combos tela
     */
    private void combos() {
        ObservableList<String> tipo = FXCollections.observableArrayList("Visitante", "Estrangeiro", "Personalidade", "Pesquisador");
        Combo.popular(cbTipo, tipo);
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void configTela(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);
        Modulo.visualizacao(false, btExcluir, btSalvar, btEditar, telaCadastro, telaEdicao, txtPesquisar);

        legenda.setText(msg);
        tbVisitante.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));

        idVisitante = 0;
    }

    /**
     * Sincronizar dados com banco de dados
     */
    private void sincronizarBase() {
        listaVisitante = ControleDAO.getBanco().getVisitanteDAO().listar();
    }

    /**
     * Mapear dados objetos para inserção dos dados na tabela
     */
    private void tabela() {
        ObservableList data = FXCollections.observableArrayList(listaVisitante);

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colPais.setCellValueFactory(new PropertyValueFactory<>("pais"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colFuncao.setCellValueFactory(new PropertyValueFactory<>("funcao"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colDataVisitante.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Visitante, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Visitante, String> obj) {
                return new SimpleStringProperty(Tempo.toString(obj.getValue().getDataVisita()));
            }
        });

        tbVisitante.setItems(data);
    }

    /**
     * Campo de pesquisar para filtrar dados na tabela
     */
    private void filtroPesquisa(String valor, ObservableList<Visitante> listaVisitante) {

        FilteredList<Visitante> dadosFiltrados = new FilteredList<>(listaVisitante, visitante -> true);
        dadosFiltrados.setPredicate(visitante -> {

            if (valor == null || valor.isEmpty()) {
                return true;
            } else if (visitante.getNome().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (visitante.getEstado().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (visitante.getFuncao().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (visitante.getTipo().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (visitante.getCidade().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (visitante.getPais().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            }

            return false;
        });

        SortedList<Visitante> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbVisitante.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de visitantes encontrados");

        tbVisitante.setItems(dadosOrdenados);
    }

    /**
     * Limpar campos textfield cadastro de coleções
     */
    private void limparCampos() {
        Campo.limpar(txtCidade, txtEstado, txtFuncao, txtNome, txtPais);
        Campo.limpar(txtDescricao);
    }

}

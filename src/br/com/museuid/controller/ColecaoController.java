package br.com.museuid.controller;

import br.com.museuid.banco.controle.ControleDAO;
import br.com.museuid.model.Colecao;
import br.com.museuid.util.*;
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

import java.io.IOException;
import java.util.List;

public class ColecaoController extends AnchorPane {

    private List<Colecao> listaColecao;
    private int idColecao = 0;

    @FXML
    private GridPane telaCadastro;
    @FXML
    private Label legenda;
    @FXML
    private Button btExcluir;
    @FXML
    private TextField txtNome;
    @FXML
    private Button btSalvar;
    @FXML
    private TextArea txtDescricao;
    @FXML
    private ToggleGroup menu;
    @FXML
    private TableColumn colNome;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private Label lbTitulo;
    @FXML
    private TableColumn colDescricao;
    @FXML
    private Button btEditar;
    @FXML
    private TableView<Colecao> tbColecao;
    @FXML
    private TableColumn colId;
    @FXML
    private AnchorPane telaEdicao;

    public ColecaoController() {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("../view/colecao.fxml"));

            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();

        } catch (IOException ex) {
            Mensagem.erro("Erro ao carregar tela coleção! \n" + ex);
        }
    }

    @FXML
    void telaCadastro(ActionEvent event) {
        config("Cadastrar Coleção", "Campos obrigatórios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        limpar();
    }

    @FXML
    void telaEdicao(ActionEvent event) {
        config("Editar Coleção", "Quantidade de coleção encontradas", 1);
        Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
        tabela();
    }

    @FXML
    void telaExcluir(ActionEvent event) {
        config("Excluir Coleção", "Quantidade de coleção encontradas", 2);
        Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
        tabela();
    }

    @FXML
    void salvar(ActionEvent event) {
        boolean vazio = Campo.noEmpty(txtNome);

        String nome = txtNome.getText();
        String descricao = txtDescricao.getText();

        if (vazio) {
            Nota.alerta("Preencher campos vazios!");
        } else if (ControleDAO.getBanco().getColecaoDAO().isColecao(nome, idColecao)) {
            Nota.alerta("Coleção já cadastrada!");
        } else {
            Colecao colecao = new Colecao(idColecao, nome, descricao);

            if (idColecao == 0) {
                ControleDAO.getBanco().getColecaoDAO().inserir(colecao);
                Mensagem.info("Coleção cadastrada com sucesso!");
            } else {
                ControleDAO.getBanco().getColecaoDAO().editar(colecao);
                Mensagem.info("Coleção atualizada com sucesso!");
            }

            telaCadastro(null);
            sincronizarBase();
        }
    }

    @FXML
    void editar(ActionEvent event) {
        try {
            Colecao colecao = tbColecao.getSelectionModel().getSelectedItem();
            colecao.getClass();

            telaCadastro(null);

            txtNome.setText(colecao.getNome());
            txtDescricao.setText(colecao.getDescricao());

            lbTitulo.setText("Editar Coleção");
            menu.selectToggle(menu.getToggles().get(1));

            idColecao = colecao.getId();

        } catch (NullPointerException ex) {
            Nota.alerta("Selecione um coleção na tabela para edição!");
        }
    }

    @FXML
    void excluir(ActionEvent event) {
        try {
            Colecao colecao = tbColecao.getSelectionModel().getSelectedItem();

            Dialogo.Resposta response = Mensagem.confirmar("Excluir coleção " + colecao.getNome() + " ?");

            if (response == Dialogo.Resposta.YES) {
                ControleDAO.getBanco().getColecaoDAO().excluir(colecao.getId());
                sincronizarBase();
                tabela();
            }

            tbColecao.getSelectionModel().clearSelection();

        } catch (NullPointerException ex) {
            Mensagem.alerta("Selecione coleção na tabela para exclusão!");
        }
    }

    @FXML
    public void initialize() {
        telaCadastro(null);

        Grupo.notEmpty(menu);
        sincronizarBase();

        txtPesquisar.textProperty().addListener((obs, old, novo) -> {
            filtro(novo, FXCollections.observableArrayList(listaColecao));
        });
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void config(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);
        Modulo.visualizacao(false, btExcluir, btSalvar, btEditar, telaCadastro, telaEdicao, txtPesquisar);

        legenda.setText(msg);//mensagem legenda
        tbColecao.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));

        idColecao = 0;
    }

    /**
     * Sincronizar dados com banco de dados
     */
    private void sincronizarBase() {
        listaColecao = ControleDAO.getBanco().getColecaoDAO().listar();
    }

    /**
     * Mapear dados objetos para inserção dos dados na tabela
     */
    private void tabela() {
        ObservableList data = FXCollections.observableArrayList(listaColecao);

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        tbColecao.setItems(data);
    }

    /**
     * Campo de pesquisar para filtrar dados na tabela
     */
    private void filtro(String valor, ObservableList<Colecao> listaColecao) {

        FilteredList<Colecao> dadosFiltrados = new FilteredList<>(listaColecao, colecao -> true);
        dadosFiltrados.setPredicate(colecao -> {

            if (valor == null || valor.isEmpty()) {
                return true;
            } else if (colecao.getNome().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            }

            return false;
        });

        SortedList<Colecao> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbColecao.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de coleção encontradas");

        tbColecao.setItems(dadosOrdenados);
    }

    /**
     * Limpar campos textfield cadastro de coleções
     */
    private void limpar() {
        Campo.limpar(txtNome);
        Campo.limpar(txtDescricao);
    }

}

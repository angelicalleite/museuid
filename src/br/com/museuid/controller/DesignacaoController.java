package br.com.museuid.controller;

import br.com.museuid.banco.controle.ControleDAO;
import br.com.museuid.model.Designacao;
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

public class DesignacaoController extends AnchorPane {

    private List<Designacao> listaDesignacao;
    private int idDesignacao = 0;

    @FXML
    private GridPane telaCadastro;
    @FXML
    private Label legenda;
    @FXML
    private TableColumn colClasse;
    @FXML
    private TextField txtEspecie;
    @FXML
    private Button btExcluir;
    @FXML
    private Button btSalvar;
    @FXML
    private TextArea txtDescricao;
    @FXML
    private TextField txtClasse;
    @FXML
    private TableColumn colOrdem;
    @FXML
    private ToggleGroup menu;
    @FXML
    private TextField txtOrdem;
    @FXML
    private TextField txtGenero;
    @FXML
    private TableColumn colFamilia;
    @FXML
    private TableColumn colEspecie;
    @FXML
    private TextField txtFamilia;
    @FXML
    private TableColumn colGenero;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private Label lbTitulo;
    @FXML
    private TableColumn colDescricao;
    @FXML
    private Button btEditar;
    @FXML
    private TableColumn colId;
    @FXML
    private TableView<Designacao> tbDesignacao;
    @FXML
    private AnchorPane telaEdicao;

    public DesignacaoController() {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("../view/designacao.fxml"));

            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();

        } catch (IOException ex) {
            Mensagem.erro("Erro ao carregar tela designação! \n" + ex);
        }
    }

    @FXML
    void telaCadastro(ActionEvent event) {
        config("Cadastrar Designação", "Campos obrigatórios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        limpar();
    }

    @FXML
    void telaEdicao(ActionEvent event) {
        config("Editar Designação", "Quantidade de designações encontradas", 1);
        Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
        tabela();
    }

    @FXML
    void telaExcluir(ActionEvent event) {
        config("Excluir Designação", "Quantidade de designações encontradas", 2);
        Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
        tabela();
    }

    @FXML
    void salvar(ActionEvent event) {
        boolean vazio = Campo.noEmpty(txtGenero);

        String genero = txtGenero.getText();
        String famalia = txtFamilia.getText();
        String classe = txtClasse.getText();
        String ordem = txtOrdem.getText();
        String especie = txtEspecie.getText();
        String descricao = txtDescricao.getText();

        if (vazio) {
            Nota.alerta("Preencher campos vazios!");
        } else if (ControleDAO.getBanco().getDesignacaoDAO().isDesignacao(genero, idDesignacao)) {
            Nota.alerta("Genêro já cadastrada!");
        } else {
            Designacao designacao = new Designacao(idDesignacao, genero, especie, famalia, ordem, classe, descricao);

            if (idDesignacao == 0) {
                ControleDAO.getBanco().getDesignacaoDAO().inserir(designacao);
                //Mensagem.info("Designação cadastrada com sucesso!");
            } else {
                ControleDAO.getBanco().getDesignacaoDAO().editar(designacao);
                Mensagem.info("Designação atualizada com sucesso!");
            }

            telaCadastro(null);
            sincronizarBase();
        }
    }

    @FXML
    void editar(ActionEvent event) {
        try {
            Designacao designacao = tbDesignacao.getSelectionModel().getSelectedItem();
            designacao.getClass();

            telaCadastro(null);

            txtGenero.setText(designacao.getGenero());
            txtClasse.setText(designacao.getClasse());
            txtEspecie.setText(designacao.getEspecie());
            txtFamilia.setText(designacao.getFamilia());
            txtOrdem.setText(designacao.getOrdem());
            txtDescricao.setText(designacao.getDescricao());

            lbTitulo.setText("Editar Designação");
            menu.selectToggle(menu.getToggles().get(1));

            idDesignacao = designacao.getId();

        } catch (NullPointerException ex) {
            Nota.alerta("Selecione um designação na tabela para edição!");
        }
    }

    @FXML
    void excluir(ActionEvent event) {
        try {
            Designacao designacao = tbDesignacao.getSelectionModel().getSelectedItem();

            Dialogo.Resposta response = Mensagem.confirmar("Excluir designação " + designacao.getGenero() + " ?");

            if (response == Dialogo.Resposta.YES) {
                ControleDAO.getBanco().getDesignacaoDAO().excluir(designacao.getId());
                sincronizarBase();
                tabela();
            }

            tbDesignacao.getSelectionModel().clearSelection();

        } catch (NullPointerException ex) {
            Mensagem.alerta("Selecione designação na tabela para exclusão!");
        }
    }

    @FXML
    public void initialize() {
        telaCadastro(null);

        Grupo.notEmpty(menu);
        sincronizarBase();

        txtPesquisar.textProperty().addListener((obs, old, novo) -> {
            filtro(novo, FXCollections.observableArrayList(listaDesignacao));
        });
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void config(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);
        Modulo.visualizacao(false, btExcluir, btSalvar, btEditar, telaCadastro, telaEdicao, txtPesquisar);

        legenda.setText(msg);
        tbDesignacao.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));

        idDesignacao = 0;
    }

    /**
     * Sincronizar dados com banco de dados
     */
    private void sincronizarBase() {
        listaDesignacao = ControleDAO.getBanco().getDesignacaoDAO().listar();
    }

    /**
     * Mapear dados objetos para inserção dos dados na tabela
     */
    private void tabela() {
        ObservableList data = FXCollections.observableArrayList(listaDesignacao);

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colClasse.setCellValueFactory(new PropertyValueFactory<>("classe"));
        colFamilia.setCellValueFactory(new PropertyValueFactory<>("familia"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colOrdem.setCellValueFactory(new PropertyValueFactory<>("ordem"));
        colEspecie.setCellValueFactory(new PropertyValueFactory<>("especie"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        tbDesignacao.setItems(data);
    }

    /**
     * Campo de pesquisar para filtrar dados na tabela
     */
    private void filtro(String valor, ObservableList<Designacao> listaDesignacao) {

        FilteredList<Designacao> dadosFiltrados = new FilteredList<>(listaDesignacao, designacao -> true);
        dadosFiltrados.setPredicate(designacao -> {

            if (valor == null || valor.isEmpty()) {
                return true;
            } else if (designacao.getEspecie().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (designacao.getClasse().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (designacao.getFamilia().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (designacao.getGenero().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (designacao.getOrdem().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            }

            return false;
        });

        SortedList<Designacao> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbDesignacao.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de designação encontradas");

        tbDesignacao.setItems(dadosOrdenados);
    }

    /**
     * Limpar campos textfield cadastro de coleções
     */
    private void limpar() {
        Campo.limpar(txtClasse, txtEspecie, txtFamilia, txtGenero, txtOrdem);
        Campo.limpar(txtDescricao);
    }

}

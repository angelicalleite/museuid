package br.com.museuid.controller;

import br.com.museuid.banco.controle.ControleDAO;
import br.com.museuid.model.Instituicao;
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

public class InstituicaoController extends AnchorPane {

    private List<Instituicao> listaInstituicoes;
    private int idInstituicao = 0;

    @FXML
    private GridPane telaCadastro;
    @FXML
    private TableColumn colCidade;
    @FXML
    private TableColumn colEstado;
    @FXML
    private TextField txtNome;
    @FXML
    private TableColumn colRepresentante;
    @FXML
    private Button btSalvar;
    @FXML
    private TextField txtPais;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private TextField txtCidade;
    @FXML
    private TableColumn colDescricao;
    @FXML
    private Button btEditar;
    @FXML
    private TableColumn colId;
    @FXML
    private TextField txtEstado;
    @FXML
    private AnchorPane telaEdicao;
    @FXML
    private TableColumn colTelefone;
    @FXML
    private Label legenda;
    @FXML
    private Button btExcluir;
    @FXML
    private TextField txtTelefone;
    @FXML
    private TextArea txtDescricao;
    @FXML
    private ToggleGroup menu;
    @FXML
    private TableView<Instituicao> tbInstituicao;
    @FXML
    private TableColumn colNome;
    @FXML
    private TableColumn colPais;
    @FXML
    private TextField txtRepresentante;

    @FXML
    private Label lbTitulo;

    public InstituicaoController() {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("../view/instituicao.fxml"));

            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();

        } catch (IOException ex) {
            Mensagem.erro("Erro ao carregar tela instituicao! \n" + ex);
        }
    }

    @FXML
    void telaCadastro(ActionEvent event) {
        configTela("Cadastrar Instituição", "Campos obrigatórios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        limparCampos();
    }

    @FXML
    void telaEdicao(ActionEvent event) {
        configTela("Editar Instituição", "Quantidade de instituições encontrados", 1);
        Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
        tabela();
    }

    @FXML
    void telaExcluir(ActionEvent event) {
        configTela("Excluir Instituição", "Quantidade de instituições encontrados", 2);
        Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
        tabela();
    }

    @FXML
    void salvar(ActionEvent event) {

        boolean vazio = Campo.noEmpty(txtCidade, txtRepresentante, txtEstado, txtNome, txtPais);

        String nome = txtNome.getText();
        String representante = txtRepresentante.getText();
        String telefone = txtTelefone.getText();
        String cidade = txtCidade.getText();
        String estado = txtEstado.getText();
        String pais = txtPais.getText();
        String descricao = txtDescricao.getText();

        if (vazio) {
            Nota.alerta("Preencher campos vazios!");
        } else {
            Instituicao instituicao = new Instituicao(idInstituicao, nome, representante, telefone, cidade, estado, pais, descricao);

            if (idInstituicao == 0) {
                ControleDAO.getBanco().getInstituicaoDAO().inserir(instituicao);
                Mensagem.info("Instituição cadastrado com sucesso!");
            } else {
                ControleDAO.getBanco().getInstituicaoDAO().editar(instituicao);
                Mensagem.info("Instituição atualizado com sucesso!");
            }

            telaCadastro(null);
            sincronizarBase();
        }
    }

    @FXML
    void editar(ActionEvent event) {
        try {
            Instituicao instituicao = tbInstituicao.getSelectionModel().getSelectedItem();
            instituicao.getClass();

            telaCadastro(null);

            txtNome.setText(instituicao.getNome());
            txtCidade.setText(instituicao.getCidade());
            txtPais.setText(instituicao.getPais());
            txtEstado.setText(instituicao.getEstado());
            txtRepresentante.setText(instituicao.getRepresentante());
            txtTelefone.setText(instituicao.getTelefone());
            txtDescricao.setText(instituicao.getDescricao());

            lbTitulo.setText("Editar Instituição");
            menu.selectToggle(menu.getToggles().get(1));

            idInstituicao = instituicao.getId();

        } catch (NullPointerException ex) {
            Nota.alerta("Selecione um instituição na tabela para edição!");
        }
    }

    @FXML
    void excluir(ActionEvent event) {
        try {
            Instituicao instituicao = tbInstituicao.getSelectionModel().getSelectedItem();

            Dialogo.Resposta response = Mensagem.confirmar("Excluir instituição " + instituicao.getNome() + " ?");

            if (response == Dialogo.Resposta.YES) {
                ControleDAO.getBanco().getInstituicaoDAO().excluir(instituicao.getId());
                sincronizarBase();
                tabela();
            }

            tbInstituicao.getSelectionModel().clearSelection();

        } catch (NullPointerException ex) {
            Mensagem.alerta("Selecione instituição na tabela para exclusão!");
        }
    }

    @FXML
    void initialize() {
        telaCadastro(null);

        Grupo.notEmpty(menu);
        sincronizarBase();

        txtPesquisar.textProperty().addListener((obs, old, novo) -> {
            filtroPesquisa(novo, FXCollections.observableArrayList(listaInstituicoes));
        });
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void configTela(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);
        Modulo.visualizacao(false, btExcluir, btSalvar, btEditar, telaCadastro, telaEdicao, txtPesquisar);

        legenda.setText(msg);
        tbInstituicao.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));

        idInstituicao = 0;
    }

    /**
     * Sincronizar dados com banco de dados
     */
    private void sincronizarBase() {
        listaInstituicoes = ControleDAO.getBanco().getInstituicaoDAO().combo();
    }

    /**
     * Mapear dados objetos para inserção dos dados na tabela
     */
    private void tabela() {
        ObservableList data = FXCollections.observableArrayList(listaInstituicoes);

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colPais.setCellValueFactory(new PropertyValueFactory<>("pais"));
        colRepresentante.setCellValueFactory(new PropertyValueFactory<>("representante"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        tbInstituicao.setItems(data);
    }

    /**
     * Campo de pesquisar para filtrar dados na tabela
     */
    private void filtroPesquisa(String valor, ObservableList<Instituicao> listaInstituicao) {

        FilteredList<Instituicao> dadosFiltrados = new FilteredList<>(listaInstituicao, instituicao -> true);
        dadosFiltrados.setPredicate(instituicao -> {

            if (valor == null || valor.isEmpty()) {
                return true;
            } else if (instituicao.getNome().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (instituicao.getRepresentante().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (instituicao.getTelefone().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (instituicao.getEstado().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (instituicao.getCidade().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (instituicao.getPais().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            }

            return false;
        });

        SortedList<Instituicao> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbInstituicao.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de instituições encontrados");

        tbInstituicao.setItems(dadosOrdenados);
    }

    /**
     * Limpar campos textfield cadastro de coleções
     */
    private void limparCampos() {
        Campo.limpar(txtNome, txtRepresentante, txtTelefone, txtCidade, txtEstado, txtPais);
        Campo.limpar(txtDescricao);
    }

}

package br.com.museuid.controller;

import br.com.museuid.banco.controle.ControleDAO;
import br.com.museuid.model.Estratigrafia;
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

public class EstratigrafiaController extends AnchorPane {

    private List<Estratigrafia> listaEstratigrafia;
    private int idEstratigrafia = 0;

    @FXML
    private GridPane telaCadastro;
    @FXML
    private Label legenda;
    @FXML
    private Button btExcluir;
    @FXML
    private TableView<Estratigrafia> tbEstratigrafia;
    @FXML
    private TextField txtFormacao;
    @FXML
    private Button btSalvar;
    @FXML
    private TextArea txtDescricao;
    @FXML
    private ToggleGroup menu;
    @FXML
    private TableColumn colFormacao;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private TableColumn colGrupo;
    @FXML
    private Label lbTitulo;
    @FXML
    private TableColumn colDescricao;
    @FXML
    private Button btEditar;
    @FXML
    private TableColumn colId;
    @FXML
    private TextField txtGrupo;
    @FXML
    private AnchorPane telaEdicao;

    public EstratigrafiaController() {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("../view/estratigrafia.fxml"));

            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();

        } catch (IOException ex) {
            Mensagem.erro("Erro ao carregar tela estratigrafia! \n" + ex);
        }
    }

    @FXML
    void telaCadastro(ActionEvent event) {
        config("Cadastrar Estratigrafia", "Campos obrigatórios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        limpar();
    }

    @FXML
    void telaEdicao(ActionEvent event) {
        config("Editar Estratigrafia", "Quantidade de estratigrafias encontradas", 1);
        Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
        tabela();
    }

    @FXML
    void telaExcluir(ActionEvent event) {
        config("Excluir Estratigrafia", "Quantidade de estratigrafias encontradas", 2);
        Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
        tabela();
    }

    @FXML
    void salvar(ActionEvent event) {

        boolean vazio = Campo.noEmpty(txtFormacao, txtGrupo);

        String formacao = txtFormacao.getText();
        String grupo = txtGrupo.getText().replaceAll(" ", "").trim();
        String descricao = txtDescricao.getText();

        if (vazio) {
            Nota.alerta("Preencher campos vazios!");
        } else if (ControleDAO.getBanco().getEstratigrafiaDAO().isEstratigrafia(formacao, idEstratigrafia)) {
            Nota.alerta("Formação já cadastrada!");
        } else {
            Estratigrafia estratigrafia = new Estratigrafia(idEstratigrafia, formacao, grupo, descricao);

            if (idEstratigrafia == 0) {
                ControleDAO.getBanco().getEstratigrafiaDAO().inserir(estratigrafia);
                Mensagem.info("Estratigrafia cadastrada com sucesso!");
            } else {
                ControleDAO.getBanco().getEstratigrafiaDAO().editar(estratigrafia);
                Mensagem.info("Estratigrafia atualizada com sucesso!");
            }

            telaCadastro(null);
            sincronizarBase();
        }
    }

    @FXML
    void editar(ActionEvent event) {
        try {
            Estratigrafia estratigrafia = tbEstratigrafia.getSelectionModel().getSelectedItem();
            estratigrafia.getClass();

            telaCadastro(null);

            txtFormacao.setText(estratigrafia.getFormacao());
            txtGrupo.setText(estratigrafia.getGrupo());
            txtDescricao.setText(estratigrafia.getDescricao());

            lbTitulo.setText("Editar Estratigrafia");
            menu.selectToggle(menu.getToggles().get(1));

            idEstratigrafia = estratigrafia.getId();

        } catch (NullPointerException ex) {
            Nota.alerta("Selecione um estratigrafia na tabela para edição!");
        }
    }

    @FXML
    void excluir(ActionEvent event) {
        try {
            Estratigrafia estratigrafia = tbEstratigrafia.getSelectionModel().getSelectedItem();

            Dialogo.Resposta response = Mensagem.confirmar("Excluir estratigrafia " + estratigrafia.getFormacao() + " ?");

            if (response == Dialogo.Resposta.YES) {
                ControleDAO.getBanco().getEstratigrafiaDAO().excluir(estratigrafia.getId());
                sincronizarBase();
                tabela();
            }

            tbEstratigrafia.getSelectionModel().clearSelection();

        } catch (NullPointerException ex) {
            Mensagem.alerta("Selecione estratigrafia na tabela para exclusão!");
        }
    }

    @FXML
    public void initialize() {
        telaCadastro(null);

        Grupo.notEmpty(menu);
        sincronizarBase();

        txtPesquisar.textProperty().addListener((obs, old, novo) -> {
            filtro(novo, FXCollections.observableArrayList(listaEstratigrafia));
        });
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void config(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);
        Modulo.visualizacao(false, btExcluir, btSalvar, btEditar, telaCadastro, telaEdicao, txtPesquisar);

        legenda.setText(msg);
        tbEstratigrafia.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));

        idEstratigrafia = 0;
    }

    /**
     * Sincronizar dados com banco de dados
     */
    private void sincronizarBase() {
        listaEstratigrafia = ControleDAO.getBanco().getEstratigrafiaDAO().listar();
    }

    /**
     * Mapear dados objetos para inserção dos dados na tabela
     */
    private void tabela() {
        ObservableList data = FXCollections.observableArrayList(listaEstratigrafia);

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFormacao.setCellValueFactory(new PropertyValueFactory<>("formacao"));
        colGrupo.setCellValueFactory(new PropertyValueFactory<>("grupo"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        tbEstratigrafia.setItems(data);
    }

    /**
     * Campo de pesquisar para filtrar dados na tabela
     */
    private void filtro(String valor, ObservableList<Estratigrafia> listaEstratigrafia) {

        FilteredList<Estratigrafia> dadosFiltrados = new FilteredList<>(listaEstratigrafia, estratigrafia -> true);
        dadosFiltrados.setPredicate(estratigrafia -> {

            if (valor == null || valor.isEmpty()) {
                return true;
            } else if (estratigrafia.getFormacao().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (estratigrafia.getGrupo().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            }

            return false;
        });

        SortedList<Estratigrafia> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbEstratigrafia.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de estratigrafias encontradas");

        tbEstratigrafia.setItems(dadosOrdenados);
    }

    /**
     * Limpar campos textfield cadastro de estratigrafias
     */
    private void limpar() {
        Campo.limpar(txtFormacao, txtGrupo);
        Campo.limpar(txtDescricao);
    }

}

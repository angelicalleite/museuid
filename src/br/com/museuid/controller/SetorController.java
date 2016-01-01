package br.com.museuid.controller;

import br.com.museuid.banco.controle.ControleDAO;
import br.com.museuid.model.Organizacao;
import br.com.museuid.model.Setor;
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
import java.util.List;

public class SetorController extends AnchorPane {

    private List<Setor> listaSetor;
    private int idSetor = 0;

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
    private TableView<Setor> tbSetor;
    @FXML
    private TableColumn colOrganizacao;
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
    private ComboBox<Organizacao> cbOrganizacao;
    @FXML
    private TableColumn colSetor;
    @FXML
    private AnchorPane telaEdicao;

    public SetorController() {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("../view/setor.fxml"));

            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();

        } catch (IOException ex) {
            Mensagem.erro("Erro ao carregar tela setor da localização! \n" + ex);
        }
    }

    @FXML
    void telaCadastro(ActionEvent event) {
        config("Cadastrar Setor", "Campos obrigatórios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        limpar();
    }

    @FXML
    void telaEdicao(ActionEvent event) {
        config("Editar Setor", "Quantidade de setores encontrados", 1);
        Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
        tabela();
    }

    @FXML
    void telaExcluir(ActionEvent event) {
        config("Excluir Setor", "Quantidade de setores encontrados", 2);
        Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
        tabela();
    }

    @FXML
    void salvar(ActionEvent event) {

        boolean vazio = Campo.noEmpty(txtNome);

        String nome = txtNome.getText();
        String descricao = txtDescricao.getText();

        Organizacao organizacao = cbOrganizacao.getValue();

        if (vazio) {
            Nota.alerta("Preencher campos vazios!");
        } else if (cbOrganizacao.getValue() == null) {
            Nota.alerta("Organização do setor não encontrada!");
        } else {
            Setor setor = new Setor(idSetor, nome, descricao, organizacao);

            if (idSetor == 0) {
                ControleDAO.getBanco().getSetorDAO().inserir(setor);
                Mensagem.info("Setor cadastrada com sucesso!");
            } else {
                ControleDAO.getBanco().getSetorDAO().editar(setor);
                Mensagem.info("Setor atualizada com sucesso!");
            }

            telaCadastro(null);
            sincronizarBase();
        }
    }

    @FXML
    void editar(ActionEvent event) {
        try {
            Setor setor = tbSetor.getSelectionModel().getSelectedItem();
            setor.getClass();

            telaCadastro(null);

            txtNome.setText(setor.getNome());
            cbOrganizacao.setValue(setor.getOrganizacao());
            txtDescricao.setText(setor.getDescricao());

            lbTitulo.setText("Editar Setor");
            menu.selectToggle(menu.getToggles().get(1));

            idSetor = setor.getId();

        } catch (NullPointerException ex) {
            Nota.alerta("Selecione um setor na tabela para edição!");
        }
    }

    @FXML
    void excluir(ActionEvent event) {
        try {
            Setor setor = tbSetor.getSelectionModel().getSelectedItem();

            Dialogo.Resposta response = Mensagem.confirmar("Excluir setor " + setor.getNome() + " ?");

            if (response == Dialogo.Resposta.YES) {
                ControleDAO.getBanco().getSetorDAO().excluir(setor.getId());
                sincronizarBase();
                tabela();
            }

            tbSetor.getSelectionModel().clearSelection();

        } catch (NullPointerException ex) {
            Mensagem.alerta("Selecione setor na tabela para exclusão!");
        }
    }

    @FXML
    public void initialize() {
        telaCadastro(null);

        Grupo.notEmpty(menu);
        sincronizarBase();
        combos();

        txtPesquisar.textProperty().addListener((obs, old, novo) -> {
            filtro(novo, FXCollections.observableArrayList(listaSetor));
        });
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void config(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);
        Modulo.visualizacao(false, btExcluir, btSalvar, btEditar, telaCadastro, telaEdicao, txtPesquisar);

        legenda.setText(msg);//mensagem legenda
        tbSetor.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));

        idSetor = 0;
    }

    /**
     * Preencher combos tela
     */
    public void combos() {
        Combo.popular(cbOrganizacao, ControleDAO.getBanco().getOrganizacaoDAO().combo());
    }

    /**
     * Sincronizar dados com banco de dados
     */
    private void sincronizarBase() {
        listaSetor = ControleDAO.getBanco().getSetorDAO().listar();
    }

    /**
     * Mapear dados objetos para inserção dos dados na tabela
     */
    private void tabela() {
        ObservableList data = FXCollections.observableArrayList(listaSetor);

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSetor.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colOrganizacao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Setor, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Setor, String> obj) {
                return new SimpleStringProperty(obj.getValue().getOrganizacao().getNome());
            }
        });
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        tbSetor.setItems(data);
    }

    /**
     * Campo de pesquisar para filtrar dados na tabela
     */
    private void filtro(String valor, ObservableList<Setor> listaSetor) {

        FilteredList<Setor> dadosFiltrados = new FilteredList<>(listaSetor, setor -> true);
        dadosFiltrados.setPredicate(setor -> {

            if (valor == null || valor.isEmpty()) {
                return true;
            } else if (setor.getNome().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (setor.getOrganizacao().getNome().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            }

            return false;
        });

        SortedList<Setor> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbSetor.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de setores encontradas");

        tbSetor.setItems(dadosOrdenados);
    }

    /**
     * Limpar campos textfield cadastro de coleções
     */
    private void limpar() {
        Campo.limpar(txtNome);
        Campo.limpar(txtDescricao);
    }

}

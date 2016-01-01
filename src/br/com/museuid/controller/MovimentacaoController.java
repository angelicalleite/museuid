package br.com.museuid.controller;

import br.com.museuid.banco.controle.ControleDAO;
import br.com.museuid.model.Movimentacao;
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

public class MovimentacaoController extends AnchorPane {

    private List<Movimentacao> listaMovimentacao;
    private int idMovimentacao = 0;

    @FXML
    private GridPane telaCadastro;
    @FXML
    private TextField txtResponsavel;
    @FXML
    private Label legenda;
    @FXML
    private Button btExcluir;
    @FXML
    private TableColumn colOrigem;
    @FXML
    private Button btSalvar;
    @FXML
    private TextField txtObjetos;
    @FXML
    private TextArea txtDescricao;
    @FXML
    private ToggleGroup menu;
    @FXML
    private TableColumn colResponsavel;
    @FXML
    private ComboBox<String> cbTipo;
    @FXML
    private TableColumn colData;
    @FXML
    private TextField txtOrigem;
    @FXML
    private DatePicker dtMovimentacao;
    @FXML
    private TableColumn colDestino;
    @FXML
    private TableColumn colObjetos;
    @FXML
    private TextField txtDestino;
    @FXML
    private TableView<Movimentacao> tbMovimentacao;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private TableColumn colTipo;
    @FXML
    private Label lbTitulo;
    @FXML
    private TableColumn colDescricao;
    @FXML
    private Button btEditar;
    @FXML
    private TableColumn colId;
    @FXML
    private AnchorPane telaEdicao;

    public MovimentacaoController() {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("../view/movimentacao.fxml"));

            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();

        } catch (IOException ex) {
            Mensagem.erro("Erro ao carregar tela movimentações! \n" + ex);
        }
    }

    @FXML
    void telaCadastro(ActionEvent event) {
        config("Cadastrar Movimentação", "Campos obrigatórios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        dtMovimentacao.setValue(LocalDate.now());
        limpar();
    }

    @FXML
    void telaEdicao(ActionEvent event) {
        config("Editar Movimentação", "Quantidade de movimentações encontradas", 1);
        Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
        tabela();
    }

    @FXML
    void telaExcluir(ActionEvent event) {
        config("Excluir Movimentação", "Quantidade de movimentações encontradas", 2);
        Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
        tabela();
    }

    @FXML
    void salvar(ActionEvent event) {
        boolean vazio = Campo.noEmpty(txtDestino, txtResponsavel, txtOrigem, txtObjetos);

        String responsavel = txtResponsavel.getText();
        String objetos = txtObjetos.getText();
        String destino = txtDestino.getText();
        String origem = txtOrigem.getText();
        String descricao = txtDescricao.getText();

        String tipo = cbTipo.getValue();
        LocalDate data = dtMovimentacao.getValue();

        if (vazio) {
            Nota.alerta("Preencher campos vazios!");
        } else if (cbTipo.getValue() == null) {
            Nota.alerta("Tipo da Movimentação não encontrada!");
        } else {
            Movimentacao movi = new Movimentacao(idMovimentacao, objetos, responsavel, origem, destino, tipo, descricao, data);

            if (idMovimentacao == 0) {
                ControleDAO.getBanco().getMovimentacaoDAO().inserir(movi);
                Mensagem.info("Movimentação cadastrada com sucesso!");
            } else {
                ControleDAO.getBanco().getMovimentacaoDAO().editar(movi);
                Mensagem.info("Movimentação atualizada com sucesso!");
            }

            telaCadastro(null);
            sincronizarBase();
        }
    }

    @FXML
    void editar(ActionEvent event) {
        try {
            Movimentacao movi = tbMovimentacao.getSelectionModel().getSelectedItem();
            movi.getClass();

            telaCadastro(null);

            txtResponsavel.setText(movi.getResponsavel());
            txtDestino.setText(movi.getOrigem());
            txtOrigem.setText(movi.getOrigem());
            txtObjetos.setText(movi.getObjetos());
            txtResponsavel.setText(movi.getResponsavel());

            cbTipo.setValue(movi.getTipo());
            dtMovimentacao.setValue(movi.getData());
            txtDescricao.setText(movi.getDescricao());

            lbTitulo.setText("Editar Movimentação");
            menu.selectToggle(menu.getToggles().get(1));

            idMovimentacao = movi.getId();

        } catch (NullPointerException ex) {
            Nota.alerta("Selecione um movimentação na tabela para edição!");
        }
    }

    @FXML
    void excluir(ActionEvent event) {
        try {
            Movimentacao movimentacao = tbMovimentacao.getSelectionModel().getSelectedItem();

            Dialogo.Resposta response = Mensagem.confirmar("Excluir movimentação " + movimentacao.getId() + " ?");

            if (response == Dialogo.Resposta.YES) {
                ControleDAO.getBanco().getMovimentacaoDAO().excluir(movimentacao.getId());
                sincronizarBase();
                tabela();
            }

            tbMovimentacao.getSelectionModel().clearSelection();

        } catch (NullPointerException ex) {
            Mensagem.alerta("Selecione movimentação na tabela para exclusão!");
        }
    }

    @FXML
    public void initialize() {
        telaCadastro(null);

        Grupo.notEmpty(menu);
        sincronizarBase();
        combo();
        dtMovimentacao.setValue(LocalDate.now());

        txtPesquisar.textProperty().addListener((obs, old, novo) -> {
            filtro(novo, FXCollections.observableArrayList(listaMovimentacao));
        });
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void config(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);
        Modulo.visualizacao(false, btExcluir, btSalvar, btEditar, telaCadastro, telaEdicao, txtPesquisar);

        legenda.setText(msg);//mensagem legenda
        tbMovimentacao.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));

        idMovimentacao = 0;
    }

    /**
     * Sincronizar dados com banco de dados
     */
    private void sincronizarBase() {
        listaMovimentacao = ControleDAO.getBanco().getMovimentacaoDAO().listar();
    }

    /**
     * Popular comb box de tipos de movimentação
     */
    private void combo() {
        Combo.popular(cbTipo, "Movimentação", "Entrada", "Saída", "Empréstimo");
    }

    /**
     * Mapear dados objetos para inserção dos dados na tabela
     */
    private void tabela() {
        ObservableList data = FXCollections.observableArrayList(listaMovimentacao);

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colResponsavel.setCellValueFactory(new PropertyValueFactory<>("responsavel"));
        colOrigem.setCellValueFactory(new PropertyValueFactory<>("origem"));
        colDestino.setCellValueFactory(new PropertyValueFactory<>("destino"));
        colObjetos.setCellValueFactory(new PropertyValueFactory<>("objetos"));
        colData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movimentacao, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Movimentacao, String> obj) {
                return new SimpleStringProperty(Tempo.toString(obj.getValue().getData()));
            }
        });
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        tbMovimentacao.setItems(data);
    }

    /**
     * Campo de pesquisar para filtrar dados na tabela
     */
    private void filtro(String valor, ObservableList<Movimentacao> listaMovimentacao) {

        FilteredList<Movimentacao> dadosFiltrados = new FilteredList<>(listaMovimentacao, movi -> true);
        dadosFiltrados.setPredicate(movi -> {

            if (valor == null || valor.isEmpty()) {
                return true;
            } else if (movi.getDestino().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (movi.getOrigem().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (movi.getResponsavel().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (movi.getTipo().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            }

            return false;
        });

        SortedList<Movimentacao> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbMovimentacao.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de movimentações encontradas");

        tbMovimentacao.setItems(dadosOrdenados);
    }

    /**
     * Limpar campos textfield cadastro de coleções
     */
    private void limpar() {
        Campo.limpar(txtDestino, txtObjetos, txtOrigem, txtResponsavel);
        Campo.limpar(txtDescricao);
    }

}

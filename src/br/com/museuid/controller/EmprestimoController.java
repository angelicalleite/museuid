package br.com.museuid.controller;

import br.com.museuid.banco.controle.ControleDAO;
import br.com.museuid.model.Emprestimo;
import br.com.museuid.model.Instituicao;
import br.com.museuid.util.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
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

public class EmprestimoController extends AnchorPane {

    private List<Emprestimo> listaEmprestimo;
    private int idEmprestimo = 0;

    @FXML
    private GridPane telaCadastro;
    @FXML
    private TableColumn colNumEmprestimo;
    @FXML
    private TextField txtSolicitante;
    @FXML
    private TableColumn colCPF;
    @FXML
    private TextField txtContato;
    @FXML
    private Button btSalvar;
    @FXML
    private TableColumn colDataEmprestimo;
    @FXML
    private TextField txtCPF;
    @FXML
    private TableColumn colContato;
    @FXML
    private TextField txtNumEmprestimo;
    @FXML
    private TableColumn colEmail;
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
    private TableColumn colDescricao;
    @FXML
    private TableColumn colResponsavel;
    @FXML
    private Button btEditar;
    @FXML
    private TableColumn colId;
    @FXML
    private AnchorPane telaEdicao;
    @FXML
    private TableColumn colDataDevolucao;
    @FXML
    private TableColumn colStatus;
    @FXML
    private Label legenda;
    @FXML
    private Button btExcluir;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtResponsavel;
    @FXML
    private TextArea txtDescricao;
    @FXML
    private ToggleGroup menu;
    @FXML
    private ComboBox<Instituicao> cbInstituicao;
    @FXML
    private ComboBox<String> cbStatus;
    @FXML
    private Label lbTitulo;
    @FXML
    private TextField txtRG;
    @FXML
    private DatePicker dtDevolucao;
    @FXML
    private DatePicker dtEmprestimo;

    public EmprestimoController() {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("../view/emprestimo.fxml"));

            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();

        } catch (IOException ex) {
            Mensagem.erro("Erro ao carregar tela empréstimos! \n" + ex);
        }
    }

    @FXML
    void telaCadastro(ActionEvent event) {
        config("Cadastrar Empréstimo", "Campos obrigatórios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        limpar();
    }

    @FXML
    void telaEdicao(ActionEvent event) {
        config("Editar Empréstimo", "Quantidade de empréstimos encontrados", 1);
        Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
        tabela();
    }

    @FXML
    void telaExcluir(ActionEvent event) {
        config("Excluir Empréstimo", "Quantidade de empréstimos encontrados", 2);
        Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
        tabela();
    }

    @FXML
    void salvar(ActionEvent event) {
        boolean vazio = Campo.noEmpty(txtContato, txtCPF, txtNumEmprestimo, txtEmail, txtRG, txtSolicitante, txtResponsavel);

        String contato = txtContato.getText();
        String cpf = txtCPF.getText();
        String email = txtEmail.getText();
        String numEmprestimo = txtNumEmprestimo.getText();
        String rg = txtRG.getText();
        String solicitante = txtSolicitante.getText();
        String descricao = txtDescricao.getText();
        String responsavel = txtResponsavel.getText();

        String status = cbStatus.getValue();
        Instituicao instituicao = cbInstituicao.getValue();
        LocalDate dataDevolucao = dtDevolucao.getValue();
        LocalDate dataEmprestimo = dtEmprestimo.getValue();

        if (vazio) {
            Nota.alerta("Preencher campos vazios!");
        } else if (cbInstituicao.getItems().isEmpty()) {
            Nota.alerta("Instituições não encontradas!");
        } else if (ControleDAO.getBanco().getEmprestimoDAO().isEmprestimo(numEmprestimo, idEmprestimo)) {
            Nota.alerta("Número Empréstimo já cadastrado!");
        } else {
            Emprestimo emprestimo = new Emprestimo(idEmprestimo, numEmprestimo, solicitante, cpf, rg, contato, email, responsavel, status, dataEmprestimo, dataDevolucao, descricao, "", instituicao);

            if (idEmprestimo == 0) {
                ControleDAO.getBanco().getEmprestimoDAO().inserir(emprestimo);
                Mensagem.info("Empréstimo cadastrado com sucesso!");
            } else {
                ControleDAO.getBanco().getEmprestimoDAO().editar(emprestimo);
                Mensagem.info("Empréstimo atualizado com sucesso!");
            }

            telaCadastro(null);
            sincronizarBase();
        }
    }

    @FXML
    void editar(ActionEvent event) {
        try {
            Emprestimo emprestimo = tbEmprestimo.getSelectionModel().getSelectedItem();
            emprestimo.getClass();

            telaCadastro(null);

            txtContato.setText(emprestimo.getContato());
            txtCPF.setText(emprestimo.getCpf());
            txtNumEmprestimo.setText(emprestimo.getNumeroEmprestimo());
            txtEmail.setText(emprestimo.getEmail());
            txtRG.setText(emprestimo.getRg());
            txtSolicitante.setText(emprestimo.getSolicitante());
            txtDescricao.setText(emprestimo.getDescricao());
            txtResponsavel.setText(emprestimo.getResponsavel());
            cbInstituicao.setValue(emprestimo.getInstituicao());
            cbStatus.setValue(emprestimo.getStatus());
            dtDevolucao.setValue(emprestimo.getDataDevolucao());
            dtEmprestimo.setValue(emprestimo.getDataEmprestimmo());

            lbTitulo.setText("Editar Emprestimo");
            menu.selectToggle(menu.getToggles().get(1));

            idEmprestimo = emprestimo.getId();

        } catch (NullPointerException ex) {
            Nota.alerta("Selecione um empréstimo na tabela para edição!");
        }
    }

    @FXML
    void excluir(ActionEvent event) {
        try {
            Emprestimo emprestimo = tbEmprestimo.getSelectionModel().getSelectedItem();

            Dialogo.Resposta response = Mensagem.confirmar("Excluir empréstimo " + emprestimo.getNumeroEmprestimo() + " ?");

            if (response == Dialogo.Resposta.YES) {
                ControleDAO.getBanco().getEmprestimoDAO().excluir(emprestimo.getId());
                sincronizarBase();
                tabela();
            }

            tbEmprestimo.getSelectionModel().clearSelection();

        } catch (NullPointerException ex) {
            Mensagem.alerta("Selecione empréstimo na tabela para exclusão!");
        }
    }

    @FXML
    void initialize() {
        telaCadastro(null);

        Grupo.notEmpty(menu);
        sincronizarBase();
        combos();

        dtEmprestimo.setValue(LocalDate.now());
        Tempo.blockDataAnterior(LocalDate.now(), dtDevolucao);

        dtEmprestimo.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> obs, LocalDate old, LocalDate novo) {
                Tempo.blockDataAnterior(novo, dtDevolucao);
            }
        });

        txtPesquisar.textProperty().addListener((obs, old, novo) -> {
            filtro(novo, FXCollections.observableArrayList(listaEmprestimo));
        });
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void config(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);
        Modulo.visualizacao(false, btExcluir, btSalvar, btEditar, telaCadastro, telaEdicao, txtPesquisar);

        legenda.setText(msg);
        tbEmprestimo.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));

        idEmprestimo = 0;
    }

    /**
     * Sincronizar dados com banco de dados
     */
    private void sincronizarBase() {
        listaEmprestimo = ControleDAO.getBanco().getEmprestimoDAO().listar();
    }

    /**
     * Preencher combos estaticos e dinamicos
     */
    public void combos() {
        Combo.popular(cbInstituicao, ControleDAO.getBanco().getInstituicaoDAO().combo());
        Combo.popular(cbStatus, "Aberto", "Entregue", "Atrasado");
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

    /**
     * Limpar campos textfield cadastro de coleções
     */
    private void limpar() {
        Campo.limpar(txtContato, txtCPF, txtEmail, txtNumEmprestimo, txtRG, txtSolicitante, txtResponsavel);
        Campo.limpar(txtDescricao);
    }

}

package br.com.museuid.controller;

import br.com.museuid.banco.controle.ControleDAO;
import br.com.museuid.model.Excursao;
import br.com.museuid.model.Instituicao;
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

public class ExcursaoController extends AnchorPane {

    private List<Excursao> listaExcursao;
    private int idExcursao = 0;
    private boolean agendamenta = false;

    @FXML
    private GridPane telaCadastro;
    @FXML
    private TextField txtResponsavel;
    @FXML
    private TextField txtCurso;
    @FXML
    private ToggleButton btAtivo;
    @FXML
    private TextField txtContato;
    @FXML
    private TableColumn colStatusAgenda;
    @FXML
    private Button btSalvar;
    @FXML
    private TableColumn colAgendar;
    @FXML
    private TableColumn colContato;
    @FXML
    private TableView<Excursao> tbExcursao;
    @FXML
    private TableColumn colDataExcursao;
    @FXML
    private TextField txtHorario;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private TextField txtGuias;
    @FXML
    private ComboBox<String> cbStatus;
    @FXML
    private TableColumn colInstituicao;
    @FXML
    private TableColumn colDescricao;
    @FXML
    private Button btEditar;
    @FXML
    private TableColumn colId;
    @FXML
    private TextField txtParticipantes;
    @FXML
    private AnchorPane telaEdicao;
    @FXML
    private TableColumn colGuias;
    @FXML
    private DatePicker dtVisita;
    @FXML
    private Label legenda;
    @FXML
    private Button btExcluir;
    @FXML
    private TableColumn colCurso;
    @FXML
    private TextArea txtDescricao;
    @FXML
    private ToggleGroup menu;
    @FXML
    private ToggleGroup agenda;
    @FXML
    private TableColumn colResponsavel;
    @FXML
    private TableColumn colHorario;
    @FXML
    private ComboBox<Instituicao> cbInstituicao;
    @FXML
    private Label lbTitulo;
    @FXML
    private TableColumn colParticipantes;

    @FXML
    private ToggleButton btInativo;

    public ExcursaoController() {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("../view/excursao.fxml"));

            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();

        } catch (IOException ex) {
            Mensagem.erro("Erro ao carregar tela excursão! \n" + ex);
        }
    }

    @FXML
    void telaCadastro(ActionEvent event) {
        configTela("Cadastrar Excursão", "Campos obrigatórios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        limparCampos();
    }

    @FXML
    void telaEdicao(ActionEvent event) {
        configTela("Editar Excursão", "Quantidade de excursões encontrados", 1);
        Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
        tabela();
    }

    @FXML
    void telaExcluir(ActionEvent event) {
        configTela("Excluir Excursão", "Quantidade de excursões encontrados", 2);
        Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
        tabela();
    }

    @FXML
    void salvar(ActionEvent event) {

        boolean vazio = Campo.noEmpty(txtCurso, txtHorario, txtParticipantes, txtResponsavel, txtContato);

        String contato = txtContato.getText();
        String curso = txtCurso.getText();
        String guias = txtGuias.getText();
        String horario = txtHorario.getText();
        int participantes = Integer.parseInt(txtParticipantes.getText().trim().isEmpty() ? "0" : txtParticipantes.getText());
        String responsavel = txtResponsavel.getText();
        String descricao = txtDescricao.getText();

        Instituicao instituicao = cbInstituicao.getValue();
        String status = cbStatus.getValue();
        LocalDate data = dtVisita.getValue();


        if (vazio) {
            Nota.alerta("Preencher campos vazios!");
        } else if (cbInstituicao.getValue() == null) {
            Nota.alerta("Instituição não encontrada!");
        } else if (participantes == 0) {
            Nota.alerta("Informe quantidade de participantes!");
        } else {
            Excursao excursao = new Excursao(idExcursao, curso, participantes, responsavel, contato, guias, horario, data, descricao, agendamenta, status, instituicao);

            if (idExcursao == 0) {
                ControleDAO.getBanco().getExcursaoDAO().inserir(excursao);
                Mensagem.info("Excursão cadastrado com sucesso!");
            } else {
                ControleDAO.getBanco().getExcursaoDAO().editar(excursao);
                Mensagem.info("Excursão atualizado com sucesso!");
            }

            telaCadastro(null);
            sincronizarBase();
        }
    }

    @FXML
    void editar(ActionEvent event) {
        try {
            Excursao excursao = tbExcursao.getSelectionModel().getSelectedItem();
            excursao.getClass();

            telaCadastro(null);

            txtContato.setText(excursao.getContato());
            txtCurso.setText(excursao.getCurso());
            txtGuias.setText(excursao.getGuias());
            txtHorario.setText(excursao.getHorario());
            txtParticipantes.setText("" + excursao.getParticipantes());
            txtResponsavel.setText(excursao.getResponsavel());
            txtDescricao.setText(excursao.getDescricao());

            cbInstituicao.setValue(excursao.getInstituicao());
            cbStatus.setValue(excursao.getStatusAgendamento());
            dtVisita.setValue(excursao.getData());

            if (excursao.isAgendamento()) {
                btAtivo.setSelected(true);
                btInativo.setSelected(false);
            } else {
                btInativo.setSelected(true);
                btAtivo.setSelected(false);
            }

            lbTitulo.setText("Editar Excursao");
            menu.selectToggle(menu.getToggles().get(1));

            idExcursao = excursao.getId();

        } catch (NullPointerException ex) {
            Nota.alerta("Selecione um excursao na tabela para edição!");
        }
    }

    @FXML
    void excluir(ActionEvent event) {
        try {
            Excursao excursao = tbExcursao.getSelectionModel().getSelectedItem();

            Dialogo.Resposta response = Mensagem.confirmar("Excluir excursão ?");

            if (response == Dialogo.Resposta.YES) {
                ControleDAO.getBanco().getExcursaoDAO().excluir(excursao.getId());
                sincronizarBase();
                tabela();
            }

            tbExcursao.getSelectionModel().clearSelection();

        } catch (NullPointerException ex) {
            Mensagem.alerta("Selecione excursão na tabela para exclusão!");
        }
    }

    @FXML
    void agendaInativa(ActionEvent event) {
        agendamenta = true;
    }

    @FXML
    void agendaAtiva(ActionEvent event) {
        agendamenta = true;
    }


    @FXML
    void initialize() {
        telaCadastro(null);

        Grupo.notEmpty(menu);
        Grupo.notEmpty(agenda);
        Mascara.numerico(txtParticipantes);

        sincronizarBase();
        combos();
        dtVisita.setValue(LocalDate.now());

        txtPesquisar.textProperty().addListener((obs, old, novo) -> {
            filtroPesquisa(novo, FXCollections.observableArrayList(listaExcursao));
        });
    }

    /**
     * Preencher combos tela
     */
    public void combos() {
        Combo.popular(cbStatus, "Inativo", "Aberto", "Cancelado", "Realizado");
        Combo.popular(cbInstituicao, ControleDAO.getBanco().getInstituicaoDAO().combo());
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void configTela(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);//informar titulo da tela
        Modulo.visualizacao(false, btExcluir, btSalvar, btEditar, telaCadastro, telaEdicao, txtPesquisar);//menus e elementos que serão exibidos conforme contexto da subtela

        legenda.setText(msg);
        tbExcursao.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));

        idExcursao = 0;
    }

    /**
     * Sincronizar dados com banco de dados
     */
    private void sincronizarBase() {
        listaExcursao = ControleDAO.getBanco().getExcursaoDAO().listar();
    }

    /**
     * Mapear dados objetos para inserção dos dados na tabela
     */
    private void tabela() {
        ObservableList data = FXCollections.observableArrayList(listaExcursao);

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colContato.setCellValueFactory(new PropertyValueFactory<>("contato"));
        colInstituicao.setCellValueFactory(new PropertyValueFactory<>("instituicao"));
        colCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
        colGuias.setCellValueFactory(new PropertyValueFactory<>("guias"));
        colHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));
        colResponsavel.setCellValueFactory(new PropertyValueFactory<>("responsavel"));
        colStatusAgenda.setCellValueFactory(new PropertyValueFactory<>("statusAgendamento"));
        colParticipantes.setCellValueFactory(new PropertyValueFactory<>("participantes"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colDataExcursao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Excursao, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Excursao, String> obj) {
                return new SimpleStringProperty(Tempo.toString(obj.getValue().getData()));
            }
        });
        colAgendar.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Excursao, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Excursao, String> obj) {
                return new SimpleStringProperty(obj.getValue().isAgendamento() ? "Ativo" : "Inativo");
            }
        });

        tbExcursao.setItems(data);
    }

    /**
     * Campo de pesquisar para filtrar dados na tabela
     */
    private void filtroPesquisa(String valor, ObservableList<Excursao> listaExcursao) {

        FilteredList<Excursao> dadosFiltrados = new FilteredList<>(listaExcursao, excursao -> true);
        dadosFiltrados.setPredicate(excursao -> {

            if (valor == null || valor.isEmpty()) {
                return true;
            } else if (excursao.getContato().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (excursao.getCurso().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (excursao.getGuias().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (excursao.getHorario().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (excursao.getInstituicao().getNome().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (excursao.getResponsavel().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (excursao.getStatusAgendamento().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            }

            return false;
        });

        SortedList<Excursao> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbExcursao.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de excursões encontrados");

        tbExcursao.setItems(dadosOrdenados);
    }

    /**
     * Limpar campos textfield cadastro de coleções
     */
    private void limparCampos() {
        Campo.limpar(txtContato, txtCurso, txtGuias, txtHorario, txtParticipantes, txtResponsavel);
        Campo.limpar(txtDescricao);
    }

}

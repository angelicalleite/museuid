package br.com.museuid.controller;

import br.com.museuid.banco.controle.ControleDAO;
import br.com.museuid.model.Emprestimo;
import br.com.museuid.model.Excursao;
import br.com.museuid.util.Mensagem;
import br.com.museuid.util.Relatorios;
import br.com.museuid.util.Tempo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class DashController extends AnchorPane {

    Map<String, String> designacoes;
    Map<String, String> colecoes;
    Map<String, String> estratigrafias;
    Map<String, String> recentes;

    List<Emprestimo> emprestimo;
    List<Excursao> excursao;

    @FXML
    private VBox boxAcompanhamento;
    @FXML
    private AnchorPane boxGrafico;
    @FXML
    private VBox boxCatalogacao;
    @FXML
    private VBox boxRecentes;
    @FXML
    private HBox boxTituloAcompanhamento;
    @FXML
    private ToggleGroup grupoGrafico;
    @FXML
    private ToggleGroup grupoCatalogacao;
    @FXML
    private ToggleGroup grupoAcompanhamento;
    @FXML
    private Label tituloCatalogacao;
    @FXML
    private Label tituloGrafico;
    @FXML
    private Label tituloGrafico1;
    @FXML
    private Label lbTotalCatalogacoes;
    @FXML
    private Label lbTotalVisitantes;
    @FXML
    private Label lbTotalDesignacao;
    @FXML
    private Label lbTotalEstratigrafias;
    @FXML
    private Label lbTotalColecoes;

    public DashController() {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("../view/dash.fxml"));

            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();

        } catch (IOException ex) {
            Mensagem.erro("Erro ao carregar tela dashboard! \n" + ex);
            ex.printStackTrace();
        }
    }

    @FXML
    void catalogacao(ActionEvent event) {
        Relatorios.criar(boxGrafico, "Data Catalogação", 3, LocalDate.now());
        tituloGrafico("Catalogações Anuais");
    }

    @FXML
    void visitantes(ActionEvent event) {
        Relatorios.criar(boxGrafico, "Visitas", 3, LocalDate.now());
        tituloGrafico("Visitantes Anuais");
    }

    @FXML
    void emprestimo(ActionEvent event) {
        Relatorios.criar(boxGrafico, "Empréstimo", 3, LocalDate.now());
        tituloGrafico("Empréstimos Anuais");
    }

    @FXML
    void movimentacao(ActionEvent event) {
        Relatorios.criar(boxGrafico, "Movimentação", 3, LocalDate.now());
        tituloGrafico("Movimentações Anuais");
    }

    @FXML
    void acompanhamentoEmprestimo(ActionEvent event) {
        boxEmprestimos(emprestimo);
    }

    @FXML
    void acompanhamentoExcursao(ActionEvent event) {
        boxExcursao(excursao);
    }

    @FXML
    void designacao(ActionEvent event) {
        boxCatalogacoes(boxCatalogacao, designacoes);
    }

    @FXML
    void estratigrafia(ActionEvent event) {
        boxCatalogacoes(boxCatalogacao, estratigrafias);
    }

    @FXML
    void colecao(ActionEvent event) {
        boxCatalogacoes(boxCatalogacao, colecoes);
    }

    @FXML
    void initialize() {
        catalogacao(null);

        totalVisitantes();
        totalCatalogacao();
        baseDados();

        boxCatalogacoes(boxRecentes, recentes);
        designacao(null);
        acompanhamentoExcursao(null);
        contagem();
    }

    /**
     * Consultar quantidade de itens no acervo cadastrado de acordo com a categoria
     */
    private void baseDados() {
        designacoes = ControleDAO.getBanco().getCatalogacaoDAO().designacoes();
        estratigrafias = ControleDAO.getBanco().getCatalogacaoDAO().estratigrafias();
        colecoes = ControleDAO.getBanco().getCatalogacaoDAO().colecoes();
        recentes = ControleDAO.getBanco().getCatalogacaoDAO().recentes();
        excursao = ControleDAO.getBanco().getExcursaoDAO().acompanhamento(Tempo.atual());
        emprestimo = ControleDAO.getBanco().getEmprestimoDAO().acompanhamento();
    }

    private void contagem() {
        lbTotalColecoes.setText("" + ControleDAO.getBanco().getColecaoDAO().total());
        lbTotalDesignacao.setText("" + ControleDAO.getBanco().getDesignacaoDAO().total());
        lbTotalEstratigrafias.setText("" + ControleDAO.getBanco().getEstratigrafiaDAO().total());
    }

    /**
     * Titulo graficos dashboard
     */
    private void tituloGrafico(String titulo) {
        tituloGrafico.setText(titulo + " " + LocalDate.now().getYear());
    }

    /**
     * Consultar total de catalogações cadastrados na base de dados
     */
    private void totalCatalogacao() {
        lbTotalCatalogacoes.setText("" + ControleDAO.getBanco().getCatalogacaoDAO().total());
    }

    /**
     * Consultar total de visitantes cadastrados na base de dados
     */
    private void totalVisitantes() {
        lbTotalVisitantes.setText("" + ControleDAO.getBanco().getVisitanteDAO().total());
    }

    /**
     * Exibir dados no vbox de catalogações
     */
    private void boxCatalogacoes(VBox box, Map<String, String> map) {
        box.getChildren().clear();

        for (Map.Entry<String, String> chave : map.entrySet()) {

            Label nome = new Label(chave.getKey());
            nome.getStyleClass().add("nome-item-catalogacao");

            Label quantidade = new Label(chave.getValue());
            quantidade.getStyleClass().add("qt-item-catalogacao");

            nome.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(nome, Priority.ALWAYS);

            boxItens(box, nome, quantidade);
        }
    }

    /**
     * Exibir dados para acompanhamento dos empréstimos
     */
    private void boxEmprestimos(List<Emprestimo> dados) {
        boxAcompanhamento.getChildren().clear();
        tituloEmprestimo();

        for (Emprestimo emprestimo : dados) {
            Label tipo = new Label();
            tipo.getStyleClass().addAll("tipo-emprestimo", "tipo-emprestimo-" + emprestimo.getStatus().toLowerCase());

            Label num = new Label(emprestimo.getNumeroEmprestimo());
            num.getStyleClass().add("emprestimo-num");

            Label instituicao = new Label(emprestimo.getInstituicao().getNome());
            instituicao.getStyleClass().add("emprestimo-instituicao");

            Label solicitante = new Label(emprestimo.getSolicitante());
            solicitante.getStyleClass().add("emprestimo-solicitante");

            Label status = new Label(emprestimo.getStatus());
            status.getStyleClass().add("emprestimo-status");

            Label data = new Label(Tempo.toString(emprestimo.getDataDevolucao()));
            data.getStyleClass().add("emprestimo-data");

            instituicao.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(instituicao, Priority.ALWAYS);

            boxItensAcompanhamento(boxAcompanhamento, tipo, num, instituicao, solicitante, status, data);
        }
    }

    /**
     * Exibir dados para acompanhamento das excursões
     */
    private void boxExcursao(List<Excursao> dados) {
        boxAcompanhamento.getChildren().clear();
        tituloExcursao();

        for (Excursao excursao : dados) {
            Label tipo = new Label();
            tipo.getStyleClass().addAll("tipo-excursao", "tipo-excursao-" + excursao.getStatusAgendamento().toLowerCase());

            Label instituicao = new Label(excursao.getInstituicao().getNome());
            instituicao.getStyleClass().add("excursao-instituicao");

            Label curso = new Label(excursao.getCurso());
            curso.getStyleClass().add("excursao-curso");

            Label participantes = new Label("" + excursao.getParticipantes());
            participantes.getStyleClass().add("excursao-participantes");

            Label responsavel = new Label(excursao.getResponsavel());
            responsavel.getStyleClass().add("excursao-responsavel");

            Label horario = new Label(excursao.getHorario());
            horario.getStyleClass().add("excursao-horario");

            Label data = new Label(Tempo.toString(excursao.getData()));
            data.getStyleClass().add("excursao-data");

            instituicao.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(instituicao, Priority.ALWAYS);

            boxItensAcompanhamento(boxAcompanhamento, tipo, instituicao, curso, participantes, responsavel, horario, data);
        }
    }

    /**
     * Adiciona itens de catalogação ao box informado
     */
    private void boxItens(VBox vbox, Node... no) {
        HBox box = new HBox();
        box.getStyleClass().add("item-catalogacao");

        box.getChildren().addAll(no);
        vbox.getChildren().addAll(box);
    }

    /**
     * Adiciona itens de excursão ou empréstimo de acompanhamento ao box informado
     */
    private void boxItensAcompanhamento(VBox vbox, Node... no) {
        HBox box = new HBox();
        box.getStyleClass().add("item-acompanhamento");

        box.getChildren().addAll(no);
        vbox.getChildren().addAll(box);
    }

    /**
     * Titulo de empréstimos box acompanhamento
     */
    private void tituloEmprestimo() {
        boxTituloAcompanhamento.getChildren().clear();

        Label num = new Label("EMPRÉSTIMO");
        num.getStyleClass().add("emprestimo-num");

        Label instituicao = new Label("INSTITUIÇÃO");
        instituicao.getStyleClass().add("emprestimo-instituicao");

        Label solicitante = new Label("SOLICITANTE");
        solicitante.getStyleClass().add("emprestimo-solicitante");

        Label status = new Label("STATUS");
        status.getStyleClass().add("emprestimo-status");

        //info vermelho data emprestimos atrasados
        Label data = new Label("DATA DEVOLUÇÃO");
        data.getStyleClass().add("emprestimo-data");

        instituicao.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(instituicao, Priority.ALWAYS);

        boxTituloAcompanhamento.getChildren().addAll(new Label("TIPO"), num, instituicao, solicitante, status, data);
    }

    /**
     * Titulo de empréstimos box excursão
     */
    private void tituloExcursao() {
        boxTituloAcompanhamento.getChildren().clear();

        Label instituicao = new Label("INSTITUIÇÃO");
        instituicao.getStyleClass().add("excursao-instituicao");

        Label curso = new Label("CURSO");
        curso.getStyleClass().add("excursao-curso");

        Label participantes = new Label("PARTICIPANTES");
        participantes.getStyleClass().add("excursao-participantes");

        Label responsavel = new Label("RESPONSÁVEL");
        responsavel.getStyleClass().add("excursao-responsavel");

        Label horario = new Label("HORÁRIO");
        horario.getStyleClass().add("excursao-horario");

        Label data = new Label("DATA VISITA");
        data.getStyleClass().add("excursao-data");

        instituicao.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(instituicao, Priority.ALWAYS);

        boxTituloAcompanhamento.getChildren().addAll(new Label("TIPO"), instituicao, curso, participantes, responsavel, horario, data);
    }
}

package br.com.museuid.controller;

import br.com.museuid.banco.controle.ControleDAO;
import br.com.museuid.model.*;
import br.com.museuid.util.*;
import br.com.museuid.view.login.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ValidacaoController extends AnchorPane {

    int tipoRelatorio;
    boolean statusValidacao;

    List<Catalogacao> catalogacoes;
    List<Catalogacao> filtro;
    Validacao validacao;

    @FXML
    private GridPane gridValidacao;
    @FXML
    private FlowPane boxValidacao;
    @FXML
    private StackPane boxCombos;
    @FXML
    private ToggleGroup status;
    @FXML
    private ToggleButton btInvalidados;
    @FXML
    private ToggleGroup menu;
    @FXML
    private ToggleButton btValidados;
    @FXML
    private ToggleButton btTodos;
    @FXML
    private ComboBox<Designacao> cbDesignacao;
    @FXML
    private ComboBox<Colecao> cbColecao;
    @FXML
    private ComboBox<Estratigrafia> cbEstratigrafia;
    @FXML
    private ComboBox<String> cbGenerico;
    @FXML
    private Label lbInvalidados;
    @FXML
    private Label lbDesignacao;
    @FXML
    private Label lbLocalizacao;
    @FXML
    private Label lbDimesoes;
    @FXML
    private Label lbEtiquetaRFID;
    @FXML
    private Label lbTotal;
    @FXML
    private Label lbNumPartes;
    @FXML
    private Label lbNumOrdem;
    @FXML
    private Label lbColecao;
    @FXML
    private Label lbValidados;
    @FXML
    private Label lbTitulo;
    @FXML
    private Label lbEstratigrafia;
    @FXML
    private TextField txtIdentificador;

    public ValidacaoController() {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("../view/validacao.fxml"));

            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();

        } catch (IOException ex) {
            Mensagem.erro("Erro ao carregar tela validação dos fósseis! \n" + ex);
        }
    }

    @FXML
    void historico(ActionEvent event) {
        config("Historico Validação", 1, cbGenerico, gridValidacao);
    }

    @FXML
    void localizacao(ActionEvent event) {
        config("Validação por Localização", 2, cbGenerico, gridValidacao);
        Combo.popular(cbGenerico, "Exposição", "Reserva Técnica", "Emprestado", "Exposição e Reserva Técnica", "Outro");
    }

    @FXML
    void designacao(ActionEvent event) {
        config("Validação por Designação", 3, cbDesignacao, gridValidacao);
    }

    @FXML
    void estratigrafia(ActionEvent event) {
        config("Validação por Estratigráfia", 4, cbEstratigrafia, gridValidacao);
    }

    @FXML
    void colecao(ActionEvent event) {
        config("Validação por Coleção", 5, cbColecao, gridValidacao);
    }

    @FXML
    void geral(ActionEvent event) {
        config("Validação Geral", 6, cbGenerico, gridValidacao);
        Combo.popular(cbGenerico, "Geral");
    }

    @FXML
    void todos(ActionEvent event) {
        if (statusValidacao) {
            box(validacao.getItens());
        }
    }

    @FXML
    void validados(ActionEvent event) {
        if (statusValidacao)
            box(validacao.getItens().stream().filter(o -> o.isStatus() == true).collect(Collectors.toList()));
    }

    @FXML
    void invalidados(ActionEvent event) {
        if (statusValidacao)
            box(validacao.getItens().stream().filter(o -> o.isStatus() == false).collect(Collectors.toList()));
    }

    @FXML
    void iniciar(ActionEvent event) {
        if (!statusValidacao) {
            validacao = filtro(tipoRelatorio);

            if (validacao == null || validacao.getItens().isEmpty()) {
                infoDados();
            } else {
                box(validacao.getItens());
                statusValidacao = true;
            }
        } else {
            Nota.alerta("Validação inicializada, cancele ou finalize para iniciar um nova validação");
        }
    }

    @FXML
    void cancelar(ActionEvent event) {
        Dialogo.Resposta resposta = Mensagem.confirmar("Cancelar validação ?");

        if (resposta == Dialogo.Resposta.YES) {
            if (statusValidacao) {
                limparDados();
                infoContadores();
            } else {
                Nota.alerta("Validação não inicializada!");
            }
        }
    }

    @FXML
    void finalizar(ActionEvent event) {
        Dialogo.Resposta resposta = Mensagem.confirmar("Finalizar validação ?");

        if (resposta == Dialogo.Resposta.YES) {
            if (statusValidacao) {
                boxValidacao.setCursor(Cursor.WAIT);

                validacao.setTotal(Integer.parseInt(lbTotal.getText()));
                validacao.setInvalidados(Integer.parseInt(lbInvalidados.getText()));
                validacao.setValidados(Integer.parseInt(lbValidados.getText()));
                int id = ControleDAO.getBanco().getValidacaoDAO().validacao(validacao);

                for (ValidacaoItem item : validacao.getItens()) {
                    ControleDAO.getBanco().getValidacaoDAO().item(id, item);
                }

                limparDados();
                infoContadores();
                boxValidacao.setCursor(Cursor.DEFAULT);
            } else {
                Nota.alerta("Validação não inicializada!");
            }
        }
    }

    @FXML
    void identificar(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && statusValidacao && !txtIdentificador.getText().isEmpty()) {
            validar(txtIdentificador.getText());
            txtIdentificador.setText("");
        }
    }

    @FXML
    void limpar(ActionEvent event) {
        Campo.limpar(lbNumOrdem, lbEtiquetaRFID, lbNumPartes, lbDimesoes, lbLocalizacao, lbDesignacao, lbColecao, lbEstratigrafia);
    }

    @FXML
    public void initialize() {
        Grupo.notEmpty(menu);
        combo();

        baseDados();
        geral(null);
    }

    /**
     * Listar todas as catalogações do acervo
     */
    private void baseDados() {
        this.catalogacoes = ControleDAO.getBanco().getCatalogacaoDAO().listar();
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void config(String tituloTela, int grupo, Node combo, Node tela) {
        lbTitulo.setText(tituloTela);
        menu.selectToggle(menu.getToggles().get(grupo - 1));
        tipoRelatorio = grupo;

        Modulo.visualizacao(false, gridValidacao, cbColecao, cbDesignacao, cbEstratigrafia, cbGenerico);
        Modulo.visualizacao(true, combo, tela);
    }

    /**
     * Preencher combobox principal com itens de acordo com o tipo de relatorio
     */
    private void combo() {
        Combo.popular(cbColecao, ControleDAO.getBanco().getColecaoDAO().combo());
        Combo.popular(cbEstratigrafia, ControleDAO.getBanco().getEstratigrafiaDAO().combo());
        Combo.popular(cbDesignacao, ControleDAO.getBanco().getDesignacaoDAO().combo());
    }

    /**
     * Adiciona os elemntos ao box para exibição de itens da validação
     */
    private void box(List<ValidacaoItem> itens) {
        boxValidacao.getChildren().clear();
        boxValidacao.setAlignment(Pos.TOP_LEFT);

        for (ValidacaoItem item : itens) {
            Button acao = new Button(item.getCatalogacao().getNumeroOrdem());
            acao.getStyleClass().add(item.isStatus() ? "itens-validados" : "itens-invalidados");
            acao.setOnAction((event) -> {
                info(item.getCatalogacao());
            });

            boxValidacao.getChildren().add(acao);
        }

        infoContadores();
    }

    /**
     * Atualiza dados dos contadores da vaidação (total-validados-invalidados)
     */
    private void infoContadores() {
        lbValidados.setText(validacao == null ? "0" : validacao.getItens().stream().filter(o -> o.isStatus() == true).count() + "");
        lbTotal.setText(validacao == null ? "0" : validacao.getItens().size() + "");
        lbInvalidados.setText(validacao == null ? "0" : validacao.getItens().stream().filter(o -> o.isStatus() == false).count() + "");
    }

    /**
     * Validação que não contém dados ou itens para validação retorna label informando que dados para validação estão
     * vazios
     */
    private void infoDados() {
        Label info = new Label("Dados não encontrados ou vazios para inicialização da validação !");
        info.getStyleClass().add("conteudo-vazio");
        boxValidacao.setAlignment(Pos.CENTER);
        boxValidacao.getChildren().add(info);
    }

    /**
     * Exibir informações detalhadas dos fosseis
     */
    private void info(Catalogacao fossil) {
        lbNumOrdem.setText(fossil.getNumeroOrdem());
        lbEtiquetaRFID.setText(fossil.getEtiquetaRFID());
        lbNumPartes.setText("" + fossil.getNumeroPartes());
        lbDimesoes.setText(fossil.getDimensoes());
        lbLocalizacao.setText(fossil.getLocalizacao());
        lbDesignacao.setText(fossil.getDesignacao().getGenero());
        lbColecao.setText(fossil.getColecao().getNome());
        lbEstratigrafia.setText(fossil.getEstratigrafia().getFormacao());
    }

    /**
     * Filtras dados da catalogação de acordo com o tipo do relatorio e valor
     */
    private Validacao filtro(int tipo) {
        String subcategoria = "", categoria = "";

        //filtrar dados da catalogação de acordo com os itens selecionados e definir categoria de validação
        if (tipo == 2) {
            filtro = catalogacoes.stream().filter(o -> o.getLocalizacao().equals(cbGenerico.getValue())).collect(Collectors.toList());
            subcategoria = "Localização";
            categoria = cbGenerico.getValue();
        } else if (tipo == 3) {
            filtro = catalogacoes.stream().filter(o -> o.getDesignacao().getId() == cbDesignacao.getValue().getId()).collect(Collectors.toList());
            subcategoria = "Designação";
            categoria = cbDesignacao.getValue().getGenero();
        } else if (tipo == 4) {
            filtro = catalogacoes.stream().filter(o -> o.getEstratigrafia().getId() == cbEstratigrafia.getValue().getId()).collect(Collectors.toList());
            subcategoria = "Estratigrafia";
            categoria = cbEstratigrafia.getValue().getFormacao();
        } else if (tipo == 5) {
            filtro = catalogacoes.stream().filter(o -> o.getColecao().getId() == cbColecao.getValue().getId()).collect(Collectors.toList());
            subcategoria = "Coleção";
            categoria = cbColecao.getValue().getNome();
        } else if (tipo == 6) {
            filtro = catalogacoes;
            subcategoria = "Geral";
            categoria = "Geral";
        }

        //cria um item da validação para cada catalogação da listar filtrado e
        List<ValidacaoItem> itens = new ArrayList<>();
        for (Catalogacao fossil : filtro) {
            itens.add(new ValidacaoItem(false, fossil));
        }

        return new Validacao(0, categoria, subcategoria, 0, 0, 0, false, LocalDate.now(), LoginController.usuarioLogado, itens);
    }

    /**
     * Verifica se identificador (rfid ou n° ordem) se encontra na listar de validação e muda seu status para verdadeiro
     */
    private void validar(String id) {
        validacao.getItens().stream().filter(o -> (o.getCatalogacao().getNumeroOrdem().equalsIgnoreCase(id) || o.getCatalogacao().getEtiquetaRFID().equalsIgnoreCase(id)) && !id.equals("0")).forEach(s -> s.setStatus(true));
        box(validacao.getItens());
    }

    /**
     * Auxiliar na limpeza do box de validação e reinicilização dos dados zerando e anulando seus valores
     */
    private void limparDados() {
        statusValidacao = false;
        validacao = null;
        filtro = null;
        boxValidacao.getChildren().clear();
    }
}

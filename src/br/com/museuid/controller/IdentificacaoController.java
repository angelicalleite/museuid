package br.com.museuid.controller;

import br.com.museuid.banco.controle.ControleDAO;
import br.com.museuid.model.Catalogacao;
import br.com.museuid.util.Campo;
import br.com.museuid.util.Grupo;
import br.com.museuid.util.Mensagem;
import br.com.museuid.util.Modulo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class IdentificacaoController extends AnchorPane {

    private static IdentificacaoController instance;
    Catalogacao fossil;

    @FXML
    private Label txtNumOrdem;
    @FXML
    private Label txtFormacao;
    @FXML
    private Label txtColecaoDescricao;
    @FXML
    private Label txtColecao;
    @FXML
    private Label txtOrdem;
    @FXML
    private Label txtSetor;
    @FXML
    private Label txtLocal;
    @FXML
    private Label txtEtiquetaRfid;
    @FXML
    private Label txtDataEntrada;
    @FXML
    private TextField txtIdentificador;
    @FXML
    private Label txtProcedencia;
    @FXML
    private Label txtDimesoes;
    @FXML
    private Label txtNumPartes;
    @FXML
    private Label txtGrupoEstratigrafia;
    @FXML
    private Label txtEspecie;
    @FXML
    private Label txtClasse;
    @FXML
    private ToggleGroup menu;
    @FXML
    private Label txtGenero;
    @FXML
    private Label txtIdEmprestimo;
    @FXML
    private Label txtEmprestimoStatus;
    @FXML
    private Label txtFamilia;
    @FXML
    private Label txtDeatlhesProcedencia;
    @FXML
    private Label txtOrgao;
    @FXML
    private Label lbTitulo;
    @FXML
    private Label txtEmprestimoFossil;
    @FXML
    private Label txtLocalizacao;

    public IdentificacaoController() {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("../view/identificacao.fxml"));

            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();

        } catch (IOException ex) {
            Mensagem.erro("Erro ao carregar tela identificação dos fósseis! \n" + ex);
        }
    }

    /**
     * Obter instancia do controller
     */
    public static IdentificacaoController getInstance() {
        return instance;
    }

    @FXML
    void telaIdentificar(ActionEvent event) {
        config("Identificar Acervo", 0);
        Modulo.visualizacao(true, txtIdentificador);
    }

    @FXML
    void detalhes(ActionEvent event) {
        identificar(txtIdentificador.getText());
    }

    @FXML
    void initialize() {
        instance = this;//pegar instancia do controller

        telaIdentificar(null);
        Grupo.notEmpty(menu);

        txtIdentificador.setOnKeyReleased((KeyEvent tecla) -> {
            if (tecla.getCode() == KeyCode.ENTER) {
                identificar(txtIdentificador.getText());
            }
        });

        txtIdentificador.setFocusTraversable(true);
        txtIdentificador.requestFocus();
    }

    /**
     * Consultar na base de dados catalogação a partir do número da etiqueta ou número de ordem informado
     */
    public void identificar(String identificador) {

        if (identificador.trim().isEmpty() || identificador.equals("0")) {
            Mensagem.alerta("Informe número de identificação da catalogação para consulta!");
        } else if (!ControleDAO.getBanco().getIdentificacaoDAO().validarIdentificador(identificador)) {
            Mensagem.alerta("Fóssil não encontrado!");
        } else {
            fossil = ControleDAO.getBanco().getIdentificacaoDAO().identificar(identificador);
            info(fossil);
        }

        txtIdentificador.setText("");
    }

    /**
     * Informar fossil identificado
     */
    private void info(Catalogacao fossil) {

        txtNumOrdem.setText(fossil.getNumeroOrdem());
        txtLocalizacao.setText(fossil.getLocalizacao());
        txtProcedencia.setText(fossil.getProcedencia());
        txtEtiquetaRfid.setText(fossil.getEtiquetaRFID());
        txtDeatlhesProcedencia.setText(fossil.getDetalhesProcedencia());
        txtDimesoes.setText(fossil.getDimensoes());
        txtNumPartes.setText("" + fossil.getNumeroPartes());
        txtDataEntrada.setText(fossil.getDataEntrada());

        txtGrupoEstratigrafia.setText(fossil.getEstratigrafia().getGrupo());
        txtFormacao.setText(fossil.getEstratigrafia().getFormacao());

        txtColecao.setText(fossil.getColecao().getNome());
        txtColecaoDescricao.setText(fossil.getColecao().getDescricao());

        txtOrdem.setText(fossil.getDesignacao().getOrdem());
        txtClasse.setText(fossil.getDesignacao().getClasse());
        txtFamilia.setText(fossil.getDesignacao().getFamilia());
        txtGenero.setText(fossil.getDesignacao().getGenero());
        txtEspecie.setText(fossil.getDesignacao().getEspecie());

        txtOrgao.setText("");
        txtSetor.setText("");
        txtLocal.setText("");

        txtEmprestimoFossil.setText(fossil.isEmprestimo() ? "Ativo" : "Inativo");
        txtEmprestimoStatus.setText(fossil.isEmprestimo() ? "Ativo" : "Inativo");

        String emprestimo = ControleDAO.getBanco().getCatalogacaoDAO().infoNumEmprestimo(fossil.getId());
        txtIdEmprestimo.setText(emprestimo.isEmpty() ? "0" : emprestimo);

    }

    /**
     * Informar as configurações padrões da tela como titulo, mensagens e demais
     */
    private void config(String tituloTela, int grupoMenu) {
        lbTitulo.setText(tituloTela);
        menu.selectToggle(menu.getToggles().get(grupoMenu));
    }

    /**
     * Limpar labels na tela
     */
    private void limpar() {
        Campo.limpar(txtNumOrdem, txtLocalizacao, txtProcedencia, txtEtiquetaRfid, txtEmprestimoFossil,
                txtDeatlhesProcedencia, txtDimesoes, txtNumPartes, txtDataEntrada, txtGrupoEstratigrafia,
                txtFormacao, txtColecao, txtColecaoDescricao, txtOrgao, txtSetor, txtLocal, txtOrdem,
                txtClasse, txtFamilia, txtGenero, txtEspecie);
    }
}

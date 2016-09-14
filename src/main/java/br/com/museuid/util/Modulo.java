package br.com.museuid.util;

import br.com.museuid.controller.CatalogarController;
import br.com.museuid.controller.ColecaoController;
import br.com.museuid.controller.DesignacaoController;
import br.com.museuid.controller.EstratigrafiaController;
import br.com.museuid.controller.DashController;
import br.com.museuid.controller.DevolucaoController;
import br.com.museuid.controller.EmprestimoController;
import br.com.museuid.controller.HistoricoController;
import br.com.museuid.controller.ItensController;
import br.com.museuid.controller.IdentificacaoController;
import br.com.museuid.controller.LocalController;
import br.com.museuid.controller.LocalizacaoController;
import br.com.museuid.controller.OrganizacaoController;
import br.com.museuid.controller.SetorController;
import br.com.museuid.controller.MovimentacaoController;
import br.com.museuid.controller.PesquisarController;
import br.com.museuid.controller.RelatorioController;
import br.com.museuid.controller.UsuarioController;
import br.com.museuid.controller.ValidacaoController;
import br.com.museuid.controller.ExcursaoController;
import br.com.museuid.controller.InstituicaoController;
import br.com.museuid.controller.VisitanteController;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 * Principal classe para controle e carregamento dos modulos da aplicação,
 * cada menu e alguns submenus da aplicação representa um modulo na aplicação
 */
public class Modulo {

    private static CatalogarController catalogar;
    private static ColecaoController colecao;
    private static DesignacaoController designacao;
    private static EstratigrafiaController estratigrafia;

    private static VisitanteController visitante;
    private static InstituicaoController instituicao;
    private static ExcursaoController excursao;

    private static EmprestimoController emprestimo;
    private static DevolucaoController devolucao;
    private static ItensController itens;
    private static HistoricoController historico;

    private static OrganizacaoController organizacao;
    private static SetorController setor;
    private static LocalController local;
    private static LocalizacaoController localizacao;

    private static IdentificacaoController identificacao;
    private static PesquisarController pesquisa;
    private static ValidacaoController validacao;
    private static MovimentacaoController movimentacao;

    private static UsuarioController usuario;
    private static RelatorioController relatorio;
    private static DashController dashboard;

    private Modulo() {
    }

    public static void getCatalogar(AnchorPane box) {
        catalogar = catalogar == null ? new CatalogarController() : catalogar;
        catalogar.sincronizarBase();
        catalogar.combo();
        config(box, catalogar);
    }

    public static void getColecao(AnchorPane box) {
        colecao = colecao == null ? new ColecaoController() : colecao;
        config(box, colecao);
    }

    public static void getDesignacao(AnchorPane box) {
        designacao = designacao == null ? new DesignacaoController() : designacao;
        config(box, designacao);
    }

    public static void getEstratigrafia(AnchorPane box) {
        estratigrafia = estratigrafia == null ? new EstratigrafiaController() : estratigrafia;
        config(box, estratigrafia);
    }

    public static void getVisitante(AnchorPane box) {
        visitante = visitante == null ? new VisitanteController() : visitante;
        config(box, visitante);
    }

    public static void getRelatorio(AnchorPane box) {
        relatorio = relatorio == null ? new RelatorioController() : relatorio;
        config(box, relatorio);
    }

    public static void getInstituicao(AnchorPane box) {
        instituicao = instituicao == null ? new InstituicaoController() : instituicao;
        config(box, instituicao);
    }

    public static void getExcursao(AnchorPane box) {
        excursao = excursao == null ? new ExcursaoController() : excursao;
        excursao.combos();
        config(box, excursao);
    }

    public static void getEmprestimo(AnchorPane box) {
        emprestimo = emprestimo == null ? new EmprestimoController() : emprestimo;
        emprestimo.combos();
        config(box, emprestimo);
    }

    public static void getItensEmprestimo(AnchorPane box) {
        itens = itens == null ? new ItensController() : itens;
        config(box, itens);
    }

    public static void getDevolucao(AnchorPane box) {
        devolucao = devolucao == null ? new DevolucaoController() : devolucao;
        config(box, devolucao);
    }

    public static void getHistorico(AnchorPane box) {
        historico = historico == null ? new HistoricoController() : historico;
        config(box, historico);
    }

    public static void getOrganizacao(AnchorPane box) {
        organizacao = organizacao == null ? new OrganizacaoController() : organizacao;
        config(box, organizacao);
    }

    public static void getSetor(AnchorPane box) {
        setor = setor == null ? new SetorController() : setor;
        setor.combos();
        config(box, setor);
    }

    public static void getLocal(AnchorPane box) {
        local = local == null ? new LocalController() : local;
        local.combos();
        config(box, local);
    }

    public static void getLocalizacao(AnchorPane box) {
        localizacao = localizacao == null ? new LocalizacaoController() : localizacao;
        config(box, localizacao);
    }

    public static void getUsuario(AnchorPane box) {
        usuario = usuario == null ? new UsuarioController() : usuario;
        config(box, usuario);
    }

    public static void getIdentificacao(AnchorPane box) {
        identificacao = identificacao == null ? new IdentificacaoController() : identificacao;
        config(box, identificacao);
    }

    public static void getDashboard(AnchorPane box) {
        dashboard = dashboard == null ? new DashController() : dashboard;
        config(box, dashboard);
    }

    public static void getPesquisar(AnchorPane box) {
        pesquisa = pesquisa == null ? new PesquisarController() : pesquisa;
        config(box, pesquisa);
    }

    public static void getValidacao(AnchorPane box) {
        validacao = validacao == null ? new ValidacaoController() : validacao;
        config(box, validacao);
    }

    public static void getMovimentacao(AnchorPane box) {
        movimentacao = movimentacao == null ? new MovimentacaoController() : movimentacao;
        config(box, movimentacao);
    }

    /**
     * Configuração da tela de conteúdo para limpar painel e adicionar nova
     * tela, redimensionado seu tamanho para preencher a tela
     */
    public static void config(AnchorPane box, AnchorPane conteudo) {
        box.getChildren().clear();
        box.getChildren().add(conteudo);
        Resize.margin(conteudo, 0);
    }

    /**
     * Auxiliar na visualização de elementos da tela como: subenus, subtelas e
     * etc...
     */
    public static void visualizacao(boolean valor, Node... no) {
        for (Node elemento : no) {
            elemento.setVisible(valor);
        }
    }

}

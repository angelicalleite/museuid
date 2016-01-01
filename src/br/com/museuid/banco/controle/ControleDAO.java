package br.com.museuid.banco.controle;

import br.com.museuid.banco.dao.*;

/**
 * Classe responsável por realizar o controle dos objetos DAO que contém os
 * CRUDs e diversas operações na base de dados, filtrando a criação desses
 * objetos.
 *
 * @autor Angelica Leite
 */
public class ControleDAO {

    private static ControleDAO banco = new ControleDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private LoginDAO loginDAO = new LoginDAO();
    private CatalogacaoDAO catalogacaoDAO = new CatalogacaoDAO();
    private ColecaoDAO colecaoDAO = new ColecaoDAO();
    private DesignacaoDAO designacaoDAO = new DesignacaoDAO();
    private EstratigrafiaDAO estratigrafiaDAO = new EstratigrafiaDAO();
    private ExcursaoDAO excursaoDAO = new ExcursaoDAO();
    private InstituicaoDAO instituicaoDAO = new InstituicaoDAO();
    private VisitanteDAO visitanteDAO = new VisitanteDAO();
    private OrganizacaoDAO organizacaoDAO = new OrganizacaoDAO();
    private SetorDAO setorDAO = new SetorDAO();
    private LocalDAO localDAO = new LocalDAO();
    private MovimentacaoDAO movimentacaoDAO = new MovimentacaoDAO();
    private IdentificacaoDAO identificacaoDAO = new IdentificacaoDAO();
    private AuditoriaDAO auditoriaDAO = new AuditoriaDAO();
    private EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
    private RelatorioDAO relatorioDAO = new RelatorioDAO();
    private ValidacaoDAO validacaoDAO = new ValidacaoDAO();
    private LocalizacaoDAO localizacaoDAO = new LocalizacaoDAO();

    public static ControleDAO getBanco() {
        return banco;
    }

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public LoginDAO getLoginDAO() {
        return loginDAO;
    }

    public CatalogacaoDAO getCatalogacaoDAO() {
        return catalogacaoDAO;
    }

    public ColecaoDAO getColecaoDAO() {
        return colecaoDAO;
    }

    public DesignacaoDAO getDesignacaoDAO() {
        return designacaoDAO;
    }

    public EstratigrafiaDAO getEstratigrafiaDAO() {
        return estratigrafiaDAO;
    }

    public ExcursaoDAO getExcursaoDAO() {
        return excursaoDAO;
    }

    public InstituicaoDAO getInstituicaoDAO() {
        return instituicaoDAO;
    }

    public VisitanteDAO getVisitanteDAO() {
        return visitanteDAO;
    }

    public OrganizacaoDAO getOrganizacaoDAO() {
        return organizacaoDAO;
    }

    public SetorDAO getSetorDAO() {
        return setorDAO;
    }

    public LocalDAO getLocalDAO() {
        return localDAO;
    }

    public MovimentacaoDAO getMovimentacaoDAO() {
        return movimentacaoDAO;
    }

    public IdentificacaoDAO getIdentificacaoDAO() {
        return identificacaoDAO;
    }

    public AuditoriaDAO getAuditoriaDAO() {
        return auditoriaDAO;
    }

    public EmprestimoDAO getEmprestimoDAO() {
        return emprestimoDAO;
    }

    public RelatorioDAO getRelatorioDAO() {
        return relatorioDAO;
    }

    public ValidacaoDAO getValidacaoDAO() {
        return validacaoDAO;
    }

    public LocalizacaoDAO getLocalizacaoDAO() {
        return localizacaoDAO;
    }
}

package br.com.museuid.banco.controle.jpa;

import br.com.museuid.banco.dao.*;
import br.com.museuid.model.*;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by c1278778 on 06/09/2016.
 */
public class TesteScript {

    public static void main(String args[]){
        new CatalogacaoDAO().inserir(new Catalogacao("001"));
        new EstratigrafiaDAO().inserir(new Estratigrafia());
        new LocalDAO().inserir(new Local());
        new OrganizacaoDAO().inserir(new Organizacao());
        new SetorDAO().inserir(new Setor());
        new AuditoriaDAO().inserir(new Auditoria("acao", LocalDate.now(), "descricao",new Usuario("Usuario")));
        new ColecaoDAO().inserir(new Colecao("Colecao"));
        new DesignacaoDAO().inserir(new Designacao("Designacao"));
        new EmprestimoDAO().inserir(new Emprestimo("Emprestimo"));
        new EmprestimoItemDAO().inserir(new EmprestimoItem("EmprestimoItem"));
        new InstituicaoDAO().inserir(new Instituicao("Instituicao"));
        new ExcursaoDAO().inserir(new Excursao("curso", 1, "responsavel",
                "contato" , "guias", "horario", LocalDate.now(),
                "descricao", true, "statusAgendamento",
                new Instituicao("Instituicao")));
        new IdentificacaoDAO().inserir(new Identificacao(new Catalogacao(), new Estratigrafia(),
                new Colecao("Colecao"), new  Local(), new Designacao("Designacao"), new Emprestimo("Emprestimo")));
        new LocalizacaoDAO().inserir(new Localizacao(new Local(),new Catalogacao()));
        new MovimentacaoDAO().inserir(new Movimentacao("objetos", "responsavel", "origem", "destino", "tipo", "descricao", LocalDate.now()));

        new UsuarioDAO().inserir(new Usuario("admin", "123", new TipoUsuario("TipoUsuario0"), true));
        new TipoUsuarioDAO().inserir(new TipoUsuario("TipoUsuario1"));
        new ValidacaoDAO().inserir(new Validacao("categoria","subcategoria", 1, 2, 3, true, LocalDate.now(),
                new Usuario("Usuario"), new ArrayList<ValidacaoItem>()));
        new ValidacaoItemDAO().inserir(new ValidacaoItem(true, new Catalogacao("01")));
        new VisitanteDAO().inserir(new Visitante("Visitante"));
    }
}

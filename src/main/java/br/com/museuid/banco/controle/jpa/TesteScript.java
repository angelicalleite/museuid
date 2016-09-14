package br.com.museuid.banco.controle.jpa;

import br.com.museuid.banco.dao.*;
import br.com.museuid.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by c1278778 on 06/09/2016.
 */
public class TesteScript {

    public static void main(String args[]){

        Catalogacao catalogacao = new CatalogacaoDAO().inserir(new Catalogacao("001"));
        Estratigrafia estratigrafia = new EstratigrafiaDAO().inserir(new Estratigrafia());
        Local local = new LocalDAO().inserir(new Local());
        new OrganizacaoDAO().inserir(new Organizacao());
        new SetorDAO().inserir(new Setor());
        new AuditoriaDAO().inserirObj(new Auditoria("acao", LocalDate.now(), "descricao", new UsuarioDAO().inserir(new Usuario("Usuario"))));
        Colecao colecao = new ColecaoDAO().inserir(new Colecao("Colecao"));
        Designacao designacao = new DesignacaoDAO().inserir(new Designacao("Designacao"));
        Emprestimo emprestimo = new EmprestimoDAO().inserir(new Emprestimo("Emprestimo"));
        new EmprestimoItemDAO().inserir(new EmprestimoItem("EmprestimoItem"));
        new InstituicaoDAO().inserir(new Instituicao("Instituicao"));
        new ExcursaoDAO().inserir(new Excursao("curso", 1, "responsavel",
                "contato" , "guias", "horario", LocalDate.now(),
                "descricao", true, "statusAgendamento",
                new InstituicaoDAO().inserir(new Instituicao("Instituicao"))));

        new IdentificacaoDAO().inserir(new Identificacao(catalogacao, estratigrafia,
               colecao, local, designacao, emprestimo));

        new LocalizacaoDAO().inserir(new Localizacao(local,catalogacao));

        new MovimentacaoDAO().inserir(new Movimentacao("objetos", "responsavel", "origem", "destino", "tipo", "descricao", LocalDate.now()));
        TipoUsuario tipoUsuario = new TipoUsuarioDAO().inserir(new TipoUsuario("TipoUsuario0"));
        Usuario usuario = new UsuarioDAO().inserir(new Usuario("admin", "123", tipoUsuario, true));

        ValidacaoItem validacaoItem = new ValidacaoItemDAO().inserir(new ValidacaoItem(true, catalogacao));
        List<ValidacaoItem> list = new ArrayList<ValidacaoItem>();
        list.add(validacaoItem);
        new ValidacaoDAO().inserir(new Validacao("categoria","subcategoria", 1, 2, 3, true, LocalDate.now(), usuario, list));
        new VisitanteDAO().inserir(new Visitante("Visitante"));
    }
}

package br.com.museuid.test;

import br.com.museuid.banco.dao.AuditoriaDAO;
import br.com.museuid.banco.dao.TipoUsuarioDAO;
import br.com.museuid.banco.dao.UsuarioDAO;
import br.com.museuid.model.Auditoria;
import br.com.museuid.model.TipoUsuario;
import br.com.museuid.model.Usuario;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by c1278778 on 12/09/2016.
 */
public class AuditoriaDAOTest {

    @Test(timeout=7000)
    public void inserir() {

        TipoUsuario tipoUsuario = new TipoUsuarioDAO().inserir(new TipoUsuario("admin1"));
        Assert.assertEquals("init TipoUsuario", tipoUsuario, tipoUsuario);

        Usuario usuario = new UsuarioDAO().inserir(new Usuario("admin", "123", tipoUsuario, false));
        Assert.assertEquals("init usuario", usuario, usuario);

        Assert.assertEquals("init ", true, new AuditoriaDAO().inserir(
                    new Auditoria("acao", LocalDate.now(), "descricao", usuario)
                )
        );
    }

    @Test
    public void excluir() {
        AuditoriaDAO<Auditoria> dao = new AuditoriaDAO();
        //Auditoria auditoria = dao.get(Auditoria.class, 1);
        //System.out.println(auditoria.getId());
        Assert.assertEquals("init ", true,  new AuditoriaDAO().excluir(3));
    }
}

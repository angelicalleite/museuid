package br.com.museuid;

import br.com.museuid.banco.dao.LoginDAO;
import org.junit.Assert;
import org.junit.Test;

public class SQLTest {
    @Test
    public void autenticarUsername() {
        Assert.assertEquals("autenticarUsername init ", true, new LoginDAO().autenticarUsername("admin"));
    }
    @Test
    public void autenticarSenha() {
        Assert.assertEquals("autenticarSenha init ", false, new LoginDAO().autenticarSenha("admin", "123"));
    }
    @Test
    public void usuarioLogado() {
        Assert.assertEquals("usuarioLogado init ", null, new LoginDAO().usuarioLogado("admin"));

    }
}

package br.com.museuid.util;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Criar links para ações que abrem uma pagina no navegador
 */
public class Link {

    private Link() {
    }

    /**
     * Auxilia exibição de endereços web no desktop padrão
     */
    public static void endereco(String link) {
        try {
            Desktop.getDesktop().browse(new URI(link));
        } catch (URISyntaxException | IOException ex) {
            Mensagem.erro("Não possível exibir www.fapce.edu.br !");
        }
    }

}

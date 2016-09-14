package br.com.museuid.util;

import br.com.museuid.model.Relatorio;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Auxilia no controle e gestão da criação e geração dos relatorios
 */
public class Relatorios {

    private Relatorios() {
    }

    /**
     * Filtrar o tipo do relatorio e chamar a função correspondente para criação do grafico
     */
    public static void criar(AnchorPane box, String relatorio, int periodo, LocalDate data) {
        switch (relatorio) {
            case "Movimentação":
                movimentacao(box, periodo, data);
                break;
            case "Empréstimo":
                emprestimo(box, periodo, data);
                break;
            case "Visitas":
                visitas(box, periodo, data);
                break;
            case "Validação":
                validacao(box, periodo, data);
                break;
            case "Data Catalogação":
                dataCatalogacao(box, periodo, data);
                break;
            case "Procêdencia":
                procedencia(box);
                break;
            case "Dimensões":
                dimensoes(box);
                break;
            case "N° Partes":
                numeroPartes(box);
                break;
            case "Localização":
                localizacao(box);
                break;
            case "Designação":
                designacao(box);
                break;
            case "Estratigrafia":
                estratigrafia(box);
                break;
            case "Coleção":
                colecao(box);
                break;
            default:
                Mensagem.alerta("Relátorio informado não encontrado.");
        }
    }

    /**
     * Dados e configurações para geração do gráfico periodicos das datas de catalogações
     */
    private static void dataCatalogacao(AnchorPane panel, int periodo, LocalDate data) {
        if (periodo == 1) {
            boxGrafico(panel, "Data Catalogações", "", periodo, Consultas.catalogacaoDiaria(data));
        } else if (periodo == 2) {
            boxGrafico(panel, "Data Catalogações", "Dia", periodo, Consultas.catalogacaoMensal(data));
        } else if (periodo == 3) {
            boxGrafico(panel, "Data Catalogações", "Mês", periodo, Consultas.catalogacaoAnual(data));
        }
    }

    /**
     * Dados e configurações para geração do gráfico periodicos dos emprestimos
     */
    private static void emprestimo(AnchorPane panel, int periodo, LocalDate data) {
        if (periodo == 1) {
            boxGrafico(panel, "Empréstimo", "", periodo, Consultas.emprestimoDiaria(data));
        } else if (periodo == 2) {
            boxGrafico(panel, "Empréstimo", "Dia", periodo, Consultas.emprestimoMensal(data));
        } else if (periodo == 3) {
            boxGrafico(panel, "Empréstimo", "Mês", periodo, Consultas.emprestimoAnual(data));
        }
    }

    /**
     * Dados e configurações para geração do gráfico periodicos das movimentações
     */
    private static void movimentacao(AnchorPane panel, int periodo, LocalDate data) {
        if (periodo == 1) {
            boxGrafico(panel, "Movimentações", "", periodo, Consultas.movimentacaoDiaria(data));
        } else if (periodo == 2) {
            boxGrafico(panel, "Movimentações", "Dia", periodo, Consultas.movimentacaoMensal(data));
        } else if (periodo == 3) {
            boxGrafico(panel, "Movimentações", "Mês", periodo, Consultas.movimentacaoAnual(data));
        }
    }

    /**
     * Dados e configurações para geração do gráfico periodicos das validações
     */
    private static void validacao(AnchorPane panel, int periodo, LocalDate data) {
        if (periodo == 1) {
            boxGrafico(panel, "Validações", "", periodo, Consultas.validacaoDiaria(data));
        } else if (periodo == 2) {
            boxGrafico(panel, "Validações", "Dia", periodo, Consultas.validacaoMensal(data));
        } else if (periodo == 3) {
            boxGrafico(panel, "Validações", "Mês", periodo, Consultas.validacaoAnual(data));
        }
    }

    /**
     * Dados e configurações para geração do gráfico periodicos das visitas
     */
    private static void visitas(AnchorPane panel, int periodo, LocalDate data) {
        if (periodo == 1) {
            boxGrafico(panel, "Validações", "", periodo, Consultas.visitasDiaria(data));
        } else if (periodo == 2) {
            boxGrafico(panel, "Validações", "", periodo, Consultas.visitasMensal(data));
        } else if (periodo == 3) {
            boxGrafico(panel, "Validações", "Mês", periodo, Consultas.visitasAnual(data));
        }
    }

    /**
     * Dados e configurações para geração do gráfico de precêdencias do acervo
     */
    private static void procedencia(AnchorPane panel) {
        boxGrafico(panel, "Procêdencia", "", 1, Consultas.procedencia());
    }

    /**
     * Dados e configurações para geração do gráfico de acordo com as dimensões do acervo
     */
    private static void dimensoes(AnchorPane panel) {
        boxGrafico(panel, "Dimensões", "", 1, Consultas.dimensoes());
    }

    /**
     * Dados e configurações para geração do gráfico de acordo com o número de partes do acervo
     */
    private static void numeroPartes(AnchorPane panel) {
        boxGrafico(panel, "N° de Partes", "", 1, Consultas.numeroPartes());
    }

    /**
     * Dados e configurações para geração do gráfico de
     */
    private static void localizacao(AnchorPane panel) {
        boxGrafico(panel, "Localização Acervo", "", 1, Consultas.localizacao());
    }

    /**
     * Dados e configurações para geração do gráfico de designações do acervo
     */
    private static void designacao(AnchorPane panel) {
        boxGrafico(panel, "", "", 3, Consultas.designacao());
    }

    /**
     * Dados e configurações para geração do gráfico de formações estratigraficas do acervo
     */
    private static void estratigrafia(AnchorPane panel) {
        boxGrafico(panel, "", "", 3, Consultas.estratigrafia());
    }

    /**
     * Dados e configurações para geração do gráfico de coleções do acervo
     */
    private static void colecao(AnchorPane panel) {
        boxGrafico(panel, "", "", 3, Consultas.colecao());
    }

    /**
     * Verificar se maps contém conteudo e caso positivo montar grafico de acordo com seus dados e tipos para exibição e geração de relátorios
     */
    private static void boxGrafico(AnchorPane box, String titulo, String eixo, int periodo, Map<String, List<Relatorio>> map) {
        if (!map.isEmpty()) {
            switch (periodo) {
                case 1:
                    boxConteudo(box, GraficoPie.criar(titulo, map));
                    break;
                case 2:
                    boxConteudo(box, GraficoLine.criar(titulo, eixo, map));
                    break;
                case 3:
                    boxConteudo(box, GraficoBar.criar(titulo, eixo, map));
                    break;
            }
        } else {
            graficoVazio(box);
        }

        map.clear();
    }

    /**
     * Informar quando grafico não contém conteúdo para exibição
     */
    private static void graficoVazio(AnchorPane box) {
        Label info = new Label("Dados não encontrados ou vazios para geração dos relátorios !");
        info.getStyleClass().add("conteudo-vazio");
        boxConteudo(box, info);
    }

    /**
     * Exibir gráfico no boxGrafico de conteúdos
     */
    private static void boxConteudo(AnchorPane box, Node conteudo) {
        box.getChildren().clear();
        Resize.margin(conteudo, 0);
        box.getChildren().add(conteudo);
    }
}

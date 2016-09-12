package br.com.museuid.controller;

import br.com.museuid.banco.dao.*;
import br.com.museuid.model.Local;
import br.com.museuid.model.Localizacao;
import br.com.museuid.model.Organizacao;
import br.com.museuid.model.Setor;
import br.com.museuid.util.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

public class LocalizacaoController extends AnchorPane {

    private List<Localizacao> lista;

    @FXML
    private Label lbTitulo;
    @FXML
    private Label legenda;
    @FXML
    private TextField txtNumOrdem;
    @FXML
    private ComboBox<Setor> cbSetor;
    @FXML
    private ComboBox<Local> cbLocal;
    @FXML
    private ComboBox<Organizacao> cbOrganizacao;
    @FXML
    private TableView<Localizacao> tbLocalizacao;
    @FXML
    private TableColumn colNumOrdem;
    @FXML
    private ToggleGroup menu;
    @FXML
    private TableColumn colDesignacao;
    @FXML
    private TableColumn colColecao;
    @FXML
    private TableColumn colEstratigrafia;

    public LocalizacaoController() {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/fxml/localizacao.fxml"));

            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();

        } catch (IOException ex) {
            Mensagem.erro("Erro ao carregar tela pesquisar localização! \n" + ex);
        }
    }

    @FXML
    void telaLocalizacao(ActionEvent event) {
        config("Localização Acervo", "Campos obrigatórios", 0);
    }

    @FXML
    void adicionar(ActionEvent event) {

        boolean vazio = Campo.noEmpty(txtNumOrdem);

        Local local = cbLocal.getValue();
        String ordem = txtNumOrdem.getText();

        CatalogacaoDAO catalogacaoDAO = new CatalogacaoDAO();

        if (vazio) {
            Nota.alerta("Preencher campos vazios!");
        } else if (cbLocal.getItems().isEmpty()) {
            Nota.alerta("Local não encontrados!");
        } else {
            int catalogacao = catalogacaoDAO.infoId(ordem);//consultar identificador da catalogacao atraves de seu numero de ordem

            if (catalogacao == 0) {
                Nota.alerta("Catalogação não encontrada!");
            } else if (new LocalizacaoDAO().isLocalizacao(catalogacao)) {
                Nota.info("Catalogação já possui cadastro de localização");
            } else {
                new LocalizacaoDAO().inserir(local.getId(), catalogacao);
            }

            baseDados(cbLocal.getValue() == null ? 0 : cbLocal.getValue().getId());
            tabela();
            limpar();
        }
    }

    @FXML
    void remover(ActionEvent event) {
        try {
            new LocalizacaoDAO().excluir(tbLocalizacao.getSelectionModel().getSelectedItem().getId());

            baseDados(cbLocal.getValue() == null ? 0 : cbLocal.getValue().getId());
            tabela();
        } catch (NullPointerException ex) {
            Nota.alerta("Selecione item da localização que deseja remover na tabela!");
        }
    }

    @FXML
    void initialize() {
        Grupo.notEmpty(menu);

        combos();
        escutadoresCombos();
        baseDados(cbLocal.getValue() == null ? 0 : cbLocal.getValue().getId());
        tabela();

        telaLocalizacao(null);
    }

    /**
     * Preencher combos estaticos e dinamicos
     */
    public void combos() {
        Combo.popular(cbOrganizacao, new OrganizacaoDAO().combo());
        Combo.popular(cbSetor, new SetorDAO().combo(cbOrganizacao.getValue() == null ? 0 : cbOrganizacao.getValue().getId()));
        Combo.popular(cbLocal, new LocalDAO().combo(cbSetor.getValue() == null ? 0 : cbSetor.getValue().getId()));
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void config(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);

        legenda.setText(msg);
        tbLocalizacao.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));
    }

    /**
     * Sincronizar dados com banco de dados
     */
    private void baseDados(int local) {
        lista = new LocalizacaoDAO().localizacoes(local);
    }

    /**
     * Mapear dados objetos para inserção dos dados na tabela
     */
    private void tabela() {
        ObservableList data = FXCollections.observableArrayList(lista);

        colNumOrdem.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Localizacao, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Localizacao, String> obj) {
                return new SimpleStringProperty(obj.getValue().getCatalogacao().getNumeroOrdem());
            }
        });
        colDesignacao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Localizacao, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Localizacao, String> obj) {
                return new SimpleStringProperty(obj.getValue().getCatalogacao().getDesignacao().getGenero());
            }
        });
        colEstratigrafia.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Localizacao, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Localizacao, String> obj) {
                return new SimpleStringProperty(obj.getValue().getCatalogacao().getEstratigrafia().getFormacao());
            }
        });
        colColecao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Localizacao, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Localizacao, String> obj) {
                return new SimpleStringProperty(obj.getValue().getCatalogacao().getColecao().getNome());
            }
        });

        tbLocalizacao.setItems(data);
    }

    /**
     * Limpar campos textfield
     */
    private void limpar() {
        Campo.limpar(txtNumOrdem);
    }

    /**
     * Adicionar escutadores aos combos para caso aconteça alguma ação ou mudança cascatear as demais mudanças nos
     * combos subsequentes
     */
    private void escutadoresCombos() {
        cbOrganizacao.valueProperty().addListener(new ChangeListener<Organizacao>() {
            @Override
            public void changed(ObservableValue ov, Organizacao old, Organizacao novo) {
                Combo.popular(cbSetor, new SetorDAO().combo(novo == null ? 0 : novo.getId()));
            }
        });
        cbSetor.valueProperty().addListener(new ChangeListener<Setor>() {
            @Override
            public void changed(ObservableValue ov, Setor old, Setor novo) {
                Combo.popular(cbLocal, new LocalDAO().combo(novo == null ? 0 : novo.getId()));
            }
        });
        cbLocal.valueProperty().addListener(new ChangeListener<Local>() {
            @Override
            public void changed(ObservableValue ov, Local old, Local novo) {
                baseDados(novo == null ? 0 : novo.getId());
                tabela();
            }
        });
    }
}
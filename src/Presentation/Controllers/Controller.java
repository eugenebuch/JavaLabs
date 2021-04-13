package Presentation.Controllers;

import Models.BankAccount;
import Models.Client;
import Repositories.BankAccountRepository;
import Services.BankService;
import Services.OperationHistoryService;
import Services.Parsers.JsonParser;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller {
    private BankService bankService;
    private OperationHistoryService historyService;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<Client> clientsList;

    @FXML
    private Button createUserBtn;

    @FXML
    private Button loadBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private Button viewBtn;

    @FXML
    private Button createAccountBtn;

    @FXML
    private Button operateBtn;

    @FXML
    private MenuButton changeAccountBtn;

    @FXML
    private Label emptyInfo;

    @FXML
    private Label accountInfo;

    @FXML
    void initialize() {
        // services arrangement
        changeAccountBtn.getItems().clear();
        bankService = BankService.getInstance(null, null, null);
        historyService = OperationHistoryService.getInstance(new JsonParser());

        // setting list
        ObservableList<Client> observableList = FXCollections.observableList(bankService
                .getClientRepository().getAll()
        );

        clientsList.setItems(observableList);
        clientsList.setOnMouseClicked(event ->
                setSelectionChanged(clientsList.getSelectionModel().selectedItemProperty()));

        // setting buttons
        checkButtonsAccessibility();
        operateBtn.setDisable(true);

        loadBtn.setOnAction(event -> {
                bankService.getOperationRepository()
                        .loadData(historyService.getOperationsHistory());
                initialize();
        });
        saveBtn.setOnAction(event -> historyService
                .saveOperationsHistory(bankService.getOperationRepository().getAll()));

        createUserBtn.setOnAction(event -> {
            try {
                setCreateUserBtn();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        viewBtn.setOnAction(event -> {
            try {
                setViewBtn();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setSelectionChanged(ReadOnlyObjectProperty<Client> selectedClient) {
        accountInfo.setText("Empty");
        operateBtn.setDisable(true);
        changeAccountBtn.getItems().clear();
        BankAccountRepository accountRepository =  bankService.getBankAccountRepository();

        List<BankAccount> bankAccountList = accountRepository
                .getAllByPredicate(x -> x.getOwner().getId() == selectedClient.get().getId());

        createAccountBtn.setOnAction(event -> setCreateAccountBtn(selectedClient));

        for (BankAccount ba:
             bankAccountList) {
            MenuItem mi = new MenuItem(String.valueOf(ba.getNumber()));
            changeAccountBtn.getItems().add(mi);
            mi.setOnAction(event -> menuItemChanged(ba));
        }

        emptyInfo.setText("Name: " + selectedClient.get().getName()
                + "\nAccounts total: " + bankAccountList.size()
                + "\nTotal funds: " + bankAccountList.stream().mapToDouble(BankAccount::getFunds).sum()
        );
    }

    private void menuItemChanged(BankAccount ba) {
        operateBtn.setDisable(false);
        accountInfo.setText(ba.toString());
        operateBtn.setDisable(!ba.isOpened());

        operateBtn.setOnAction(event -> {
            try {
                setOperateBtn(ba);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void checkButtonsAccessibility() {
        boolean isEmpty = bankService.getOperationRepository().getAll().isEmpty();
        saveBtn.setDisable(isEmpty);
        loadBtn.setDisable(isEmpty);
    }

    private void setCreateAccountBtn(ReadOnlyObjectProperty<Client>  client) {
        bankService.getBankAccountRepository().add(new BankAccount(client.get()));
        setSelectionChanged(client);
    }

    private void setCreateUserBtn() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scenes/CreateUserScene.fxml"));
        loader.load();

        Stage stage = showWindowHelper(loader, "Create Client");

        stage.setOnCloseRequest(event -> initialize());
    }

    private void setOperateBtn(BankAccount currentBankAccount) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scenes/OperateScene.fxml"));
        loader.load();

        OperateController controller = loader.getController();
        controller.setBankAccount(currentBankAccount);

        Stage stage = showWindowHelper(loader, "Operate with account: " + currentBankAccount.getNumber());

        stage.setOnCloseRequest(event -> accountInfo.setText(bankService.getBankAccountRepository()
                .get(currentBankAccount.getId()).toString()));
    }

    private void setViewBtn() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Scenes/ViewScene.fxml"));
        loader.load();

        showWindowHelper(loader, "View Operations");
    }

    private Stage showWindowHelper(FXMLLoader loader, String name) {
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle(name);
        stage.setAlwaysOnTop(true);
        stage.show();

        return stage;
    }
}

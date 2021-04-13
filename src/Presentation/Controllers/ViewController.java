package Presentation.Controllers;

import Models.Operation;
import Services.BankService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<Operation> operationsList;

    @FXML
    void initialize() {
        BankService bankService = BankService.getInstance(null, null, null);

        // setting list
        ObservableList<Operation> observableList = FXCollections.observableList(bankService
                .getOperationRepository().getAll()
        );

        operationsList.setItems(observableList);
    }
}

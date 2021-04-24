package Presentation.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import Models.Client;
import Repositories.ClientRepository;
import Services.BankService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CreateUserController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField nameForm;

    @FXML
    private Button createBtn;

    @FXML
    void initialize() {
        createBtn.setOnAction(event -> createClient(nameForm.getText()));
    }

    void createClient(String name) {
        if (name.isEmpty()) {
            return;
        }

        BankService bankService = BankService.getInstance(null, null, null);
        ClientRepository clientRepository = bankService.getClientRepository();
        clientRepository.add(new Client(-1, name));
        bankService.setClientRepository(clientRepository);
    }
}
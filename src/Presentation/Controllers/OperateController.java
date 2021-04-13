package Presentation.Controllers;

import Models.BankAccount;
import Services.BankService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class OperateController {
    BankAccount bankAccount;
    BankService bankService;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField amountField;

    @FXML
    private TextField recieverIDField;

    @FXML
    private Button operateBtn;

    @FXML
    private MenuButton operationMenu;

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @FXML
    void initialize() {
        operationMenu.getItems().clear();

        bankService = BankService.getInstance(null, null, null);
        if (!operationMenu.getText().equals("Send")) {
            recieverIDField.setDisable(true);
        }

        String[] operates = new String[]{ "Deposit", "Withdraw", "Send"};
        for (String op:
             operates) {
            MenuItem mi = new MenuItem(op);
            operationMenu.getItems().add(mi);
            mi.setOnAction(event -> menuItemChanged(op));
        }

        operateBtn.setOnAction(event -> setOperateBtn());
    }

    private void menuItemChanged(String op) {
        operationMenu.setText(op);
        if (operationMenu.getText().equals("Send")) {
            recieverIDField.setDisable(false);
        }
    }

    private void setOperateBtn() {
        switch (operationMenu.getText()) {
            case ("Deposit"):
                bankService.deposit(bankAccount.getId(), Double.parseDouble(amountField.getText()));
                break;
            case ("Withdraw"):
                bankService.withdraw(bankAccount.getId(), Double.parseDouble(amountField.getText()));
                break;
            case ("Send"):
                bankService.send(bankAccount.getId(), Integer.parseInt(recieverIDField.getText()),
                        Double.parseDouble(amountField.getText()));
                break;
        }
    }
}

import Repositories.BankAccountRepository;
import Repositories.ClientRepository;
import Repositories.OperationRepository;
import Services.BankService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Models.BankAccount;
import Models.Client;
import Models.Operation;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Presentation/Scenes/MainScene.fxml"));
        primaryStage.setTitle("Bank Account Manager");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        TestRun();      // Generates Sample Data
        launch(args);
    }

    public static void TestRun() {
        BankAccountRepository bankAccountRepository = new BankAccountRepository();
        ClientRepository clientRepository = new ClientRepository();
        OperationRepository operationRepository = new OperationRepository();

        Client client1 = new Client("Ivan");
        clientRepository.add(client1);
        Client client2 = new Client("Petr");
        clientRepository.add(client2);

        BankAccount bankAccount1 = new BankAccount(client1);
        bankAccount1.setFunds(200);
        bankAccountRepository.add(bankAccount1);
        BankAccount bankAccount2 = new BankAccount(client1);
        bankAccount2.setFunds(1);
        bankAccountRepository.add(bankAccount2);
        BankAccount bankAccount3 = new BankAccount(client2);
        bankAccount3.setFunds(1337);
        bankAccountRepository.add(bankAccount3);

        bankAccount1.setFunds(100);
        bankAccountRepository.update(bankAccount1.getId(), bankAccount1);

        BankService bankService = BankService.getInstance(
                bankAccountRepository,
                operationRepository,
                clientRepository);

        bankService.withdraw(bankAccount1.getId(), 10);
        bankService.changeAccountStatus(bankAccount2.getId());
        //bankAccountService.deposit(bankAccount2.getId(), 30);
        bankService.send(bankAccount3.getId(), bankAccount1.getId(), 50);

        System.out.println("Operations:");
        for (Operation op:
             operationRepository.getAll()) {
            System.out.println(op);
        }

        System.out.println("\nBankAccounts:");
        for (BankAccount ba:
             bankAccountRepository.getAll()) {
            System.out.println(ba);
        }

        System.out.println("\nClients:");
        for (Client cl:
             clientRepository.getAll()) {
            System.out.println(cl);
        }
    }
}

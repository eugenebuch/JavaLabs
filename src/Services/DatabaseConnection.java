package Services;

import Models.BankAccount;
import Models.Client;
import Models.Operation;
import Repositories.BankAccountRepository;
import Repositories.ClientRepository;
import Repositories.OperationRepository;

import java.sql.*;

public class DatabaseConnection {
    private Connection connection;
    private Statement statement;
    private BankService bankService;

    public void init() throws SQLException, ClassNotFoundException {
        bankService = BankService.getInstance(new BankAccountRepository(),
                new OperationRepository(), new ClientRepository());
        String password = "123456";
        String username = "postgres";
        String connectionString = "jdbc:postgresql://localhost:5432/postgres";
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(connectionString, username, password);
        if (connection == null)
            throw new SQLException();
        statement = connection.createStatement();
    }

    public void load() throws SQLException {
        ClientRepository clientRepository = bankService.getClientRepository();
        BankAccountRepository bankAccountRepository = bankService.getBankAccountRepository();
        OperationRepository operationRepository = bankService.getOperationRepository();

        ResultSet clientsSet = statement.executeQuery(
                "SELECT * FROM client"
        );

        while (clientsSet.next()) {
            clientRepository.add(new Client(
                    clientsSet.getInt("id"),
                    clientsSet.getString("name")
            ));
        }

        ResultSet bankAccountsSet = statement.executeQuery(
                "SELECT * FROM bankaccount"
        );
        while (bankAccountsSet.next()) {
            bankAccountRepository.add(new BankAccount(
                    bankAccountsSet.getInt("id"),
                    bankAccountsSet.getInt("number"),
                    clientRepository.get(bankAccountsSet.getInt("clientid") - 1),
                    bankAccountsSet.getDouble("funds"),
                    bankAccountsSet.getBoolean("isopened")
            ));
        }

        ResultSet operationsSet = statement.executeQuery(
                "SELECT * FROM operation"
        );
        while (operationsSet.next()) {
            operationRepository.add(new Operation(
                    operationsSet.getInt("id"),
                    operationsSet.getString("name"),
                    bankAccountRepository.get(operationsSet.getInt("bankaccountid") - 1),
                    operationsSet.getDouble("amount")
            ));
        }

        bankService.setClientRepository(clientRepository);
        bankService.setBankAccountRepository(bankAccountRepository);
        bankService.setOperationRepository(operationRepository);
    }

    public void save() throws SQLException {
        //TODO: make save logic with update old and adding new values (repository reference)
        ClientRepository clientRepository = bankService.getClientRepository();
        BankAccountRepository bankAccountRepository = bankService.getBankAccountRepository();
        OperationRepository operationRepository = bankService.getOperationRepository();

        PreparedStatement preparedStatement = connection
                .prepareStatement("INSERT INTO client(name) values (?)");
        for (Client client:
             clientRepository.getAll()) {
            preparedStatement.setString(1, client.getName());
            preparedStatement.executeUpdate();
        }

        preparedStatement = connection
                .prepareStatement("INSERT INTO bankaccount(number, clientid, funds, isopened) values (?, ?, ?, ?)");
        for (BankAccount ba:
             bankAccountRepository.getAll()) {
            preparedStatement.setInt(1, ba.getNumber());
            preparedStatement.setInt(2, ba.getOwner().getId());
            preparedStatement.setDouble(3, ba.getFunds());
            preparedStatement.setBoolean(4, ba.isOpened());
            preparedStatement.executeUpdate();
        }

        preparedStatement = connection
                .prepareStatement("INSERT INTO operation(id, name, bankaccountid, amount) values (?, ?, ?, ?)");
        for (Operation op:
                operationRepository.getAll()) {
            preparedStatement.setInt(1, op.getId());
            preparedStatement.setString(2, op.getName());
            preparedStatement.setInt(3, op.getBankAccount().getId());
            preparedStatement.setDouble(4, op.getAmount());
            preparedStatement.executeUpdate();
        }
    }

    public void close() throws SQLException {
        if (connection != null) {
            statement.close();
            connection.close();
        }
    }
}

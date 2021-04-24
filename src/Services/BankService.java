package Services;

import Models.BankAccount;
import Models.Operation;
import Repositories.BankAccountRepository;
import Repositories.ClientRepository;
import Repositories.OperationRepository;
import org.jetbrains.annotations.NotNull;

public final class BankService {
    private BankAccountRepository bankAccountRepository;
    private OperationRepository operationRepository;
    private ClientRepository clientRepository;

    private static volatile BankService instance;

    private BankService(BankAccountRepository bankAccountRepository,
                       OperationRepository operationRepository,
                       ClientRepository clientRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.operationRepository = operationRepository;
        this.clientRepository = clientRepository;
    }

    public static BankService getInstance(BankAccountRepository bankAccountRepository,
                                          OperationRepository operationRepository,
                                          ClientRepository clientRepository) {
        BankService result = instance;
        if (result != null) {
            return result;
        }
        synchronized(BankService.class) {
            if (instance == null) {
                instance = new BankService(
                        bankAccountRepository,
                        operationRepository,
                        clientRepository);
            }
            return instance;
        }
    }

    public BankAccountRepository getBankAccountRepository() {
        return bankAccountRepository;
    }

    public OperationRepository getOperationRepository() {
        return operationRepository;
    }

    public ClientRepository getClientRepository() { return clientRepository; }

    public void setBankAccountRepository(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public void setOperationRepository(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void withdraw(int id, double amount) {
        BankAccount acc = isAccValid(id, amount);

        double funds = acc.getFunds();
        if (funds - amount < 0) {
            throw new IllegalArgumentException("Can't withdraw more than an " +
                    "account with id {"+ acc.getId() +"}");
        }
        acc.setFunds(funds - amount);

        updateEntity(acc, "Withdraw", amount);
    }

    public void deposit(int id, double amount) {
        BankAccount acc = isAccValid(id, amount);

        acc.setFunds(acc.getFunds() + amount);

        updateEntity(acc, "Deposit", amount);
    }

    public void send(int senderId, int receiverId, double amount) {
        if ((int) clientRepository.getAll().stream()
                .filter(x -> x.getId() == receiverId).count() == 0) {
            throw new IllegalArgumentException("Client not found");
        }

        withdraw(senderId, amount);
        deposit(receiverId, amount);
    }

    public void changeAccountStatus(int id) {
        bankAccountRepository.get(id).setOpened(!bankAccountRepository.get(id).isOpened());
    }

    private @NotNull
    BankAccount isAccValid(int id, double amount) {
        BankAccount acc = bankAccountRepository.get(id);

        // TODO: Add middlewares and custom exceptions
        if (!acc.isOpened()) {
            throw new IllegalArgumentException("Can't handle closed account " +
                    "with id {"+ acc.getId() +"}");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid amount when withdraw from " +
                    "account with id {"+ acc.getId() +"}");
        }

        return acc;
    }

    private void updateEntity(BankAccount acc, String operationName, double amount) {
        Operation op = new Operation(acc, operationName, amount);

        bankAccountRepository.update(acc.getId(), acc);
        operationRepository.add(op);
    }
}

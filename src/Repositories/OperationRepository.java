package Repositories;

import Models.BankAccount;
import Models.Operation;
import Services.BankService;

import java.util.List;

public class OperationRepository extends BaseRepository<Operation> {
    public void loadData(List<Operation> list) {
        BankService bankService = BankService.getInstance(null, null, null);
        BankAccountRepository bankAccountRepository = bankService.getBankAccountRepository();
        ClientRepository clientRepository = bankService.getClientRepository();

        this.list = list;
        for (Operation op:
             list) {
            BankAccount currBA = op.getBankAccount();
            if (!bankAccountRepository.getAll().contains(currBA)) {
                bankAccountRepository.add(currBA);
            }
            else {
                bankAccountRepository.update(currBA.getId(), op.getBankAccount());
            }
            if (!clientRepository.getAll().contains(currBA.getOwner())) {
                clientRepository.add(currBA.getOwner());
            }
        }

        // save in current service session
        BankService.getInstance(null, null, null)
                .setBankAccountRepository(bankAccountRepository);
        BankService.getInstance(null, null, null)
                .setClientRepository(clientRepository);
    }
}

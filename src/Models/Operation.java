package Models;

import java.io.Serializable;

public class Operation implements Serializable {
    private int id;
    private static int _id = 0;
    private final BankAccount bankAccount;
    private final int bankAccountId;
    private final String name;
    private final double amount;

    public Operation(BankAccount bankAccount, String name, double amount) {
        _id = id + 1;
        this.bankAccount = bankAccount;
        bankAccountId = bankAccount.getId();
        this.name = name;
        this.amount = amount;
    }

    public Operation(int id, String name, BankAccount bankAccount, double amount) {
        this.id = id;
        _id++;
        this.bankAccount = bankAccount;
        this.bankAccountId = bankAccount.getId();
        this.name = name;
        this.amount = amount;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public double getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "bankAccountNumber =" + bankAccount.getNumber() +
                ", operation = " + name  +
                ", amount = " + amount;
    }

    public int getId() {
        return id;
    }

    public int getBankAccountId() {
        return bankAccountId;
    }
}

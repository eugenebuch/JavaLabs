package Models;

import java.io.Serializable;

public class Operation implements Serializable {
    private static int _id = 0;
    private final int id;
    private final BankAccount bankAccount;
    private final String name;
    private final double amount;

    public Operation(BankAccount bankAccount, String name, double amount) {
        id = _id++;
        this.bankAccount = bankAccount;
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
}

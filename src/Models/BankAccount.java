package Models;

import java.io.Serializable;
import java.util.Objects;

public class BankAccount implements Serializable {
    private int id;
    private static int _id = 0;
    private final int number;
    private final Client owner;
    private final int clientId;
    private double funds = 0;
    private boolean isOpened = true;

    public BankAccount(Client owner) {
        _id = id + 1;
        this.owner = owner;
        clientId = owner.getId();
        number = (owner.getName() + id).hashCode();
    }

    public BankAccount(int id, int number, Client owner, double funds, boolean isOpened) {
        this.id = id;
        _id++;
        this.number = number;
        this.owner = owner;
        clientId = owner.getId();
        this.funds = funds;
        this.isOpened = isOpened;
    }

    public int getId() {
        return id;
    }

    public Client getOwner() {
        return owner;
    }

    public double getFunds() {
        return funds;
    }

    public void setFunds(double funds) {
        this.funds = funds;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    @Override
    public String toString() {
        return "Number: " + number +
                "\nFunds: " + funds +
                (isOpened ? "" : "\nAccount is closed, contact admin to open it");
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankAccount)) return false;
        BankAccount that = (BankAccount) o;
        return id == that.id && number == that.number
                && isOpened == that.isOpened && owner.equals(that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, owner, funds);
    }

    public int getClientId() {
        return clientId;
    }
}

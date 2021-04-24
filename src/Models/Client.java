package Models;

import java.io.Serializable;
import java.util.Objects;

public class Client implements Serializable {
    private int id;
    private static int _id = 0;
    private String name;

    public Client(int id, String name) {
        if (id == -1)
            this.id = _id;
        else
            this.id = id;
        _id = id + 1;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " [id: "+ id +"]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return id == client.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

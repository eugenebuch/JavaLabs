package Models;

import java.io.Serializable;
import java.util.List;

public class OperationsList implements Serializable {
    private List<Operation> list;

    public void setList(List<Operation> list) {
        this.list = list;
    }

    public List<Operation> getList() {
        return list;
    }

}

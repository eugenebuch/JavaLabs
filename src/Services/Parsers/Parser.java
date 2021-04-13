package Services.Parsers;

import Models.OperationsList;

public interface Parser {
    void saveData(OperationsList data);

    OperationsList loadData();
}

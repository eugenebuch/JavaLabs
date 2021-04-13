package Services;

import Models.Operation;
import Models.OperationsList;
import Services.Parsers.Parser;

import java.util.List;

public class OperationHistoryService {
    private final Parser parser;

    private static volatile OperationHistoryService instance;

    private OperationHistoryService(Parser parser) {
        this.parser = parser;
    }

    public static OperationHistoryService getInstance(Parser parser) {
        OperationHistoryService result = instance;
        if (result != null) {
            return result;
        }
        synchronized(OperationHistoryService.class) {
            if (instance == null) {
                instance = new OperationHistoryService(parser);
            }
            return instance;
        }
    }

    public List<Operation> getOperationsHistory() {
        return parser.loadData().getList();
    }

    public void saveOperationsHistory(List<Operation> list) {
        OperationsList opList = new OperationsList();
        opList.setList(list);
        parser.saveData(opList);
    }
}

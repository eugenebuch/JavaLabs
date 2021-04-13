package Services.Parsers;

import Models.OperationsList;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonParser implements Parser{
    private final Gson parser;

    public JsonParser() {
        parser = new Gson();
    }

    @Override
    public void saveData(OperationsList data) {
        try (FileWriter fw = new FileWriter("Operations.json", false)) {
            fw.write(parser.toJson(data));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public OperationsList loadData() {
        try {
            StringBuilder sb = new StringBuilder();
            Files.lines(Paths.get("Operations.json")).forEach(sb::append);
            return parser.fromJson(sb.toString(), OperationsList.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ClientLog {

    List<String[]> clientOperations = new ArrayList<>();

    public ClientLog() {
        clientOperations.add(new String[]{"productNumber", "Amount"});
    }

    public void log(int productNum, int amount) {
        clientOperations.add(new String[]{"" + productNum, "" + amount});
    }

    public void exportAsCSV(File txtFile) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(txtFile))) {
            for (String [] n :clientOperations) {
                writer.writeNext(n);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


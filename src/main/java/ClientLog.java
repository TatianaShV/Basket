import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringJoiner;

public class ClientLog {
    private static long stringToIntFunction = 8609175038441759607L;

    public String[] log(int productNum, int amount) {

        String operation = String.valueOf(productNum);
        String operation1 = String.valueOf(amount);
        String[] addOperation = {operation, operation1};
        return addOperation;

    }

    public void exportAsCSV(File txtFile) {
       /* Scanner scanner= new Scanner(System.in);
        String input = scanner.nextLine();
        String[] parts = input.split(" ");*/
        int productNumber = Integer.parseInt(parts[0]);
        int productCount = Integer.parseInt(parts[1]);
        String[] n = {"productNum", "amount"};
        try (CSVWriter writer = new CSVWriter(new FileWriter(txtFile))) {
            writer.writeNext(n);
            writer.writeNext(log(productNumber, productCount));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
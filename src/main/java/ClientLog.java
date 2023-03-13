import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

public class ClientLog {
    private static long stringToIntFunction = 8609175038441759607L;
    private int productNumber;
    private int amountOfProduct;

    public String[] log(int productNum, int amount) {
        setProductNumber(productNum);
        setAmountOfProduct(amount);
        String operation = String.valueOf(productNum);
        String operation1 = String.valueOf(amount);
        String[] addOperation = {operation, operation1};

        return addOperation;

    }

    public void exportAsCSV(File txtFile) {

        String[] n = {"productNum", "amount"};

        try (CSVWriter writer = new CSVWriter(new FileWriter(txtFile))) {
            writer.writeNext(n);
            writer.writeNext(log(getProductNumber(), getAmountOfProduct()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(int productNumber) {
        this.productNumber = productNumber;
    }

    public int getAmountOfProduct() {
        return amountOfProduct;
    }

    public void setAmountOfProduct(int amountOfProduct) {
        this.amountOfProduct = amountOfProduct;
    }
}
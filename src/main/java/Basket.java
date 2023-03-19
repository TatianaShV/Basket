

import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.ToIntFunction;

public class Basket {

    private static ToIntFunction<String> stringToIntFunction;
    protected String[] products;
    protected int[] prices;
    protected int sumProduct;
    protected int[] count;
    protected int[] sum;
    protected int[] countFromFile;


    public Basket(int[] prices, String[] products) {
        this.prices = prices;
        this.products = products;
        count = new int[products.length];
        sum = new int[products.length];
    }

    public void addToCart(int productNum, int amount) {

        int currentPrice = prices[productNum];
        int sumProducts = amount * currentPrice;
        count[productNum] += amount;
        sum[productNum] += sumProducts;

    }

    public void printCart() {

        System.out.println("Ваша корзина: ");
        for (int i = 0; i < products.length; i++) {
            if (count[i] != 0) {
                System.out.println(products[i] + " " + count[i] + " шт  " + prices[i] + " руб/шт " + sum[i] + " руб. в сумме");
            }
        }
        for (int s : sum) {
            sumProduct += s;
        }
        System.out.println("Итого: " + sumProduct);
    }

    public void saveTxt(File file) throws IOException {
        try (PrintWriter out = new PrintWriter(file)) {
            for (String p : products) {
                out.print(p + ",");
            }
            out.print("\n");
            for (int pr : prices) {
                out.print(pr + ";");
            }
            out.print("\n");
            for (int i : count) {
                out.print(i + " ");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveJson(File file) throws IOException {
        try (PrintWriter out = new PrintWriter(file)) {
            Gson gson = new Gson();
            String clientOperationJson = gson.toJson(this);
            out.println(clientOperationJson);
        }
    }

    public static Basket loadFromJsonFile(File file) throws IOException {

        try (Scanner scanner = new Scanner(file)) {
            Gson gson = new Gson();
            String json = scanner.nextLine();
            Basket basket = gson.fromJson(json, Basket.class);
            return basket;
        }
    }

        public static Basket loadFromTxtFile (File file) throws IOException {
            try (Scanner scanner = new Scanner(file)) {
                int[] priceFromFile = null;
                String[] items = null;
                int[] countFromFile = null;
                while (scanner.hasNextLine()) {
                    String input = scanner.nextLine();
                    if (input.contains(",")) {
                        items = input.split(",");
                    }
                    if (input.contains(";")) {
                        String[] pp = input.split(";");
                        priceFromFile = new int[items.length];
                        for (int i = 0; i < pp.length; i++) {
                            int pr = Integer.parseInt(pp[i]);
                            priceFromFile[i] = pr;
                        }
                    }
                    if (input.contains(" ")) {
                        countFromFile = Arrays.stream(input.split(" "))
                                .mapToInt(Integer::parseInt)
                                .toArray();
                    }
                }
                Basket basket = new Basket(priceFromFile, items);
                basket.count = countFromFile;
                return basket;
            }
        }
    }



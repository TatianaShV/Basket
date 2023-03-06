

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.ToIntFunction;

public class Basket {

    private static ToIntFunction<String> stringToIntFunction;
    protected String[] products;
    protected int[] prices;
    protected int sumProduct = 0;
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
        //Basket basket = new Basket(prices, products);
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

    public void saveTxt(File textFile) throws IOException {
        try (PrintWriter out = new PrintWriter(textFile)) {
            for (String p : getProducts()) {
                out.print(p + ",");
            }
            out.print("\n");
            for (int pr : getPrices()) {
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

    public static void loadFromTxtFile(File textFile) throws IOException {
        Basket basket = null;
        try (FileReader reader = new FileReader(textFile)) {
            Scanner scanner = new Scanner(reader);
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

            basket = new Basket(priceFromFile, items);
            for (int i = 0; i < countFromFile.length; i++) {
                if (countFromFile[i] != 0) {
                    basket.addToCart(i, countFromFile[i]);
                }
            }
            //}

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public void setProducts(String[] products) {
        this.products = products;
    }

    public void setPrices(int[] prices) {
        this.prices = prices;
    }

    public String[] getProducts() {
        return products;
    }

    public int[] getPrices() {
        return prices;
    }
}

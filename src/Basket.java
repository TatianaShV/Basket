import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Basket implements Serializable {
    private static final long serialVersionUID = 1L;
    protected static String[] products;
    protected static int[] prices;
    protected int sumProduct = 0;
    protected int[] count;
    protected int[] sum;
    protected static int[] countFromFile;


    public Basket(int[] prices, String[] products) {
        Basket.prices = prices;
        Basket.products = products;
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

    public void saveBin(File file) throws IOException {
        try (FileOutputStream out = new FileOutputStream(file);
             ObjectOutputStream obj = new ObjectOutputStream(out)) {
            Basket basket = new Basket(prices, products);
            obj.writeObject(basket);
            for (int i : count) {
                obj.writeInt(i);
                obj.flush();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveTxt(File textFile) throws IOException {
        try (PrintWriter out = new PrintWriter(textFile)) {
            for (int i : count) {
                out.print(i + " ");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Basket loadFromBinFile(File file) throws IOException {
        Basket basket = null;

        try (FileInputStream input = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(input)) {

            basket = (Basket) ois.readObject();

            for (int i = 0; i < countFromFile.length; i++) {
                countFromFile[i] = ois.readInt();
            }


        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        for (int i = 0; i < countFromFile.length; i++) {
            if (countFromFile[i] != 0) {
                basket.addToCart(i, countFromFile[i]);
            }
        }
        return basket;
    }


    public static Basket loadFromTxtFile(File textFile) throws IOException {
        Basket basket = new Basket(prices, products);
        try (FileReader reader = new FileReader(textFile)) {
            Scanner scanner = new Scanner(reader);
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();

                countFromFile = Arrays.stream(input.split(" "))
                        .mapToInt(Integer::parseInt)
                        .toArray();
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        for (int i = 0; i < countFromFile.length; i++) {
            if (countFromFile[i] != 0) {
                basket.addToCart(i, countFromFile[i]);
            }

        }
        return basket;
    }

    public void setProducts(String[] products) {
        Basket.products = products;
    }

    public void setPrices(int[] prices) {
        Basket.prices = prices;
    }

    public String[] getProducts() {
        return products;
    }

    public int[] getPrices() {
        return prices;
    }
}

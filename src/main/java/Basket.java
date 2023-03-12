

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
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

    /*  public void saveTxt(File textFile) throws IOException {
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
      }*/
   /* public void loadFromJsonFile(File textFile) throws IOException {
        Basket basket = null;*/
      /*  JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("basket.json"));
            JSONObject basketParsedJson = (JSONObject) obj;
            JSONArray productJson = (JSONArray) basketParsedJson.get("product");
            JSONArray priceJson = (JSONArray) basketParsedJson.get("price");
            JSONArray amountJson = (JSONArray) basketParsedJson.get("amount");
            String[] product = (String[]) productJson.stream()
                    .flatMap(Object::toString)
                    .toArray();
            int[] prices = new int[priceJson.size()];
            int[] amount = new int[amountJson.size()];
            for (Object o : priceJson) {

                for (int i = 0; i < prices.length; ++i) {
                    prices[i] = (int) o;
                }
                basket = new Basket(prices, product);

                for (Object ob : amountJson) {
                    for (int i = 0; i < amount.length; i++) {
                        amount[i] = (int) ob;
                    }
                }for (int i = 0; i < amount.length; i++) {
                    if (amount[i] != 0) {
                        basket.addToCart(i, amount[i]);
                    }
            }
        }}catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }*/
    /*public static void loadFromTxtFile(File textFile) throws IOException {
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
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }*/

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

    public int[] getCount() {
        return count;
    }

    public void setCount(int[] count) {
        this.count = count;
    }}


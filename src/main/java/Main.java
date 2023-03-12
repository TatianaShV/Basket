import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String[] items = {"1.Молоко", "2.Яблоки", "3.Хлеб"};
        int[] productsPrices = {100, 150, 90};
       // File file = new File("basket.txt");
        ClientLog clientLog = new ClientLog();
        Basket basket = new Basket(productsPrices, items);
        JSONObject clientOperationJson = new JSONObject();

        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("basket.json"));
            JSONObject basketParsedJson = (JSONObject) obj;
            JSONArray productJson = (JSONArray) basketParsedJson.get("product");
            JSONArray priceJson = (JSONArray) basketParsedJson.get("price");
            JSONArray amountJson = (JSONArray) basketParsedJson.get("amount");
           /* String[] product = (String[]) productJson.stream()
                    .flatMap(Object::toString)
                    .toArray();
            int[] prices = new int[priceJson.size()];*/
            int[] amount = new int[amountJson.size()];
            /*for (Object o : priceJson) {

                for (int i = 0; i < prices.length; ++i) {
                    prices[i] = (int) o;
                }
                basket = new Basket(prices, product);*/

                for (Object ob : amountJson) {
                    for (int i = 0; i < amount.length; i++) {
                        amount[i] = (int) ob;
                    }
                }for (int i = 0; i < amount.length; i++) {
                    if (amount[i] != 0) {
                        basket.addToCart(i, amount[i]);
                    }
                }
            }catch (IOException | ParseException e) {
            System.out.println(e);
        }
   // }
        while (true) {
            System.out.println("Список возможных товаров для покупки:");
            for (int i = 0; i < items.length; i++) {
                System.out.println(items[i] + " " + productsPrices[i] + " руб/шт");
            }
            System.out.println("Выберите номер товара и количество или введите end");
            String input = scanner.nextLine();
            if (input.equals("end")) {
                break;1
            }
            String[] parts = input.split(" ");
            try {
                if (parts.length != 2) {
                    System.out.println("Введите 2 числа: номер товара и его количество! Было введено: " + input);
                    continue;
                }
                int productNumber = Integer.parseInt(parts[0]) - 1;
                int productCount = Integer.parseInt(parts[1]);
                clientLog.log( Integer.parseInt(parts[0]), productCount);
                clientLog.exportAsCSV(new File("log.csv"));
                if (productNumber > items.length
                        || productNumber < 0
                        || productCount < 0) {
                    System.out.println("Данные числа не подходят на роль номера продукта или количества. Было введено: " + input);
                    continue;
                }
                basket.addToCart(productNumber, productCount);

            } catch (NumberFormatException e) {
                System.out.println("Введите два числа! Было введено: " + input);
                continue;
            }}

            clientOperationJson.put("product", basket.getProducts());
            clientOperationJson.put("price", basket.getPrices());
            clientOperationJson.put("amount", basket.getCount());
            try (FileWriter file = new FileWriter("basket.json")) {

                file.write(clientOperationJson.toJSONString());
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            //}
        }
        basket.printCart();
    }
}




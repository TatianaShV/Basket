import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        String[] products = {"1.Молоко", "2.Яблоки", "3.Хлеб"};
        int[] prices = {100, 150, 90};
        Basket basket = new Basket(prices,products);
        File file = new File("basket.bin");


        basket.loadFromBinFile(file);
        while (true) {
            System.out.println("Список возможных товаров для покупки:");
            for (int i = 0; i < products.length; i++) {
                System.out.println(products[i] + " " + prices[i] + " руб/шт");
            }
            System.out.println("Выберите номер товара и количество или введите end");
            String input = scanner.nextLine();
            if (input.equals("end")) {
                break;
            }
            String[] parts = input.split(" ");
            try {
                if (parts.length != 2) {
                    System.out.println("Введите 2 числа: номер товара и его количество! Было введено: " + input);
                    continue;
                }
                int productNumber = Integer.parseInt(parts[0]) - 1;
                int productCount = Integer.parseInt(parts[1]);
                if (productNumber > products.length
                        || productNumber < 0
                        || productCount < 0) {
                    System.out.println("Данные числа не подходят на роль номера продукта или количества. Было введено: " + input);
                    continue;
                }
                basket.addToCart(productNumber, productCount);

            } catch (NumberFormatException e) {
                System.out.println("Введите два числа! Было введено: " + input);
                continue;
            }

            basket.saveBin(file);
        }
        basket.printCart();
    }
}




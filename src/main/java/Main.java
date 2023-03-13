import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        Scanner scanner = new Scanner(System.in);
        String[] items = {"1.Молоко", "2.Яблоки", "3.Хлеб"};
        int[] productsPrices = {100, 150, 90};
        Basket basket = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse("config.xml");
        XPath xPath = XPathFactory.newInstance().newXPath();
        String loadFileName = xPath
                .compile("/config/load/fileName")
                .evaluate(doc);
        String loadFormat = xPath
                .compile("/config/load/format")
                .evaluate(doc);
        Boolean loadEnabled = Boolean.parseBoolean(xPath
                .compile("/config/load/enabled")
                .evaluate(doc));
        if (loadEnabled) {
            File loadFile = new File(loadFileName);
            switch (loadFormat) {
                case "json":
                    basket = Basket.loadFromJsonFile(loadFile);
                    break;
                case "txt":
                    basket = Basket.loadFromTxtFile(loadFile);
                    break;
            }else{
                basket = new Basket(productsPrices, items);
            }
        }

        File file = new File("basket.txt");
        ClientLog clientLog = new ClientLog();
        //Basket basket = new Basket(productsPrices, items);
        //  basket.loadFromTxtFile("basket.txt");

        while (true) {
            System.out.println("Список возможных товаров для покупки:");
            for (int i = 0; i < items.length; i++) {
                System.out.println(items[i] + " " + productsPrices[i] + " руб/шт");
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
                clientLog.log(Integer.parseInt(parts[0]), productCount);
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
            }
        }
        String saveFileName = xPath
                .compile("/config/save/fileName")
                .evaluate(doc);
        String saveFormat = xPath
                .compile("/config/save/format")
                .evaluate(doc);
        Boolean saveEnabled = Boolean.parseBoolean(xPath
                .compile("/config/save/enabled")
                .evaluate(doc));
        if (saveEnabled) {
            File saveFile = new File(saveFileName);
            switch (loadFormat) {
                case "json":
                    basket.saveJson(saveFile);
                    break;
                case "txt":
                    basket.saveTxt(saveFile);
                    break;
            }
        }
        String logFileName = xPath
                .compile("/config/log/fileName")
                .evaluate(doc);

        Boolean logEnabled = Boolean.parseBoolean(xPath
                .compile("/config/log/enabled")
                .evaluate(doc));
        if (logEnabled) {
            File logFile = new File(logFileName);
        }


        //basket.saveTxt(file);
        // basket.saveJson(new File("basket.json"));
        basket.printCart();
    }
}




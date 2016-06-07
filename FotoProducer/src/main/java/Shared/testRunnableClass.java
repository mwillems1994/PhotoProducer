/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author marcowillems
 */
public class testRunnableClass {

    public static void main(String[] args) throws SQLException {
        //Get all order from all producers, was for testing but ill leave it as an example..
        /*Producer producer = DbController.getProducerFromID(8);
         LinkedList<Order> orders = producer.getOrders();
         for(Order order : orders){
         LinkedList<ProductCopy> products = order.getProducts();
         for (ProductCopy product : products){
         System.out.println("OrderID: " + order.getOrderID() + ", ProductID: " + product.getProductID() + ", Productname: " + product.getName());
         }
         }
         */
        //showLanguageExample();
        String hashCodeString = "december";
        int pos = hashCodeString.hashCode() % 16;
        System.out.println(pos);
    }

    private static void showLanguageExample() {
        Locale dutchLocale = new Locale("nl", "NL");
        ResourceBundle bundle1 = ResourceBundle.getBundle("LanguageBundle", dutchLocale);
        displayValues(bundle1);

        Locale englishLocale = new Locale("en", "EN");
        ResourceBundle bundle2 = ResourceBundle.getBundle("LanguageBundle", englishLocale);
        displayValues(bundle2);
    }

    private static void displayValues(ResourceBundle bundle) {
        System.out.println("hello message:" + bundle.getString("screen1.greetings"));
        System.out.println("goodbye message:" + bundle.getString("screen2.farewell"));
        System.out.println("question message:" + bundle.getString("screen3.inquiry"));
        System.out.println();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ShoppingCart;

import Json.JsonToClass;
import LanguageSupport.LanguageHelper;
import Shared.ProductCopy;
import com.google.gson.Gson;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author marcowillems
 */
public class ShoppingCart {

    private static ObservableList<ShoppingCartItem> shoppingCartItems = FXCollections.observableList(new LinkedList<ShoppingCartItem>());

    public static void addItem(ShoppingCartItem sci) {
        shoppingCartItems.add(sci);
    }

    public static void addItem(ProductCopy pc, int amount) {
        shoppingCartItems.add(new ShoppingCartItem(pc, amount));
    }

    public static boolean removeItem(ShoppingCartItem sci) throws Exception {
        try {
            shoppingCartItems.remove(sci);
            return true;
        } catch (Exception ex) {
            throw new Exception(LanguageHelper.selectedLanguage().getString("ShoppingCart.error.removeItem") + ex.getMessage());
        }
    }

    public static boolean removeItemAtIndex(int index) throws Exception {
        try {
            shoppingCartItems.remove(index);
            return true;
        } catch (Exception ex) {
            throw new Exception(LanguageHelper.selectedLanguage().getString("ShoppingCart.error.removeItem") + ex.getMessage());
        }
    }

    public static ObservableList<ShoppingCartItem> getShoppingCart() {
        return shoppingCartItems;
    }

    public static double sumTotalPrice() throws Exception {
        try {
            double totalPrice = 0;
            for (ShoppingCartItem sci : shoppingCartItems) {
                totalPrice += sci.getPrice();
            }
            return totalPrice;
        } catch (Exception ex) {
            throw new Exception(LanguageHelper.selectedLanguage().getString("ShoppingCart.error.sumItems") + ex.getMessage());
        }
    }

    public static boolean save() throws Exception {
        String foo = makeJsonFile();
        if (!makeJsonFile().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean load() throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get("ShoppingCart.txt"));
        String json = new String(encoded);
        ShoppingCartItem[] items = JsonToClass.JsonToShoppingCart(json);
        shoppingCartItems.clear();
        for (ShoppingCartItem sci : items) {
            shoppingCartItems.add(sci);
        }
        return true;
    }

    private static String makeJsonFile() throws Exception {
        BufferedWriter writer = null;
        try {
            Files.delete(Paths.get("ShoppingCart.txt"));
        } catch (NoSuchFileException nfe) {

        }
        try {
            File saveFile = new File("ShoppingCart.txt");
            LinkedList<ShoppingCartItem> items = new LinkedList<ShoppingCartItem>();
            for (ShoppingCartItem sc : shoppingCartItems) {
                items.add(sc);
            }

            // This will output the full path where the file will be written to...
            writer = new BufferedWriter(new FileWriter(saveFile, true));
            String json = new Gson().toJson(items);
            writer.write(json);

            return saveFile.getCanonicalPath();
        } catch (Exception ex) {
            throw new Exception(LanguageHelper.selectedLanguage().getString("ShoppingCart.error.saveCart") + ex.getMessage());
        } finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();
            } catch (Exception e) {
            }
        }
    }
}

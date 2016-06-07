/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Json;

import Shared.*;
import Shared.Producer;
import ShoppingCart.ShoppingCart;
import ShoppingCart.ShoppingCartItem;
import com.google.gson.Gson;
import javafx.collections.ObservableList;

/**
 *
 * @author Ruud
 */
public class JsonToClass {
    private static final Gson gson = new Gson();
    
    public static User JsonToUser(String jsonString) {
        return JsonToClass.gson.fromJson(jsonString, User.class);
    }

    public static Customer JsonToCustomer(String jsonString) {
        return JsonToClass.gson.fromJson(jsonString, Customer.class);
    }

    public static Filter JsonToFilter(String jsonString) {
        return JsonToClass.gson.fromJson(jsonString, Filter.class);
    }

    public static Photo JsonToPhoto(String jsonString) {
        return JsonToClass.gson.fromJson(jsonString, Photo.class);
    }

    public static Photographer JsonToPhotographer(String jsonString) {
        return JsonToClass.gson.fromJson(jsonString, Photographer.class);
    }

    public static Photographer[] JsonToPhotographerAll(String jsonString) {
        return JsonToClass.gson.fromJson(jsonString, Photographer[].class);
    }

    public static Producer JsonToProducer(String jsonString) {
        return JsonToClass.gson.fromJson(jsonString, Producer.class);
    }
    
    public static Producer[] JsonToProducerAll(String jsonString) {
        return JsonToClass.gson.fromJson(jsonString, Producer[].class);
    }

    public static Product JsonToProduct(String jsonString) {
        return JsonToClass.gson.fromJson(jsonString, Product.class);
    }

    public static Product[] JsonToProducts(String jsonString) {
        return JsonToClass.gson.fromJson(jsonString, Product[].class);
    }

    public static ProductPhotographer[] JsonToProductsPhotograper(String jsonString) {
        return JsonToClass.gson.fromJson(jsonString, ProductPhotographer[].class);
    }

    public static ProductCopy JsonToProductCopy(String jsonString) {
        return JsonToClass.gson.fromJson(jsonString, ProductCopy.class);
    }

    public static Order JsonToOrder(String jsonString) {
        return JsonToClass.gson.fromJson(jsonString, Order.class);
    }

    public static Order[] JsonToOrderList(String jsonString) {
        return JsonToClass.gson.fromJson(jsonString, Order[].class);
    }

    public static Customer[] JsonToCustomerList(String jsoncustomer) {
        return JsonToClass.gson.fromJson(jsoncustomer, Customer[].class);
    }

    public static Photo[] JsonToPhotos(String jsonphotos) {
        return JsonToClass.gson.fromJson(jsonphotos, Photo[].class);
    }
    
    public static Album[] JsonToAlbums(String jsonAlbums)
    {
        return JsonToClass.gson.fromJson(jsonAlbums, Album[].class);
    }
    
    public static ShoppingCartItem[] JsonToShoppingCart(String jsonShoppingCart){
        return JsonToClass.gson.fromJson(jsonShoppingCart, ShoppingCartItem[].class);
    }
}

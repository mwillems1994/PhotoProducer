/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import java.util.LinkedList;
import javafx.collections.ObservableList;

/**
 *
 * @author marcowillems
 */
public class ProductCopy extends Product{
    private Photo photo;
    private Filter filter;

    public ProductCopy(int productID, Producer producer, String name, String description, double price, Photo photo, Filter filter) {
        super(productID, producer, name, description, price);
        this.photo = photo;
        this.filter = filter;
    }
    
    public int getProductID(){
        return super.getProductID();
    }
    public Producer getProducer(){
        return super.getProducer();
    }
    public String getName(){
        return super.getName();
    }
    public String getDescription(){
        return super.getDescription();
    }
    public double getPrice(){
        return super.getPrice();
    }
    public Photo getPhoto(){
        return this.photo;
    }
    public Filter getFilter(){
        return this.filter;
    }
    public ProductCopy getProductCopy(){
        return this;
    }
    public static LinkedList<ProductCopy> shoppingCartToProducts(ObservableList<ShoppingCart.ShoppingCartItem> shoppingCartItems){
        LinkedList<ProductCopy> products = new LinkedList();
        for(ShoppingCart.ShoppingCartItem sci : shoppingCartItems){
            products.add(sci.getProductCopy());
        }
        return products;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import ShoppingCart.ShoppingCartItem;
import Storage.DbController;
import java.sql.SQLException;
import java.util.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author marcowillems
 */
public class Order {

    private int orderID;
    private int producerID;
    private int customerID;
    private int PhotograpgerID;
    private Boolean Payed;
    private ObservableList<ShoppingCart.ShoppingCartItem> shoppingCart;

    public Order(int orderID, int customerID, int PhotograpgerID, Boolean Payed, ObservableList<ShoppingCart.ShoppingCartItem> shoppingCart) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.PhotograpgerID = PhotograpgerID;
        this.Payed = Payed;
        this.shoppingCart = shoppingCart;
    }
    public Order(int orderID, int producerID, int customerID) {
        this.orderID = orderID;
        this.producerID = producerID;
        this.customerID = customerID;
    }
    
    //hack constructor, dont use this... :)
    public Order(){
        this.shoppingCart = FXCollections.observableList(new LinkedList<ShoppingCart.ShoppingCartItem>());
    }

    public int getProducerID() {
        return producerID;
    }


    public int getCustomerID() {
        return customerID;
    }

    public ObservableList<ShoppingCart.ShoppingCartItem> getShoppingCart() {
        return this.shoppingCart;
    }

    public int getOrderID() {
        return this.orderID;
    }

    public LinkedList<Product> getProducts() throws SQLException {
        return DbController.getProductsForOrder(this.orderID);
    }
    
    public int getPhotograpgerID() {
        return PhotograpgerID;
    }

    public Boolean getPayed() {
        return Payed;
    }
    
    public void setShoppingCart(ObservableList<ShoppingCart.ShoppingCartItem> shoppingCart){
        this.shoppingCart = shoppingCart;
    }
}

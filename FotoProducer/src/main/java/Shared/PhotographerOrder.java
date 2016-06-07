/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import Json.JsonToClass;
import SocketClient.SocketManager;
import java.util.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Luc
 */
public class PhotographerOrder {

    private ObservableList<PhotographerOrder> photographerOrders = FXCollections.observableArrayList();
    private String orderID;
    private String photographerID;
    private String customerID;
    private String CustomerName;
    private LinkedList<ProductCopy> Products;
    private String nrproducts;
    private String payed;

    public String getTotalPrice() {
        return totalPrice;
    }
    private String totalPrice;

    public PhotographerOrder(String orderID, String photographerID, String customerID, String CustomerName, LinkedList<ProductCopy> Products, String nrproducts, String payed, String tp) {
        this.orderID = orderID;
        this.photographerID = photographerID;
        this.customerID = customerID;
        this.CustomerName = CustomerName;
        this.Products = Products;
        this.nrproducts = nrproducts;
        this.payed = payed;
        this.totalPrice = tp;
    }

    public PhotographerOrder() {
    }

    public ObservableList<PhotographerOrder> getPhotographerOrders() {
        return photographerOrders;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getPhotographerID() {
        return photographerID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public LinkedList<ProductCopy> getProducts() {
        return Products;
    }

    public String getNrproducts() {
        return nrproducts;
    }

    public String getPayed() {
        return payed;
    }

    public ObservableList<PhotographerOrder> getAllPhotographerOrdes(int photgrapherid) {
        String tprice = "";
        String p = "";
        JsonToClass jtc = new JsonToClass();
        String jsoncustomer = SocketManager.sendCommand("getCustomers");
        Customer[] customers = jtc.JsonToCustomerList(jsoncustomer);
        String json = SocketManager.sendCommand("getOrdersFromPhotographer|" + photgrapherid);
        Order[] orderlist = jtc.JsonToOrderList(json);
        for (Order o : orderlist) {
            Double totalp = 0.0;
            String cname = "";
            for (Customer c : customers) {
                if (o.getCustomerID() == c.getId()) {
                    cname = c.getName();
                }
            }
            if (o.getPayed()) {
                p = "ja";
            } else {
                p = "nee";
            }
            for (ShoppingCart.ShoppingCartItem sci : o.getShoppingCart()) {
                Double productp = sci.getProductCopy().getPrice();
                totalp = totalp + productp;
            }
            tprice = String.valueOf(totalp);
            PhotographerOrder po = new PhotographerOrder(
                    String.valueOf(o.getOrderID()),
                    String.valueOf(o.getPhotograpgerID()),
                    String.valueOf(o.getCustomerID()),
                    cname,
                    ProductCopy.shoppingCartToProducts(o.getShoppingCart()),
                    String.valueOf(ProductCopy.shoppingCartToProducts(o.getShoppingCart()).size()),
                    p,
                    tprice);
            photographerOrders.add(po);
        }
        return photographerOrders;
    }
    public ObservableList<ProductCopy> productsOfOrder(int oid) {
        ObservableList<ProductCopy> op = FXCollections.observableArrayList();
        for (PhotographerOrder o : photographerOrders) {
            if (Integer.parseInt(o.getOrderID()) == oid) {
                for (ProductCopy pco : o.Products) {
                    op.add(pco);
                }
            }
        }
        return op;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import Json.JsonToClass;
import SocketClient.SocketManager;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Luc
 */
public class OrderShow {
    private ObservableList<OrderShow> ordershows = FXCollections.observableArrayList();
    private String orderID;
    private String producerID;
    private String customerID;
    private String CustomerName;
    private LinkedList<ProductCopy> Products;
    private String nrproducts;

    public OrderShow(String orderID, String producerID, String customerID, String CustomerName, LinkedList<ProductCopy> Products, String nrproducts) {
        this.orderID = orderID;
        this.producerID = producerID;
        this.customerID = customerID;
        this.CustomerName = CustomerName;
        this.Products = Products;
        this.nrproducts = nrproducts;
    }

    public OrderShow() {
    }

    

    public String getOrderID() {
        return orderID;
    }

    public String getProducerID() {
        return producerID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public String getNrproducts() {
        return nrproducts;
    }



    
    public ObservableList<OrderShow> getAllOrderShow(int producerid)
    {
        JsonToClass jtc = new JsonToClass();
        String jsoncustomer = SocketManager.sendCommand("getCustomers");
        Customer[] customers = jtc.JsonToCustomerList(jsoncustomer);
        String json = SocketManager.sendCommand("getOrdersFromProducer|"+producerid);
        Order[] orderlist = jtc.JsonToOrderList(json);
        for (Order o : orderlist) {
            String cname = "";
            for (Customer c : customers) {
                if(o.getCustomerID()==c.getId())
                {
                    cname = c.getName();
                }
            }
            /*OrderShow os = new OrderShow(String.valueOf(o.getOrderID()),String.valueOf(o.getProducerID()),String.valueOf(o.getCustomerID()),cname,o.getProducts2(),String.valueOf(o.getProducts2().size()));
            ordershows.add(os);*/
        }
        return ordershows;
        
    }
    public ObservableList<ProductCopy> productsOfOrder(int oid){
        ObservableList<ProductCopy> op = FXCollections.observableArrayList();
        for (OrderShow o : ordershows) {
            if(Integer.parseInt(o.getOrderID())==oid)
            {
                for (ProductCopy pco : o.Products) {
                    op.add(pco);
                }
            }
        }
        return op;
    }

    public LinkedList getProducts() {
        return Products;
    }
    
}

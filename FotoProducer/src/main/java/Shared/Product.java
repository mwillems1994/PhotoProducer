/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

/**
 *
 * @author marcowillems
 */
public class Product {
    private int productID;
    private Producer producer;
    private String name;
    private String description;
    private double price;

    public Product(int productID, Producer producer, String name, String description, double price){
        this.productID = productID;
        this.producer = producer;
        this.name = name;
        this.description = description;
        this.price = price;
    }
    
    public int getProductID(){
        return this.productID;
    }
    public Producer getProducer(){
        return this.producer;
    }
    public String getName(){
        return this.name;
    }
    public String getDescription(){
        return this.description;
    }
    public double getPrice(){
        return this.price;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Name: " + name + " | Description: " + description + " | Price: " + price ;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

/**
 *
 * @author Thijs
 */
public class ProductPhotographer extends Product {

    private double profitPrice;
    private int photograperProductId;
    private int photographerId;

    public ProductPhotographer(int productID, Producer producer, String name, String description, double price, double profitPrice, int photographerId, int photographerProductId) {
        super(productID, producer, name, description, price);
        this.photograperProductId = photographerProductId;
        this.photographerId = photographerId;
        this.profitPrice = profitPrice;
    }

    public int getPhotographerId() {
        return photographerId;
    }

    public double getProfitPrice() {
        return profitPrice;
    }

    public void setProfitPrice(double profitPrice) {
        this.profitPrice = profitPrice;
    }

    public int getPhotograperProductId() {
        return photograperProductId;
    }
    
    @Override
    public String toString() {
        return super.toString() + " | ProfitPrice: " + profitPrice;
    }
}

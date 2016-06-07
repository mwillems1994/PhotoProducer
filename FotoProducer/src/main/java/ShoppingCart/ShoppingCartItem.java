/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ShoppingCart;

import Shared.Filter;
import Shared.Photo;
import Shared.Producer;
import Shared.ProductCopy;

/**
 *
 * @author marcowillems
 */
public class ShoppingCartItem extends Shared.ProductCopy{

    private int amount;
    public ShoppingCartItem(int productID, Producer producer, String name, String description, double price, Photo photo, Filter filter, int amount) {
        super(productID, producer, name, description, price, photo, filter);
        this.amount = amount;
    }
    public ShoppingCartItem(ProductCopy productCopy, int amount){
        super(productCopy.getProductID(), productCopy.getProducer(), productCopy.getName(), productCopy.getDescription(), productCopy.getPrice(), productCopy.getPhoto(), productCopy.getFilter());
        this.amount = amount;
    }
    
    public int getProductID(){
        return super.getProductID();
    }
    public Producer getProducer(){
        return super.getProducer();
    }
    public String getProductName(){
        return super.getName();
    }
    public String getProductDescription(){
        return this.getDescription();
    }
    public double getPrice(){
        return super.getPrice();
    }
    public Photo getPhoto(){
        return super.getPhoto();
    }
    public Filter getFilter(){
        return super.getFilter();
    }
    public int getAmount(){
        return this.amount;
    }

    public ProductCopy getProductCopy(){
        return super.getProductCopy();
    }
}

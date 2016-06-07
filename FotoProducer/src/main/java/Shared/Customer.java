/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import java.util.LinkedList;

/**
 *
 * @author marcowillems
 */

/**
 * Customer class
 * @author marcowillems
 */
public class Customer extends User{
    private LinkedList<ProductCopy> shoppingCart;
    public Customer(int id, String name) {
        super(id, name);
        this.shoppingCart = new LinkedList<ProductCopy>();
    }  
    /**
     * get id from customer
     * @return customer id
     */
    public int getId(){
        return super.getUserID();
    }
    
    /**
     * get name from customer
     * @return customer name
     */
    public String getName(){
        return super.getName();
    }
    
    /**
     * get shopping cart from customer
     * @return list of product copies
     */
    public LinkedList<ProductCopy> getShoppingCart(){
        return this.shoppingCart;
    }
    
    /**
     * add product to customers shopping cart
     * @param product
     * @return true if it works
     */
    public boolean addToShoppingCart(ProductCopy product) throws Exception{
        try {
            this.shoppingCart.add(product);
            return true;
        } catch(Exception ex) {
            throw new Exception("Error adding productcopy to shoppingcart, " + ex.getMessage());
        }
    }
    
    /**
     * remove productcopy from shopping cart
     * @param productCopy
     * @return true if product was found and successfully removed
     */
    public boolean removeFromShopping(ProductCopy productCopy) throws Exception{
        //this should work, never tried it though. we have to look for a alternative for LinkedList, as it does not indices and stuff.
        try {
            for(ProductCopy pc : this.shoppingCart){
               if (pc.hashCode() == productCopy.hashCode()){
                   this.shoppingCart.remove(productCopy);
                   return true;
               }
            }
            return false;
        } catch(Exception ex){
            throw new Exception("Error removing product from shopping cart, " + ex.getMessage());
        }
    }
    
    public String toString(){
        return this.getName();
    }
}

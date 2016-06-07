/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import Storage.DbController;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author marcowillems
 */
public class Producer extends User{
    private String companyName;
    private LinkedList<Photographer> photographers;
    private LinkedList<Order> orders;

    public Producer(int id, String name, String companyName) throws SQLException {
        super(id, name);
        this.companyName = companyName;
    }
    
    public int getID(){
        return super.getUserID();
    }
    public String getName(){
        return super.getName();
    }
    public String getCompanyName(){
        return this.companyName;
    }
    public String getPhotographers() throws SQLException{
        return DbController.getPhotographersFromProducer(super.getUserID());
    }
    public String getOrders() throws SQLException{
        return DbController.getOrdersFromProducer(super.getUserID());
    }
    
    @Override
    public String toString(){
        return companyName;
    }
    
}

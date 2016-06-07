/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import java.util.ArrayList;

/**
 *
 * @author hugoL
 */
public class Album {
    private ArrayList<Photo> photos;
    private Customer customer;
    private String albumnaam;
    private int id;
    
    public Album(String albumnaam, int id){
        photos = new ArrayList<Photo>();
        this.albumnaam = albumnaam;
        this.id = id;
    }
    
    public ArrayList<Photo> getPhotos(){
        return photos;
    }
    
    public Customer getCustomer(){
        return customer;
    }
    
    public String getNaam(){
        return albumnaam;
    }
    
    public int getID(){
        return id;
    }
    
    public void setPhotos(ArrayList<Photo> givenPhotos){
        photos = givenPhotos;
    }
    
    public void setCustomer(Customer givenCustomer){
        customer = givenCustomer;
    }
    
    public String toString()
    {
        return albumnaam;
    }
}

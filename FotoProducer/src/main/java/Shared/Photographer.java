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
/**
 * Photographer class
 * @author marcowillems
 */
public class Photographer extends User{
    public Photographer(int id, String name) {
        super(id, name);
    }
    
    /**
     * get id from photographer
     * @return 
     */
    public int getID(){
        return super.getUserID();
    }
    /**
     * get name from photographer
     * @return 
     */
    public String getName(){
        return super.getName();
    }
    
    @Override
    public String toString(){
        return getName();
    }
}

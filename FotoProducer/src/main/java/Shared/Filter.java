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
public class Filter {
    
    private int id;
    private String name;
    
    public Filter(int id, String name){
        this.id = id;
        this.name = name;
    }
    
    public int getId(){
        return this.id;
    }
    
    public String getName(){
        return this.name;
    }
}

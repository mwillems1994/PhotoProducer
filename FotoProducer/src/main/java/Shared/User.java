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
 * Super class of user
 * @author marcowillems
 */
public class User {
    private int userID;
    private String name;
    public User(int id, String name){
        this.userID = id;
        this.name = name;
    }
    
    /**
     * get id from generic user
     * @return user id
     */
    public int getUserID(){
        return this.userID;
    }
    
    /**
     * get name from generic user
     * @return 
     */
    public String getName(){
        return this.name;
    }
}

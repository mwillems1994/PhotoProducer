/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import Json.JsonToClass;
import SocketClient.SocketManager;
import java.sql.SQLException;

/**
 *
 * @author Ruud
 */
public class Login {
    /**
     * returns the current logged in user
     */
    public static Object loggedInUser;
    public static String selectedPath;
    
    /**
     * login function.
     * @param username
     * @param password
     * @param userType
     * @return returns true if login was successful and false when it's not
     * @throws Exception 
     */
    public static boolean login(String username, String password, String userType) throws Exception {
        JsonToClass jtc = new JsonToClass();
        String userJson = SocketManager.sendCommand("login|" + username + "|" + password + "|" + userType);
        if(!userJson.equals("")){
            loggedInUser = Login.getUsertype(userJson, userType);
            return true;
        }
        return false;
    }
    
    public static boolean login(String clientCode){
        int customerID;
        try{
            customerID = Integer.parseInt(clientCode.split("_")[0]);
        } catch(Exception ex) {
            throw ex;
        }
        String customersJson = SocketManager.sendCommand("getCustomers");
        Customer[] customers = JsonToClass.JsonToCustomerList(customersJson);
        for(Customer customer : customers){
            if(customer.getId() == customerID){
                loggedInUser = customer;
                return true;
            }
        }
        
        return false;
    }
    /**
     * function the determine usertype
     * @param userJson json version of user(from socket server)
     * @param userType e.g. : Producer, Photographer and Customer
     * @return correct user object, e.g. : Producer, Photographer and Customer. 
     * @throws Exception if usertype is unknown
     */
    private static Object getUsertype(String userJson, String userType) throws Exception{
        Object returnValue = "";
        switch(userType){
            case "Producer": returnValue = JsonToClass.JsonToProducer(userJson);
                break;
            case "Photographer": returnValue = JsonToClass.JsonToPhotographer(userJson);
                break;
            case "Customer": returnValue = JsonToClass.JsonToCustomer(userJson);
                break;   
            default: throw new Exception("userType: " + userType + ", unknown");
        }
        return returnValue;
    }
}

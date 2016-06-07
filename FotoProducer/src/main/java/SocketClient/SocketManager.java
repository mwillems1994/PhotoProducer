/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SocketClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marcowillems
 */
public class SocketManager {
    private static Socket socket = getSocket("192.168.13.16", 80);
    private static DataOutputStream toServer = getToServer();
    private static BufferedReader fromServer = getFromServer();

    
    private static Socket getSocket(String ipAddress, int port){
        try {
            return new Socket(ipAddress, port);
        } catch (IOException ex) {
            System.out.println("Something went reading response from the server, " + ex.getMessage());
        }
        return null;
    }
    private static DataOutputStream getToServer(){
        try {
            return new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            System.out.println("Something went wrong sending command to the server, " + ex.getMessage());
        }
        return null;
    }
    private static BufferedReader getFromServer(){
        try {
            return new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException ex) {
            System.out.println("Something went wrong reading command from the server, " + ex.getMessage());
        }
        return null;
    }
    
    /**
     * Funtion to send a command to the socket server
     * @param commandString
     * @return return probably the json from the server, returns null if something went wrong
     */
    public static String sendCommand(String commandString){
        try {
        toServer.writeBytes(commandString + "\n");
        return fromServer.readLine();
        } catch (UnknownHostException e) {
            System.out.println("Something went wrong connecting to the server, " + e.getMessage());
        } catch (IOException ex) {
            System.out.println("Something went reading response from the server, " + ex.getMessage());
        }
        return null;
    }
}

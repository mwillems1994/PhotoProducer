/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SocketClient;

/**
 *
 * @author marcowillems
 */
import java.io.*;
import java.net.*;

public class SocketClient {    
    public static void main(String argv[]) throws Exception {
        System.out.println("Server says:" + SocketManager.sendCommand("getProducerFromID-6"));
    }
}

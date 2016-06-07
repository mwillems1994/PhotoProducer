/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ruud
 */
public class ProducerTest {
    
    int userID = 1;
    String name = "piet";
    String companyName = "softraar";
    LinkedList<Photographer> photographers = null;
    LinkedList<Order> orders = null;
    Producer producer;
    
    
    public ProducerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        try {
            producer = new Producer(userID, name, companyName);
        } catch (SQLException ex) {
            Logger.getLogger(ProducerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getID method, of class Producer.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        Producer instance = producer;
        int expResult = userID;
        int result = instance.getID();
        assertEquals(expResult, result);
    }

    /**
     * Test of getName method, of class Producer.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Producer instance = producer;
        String expResult = name;
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCompanyName method, of class Producer.
     */
    @Test
    public void testGetCompanyName() {
        System.out.println("getCompanyName");
        Producer instance = producer;
        String expResult = companyName;
        String result = instance.getCompanyName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPhotographers method, of class Producer.
     */
    /*@Test
    public void testGetPhotographers() throws Exception {
        System.out.println("getPhotographers");
        Producer instance = producer;
        LinkedList<Photographer> expResult = null;
        LinkedList<Photographer> result = instance.getPhotographers();
        assertEquals(expResult, result);
    }
*/
    /**
     * Test of getOrders method, of class Producer.
     *//*
    @Test
    public void testGetOrders() throws Exception {
        System.out.println("getOrders");
        Producer instance = producer;
        LinkedList<Order> expResult = null;
        LinkedList<Order> result = instance.getOrders();
        assertEquals(expResult, result);
    }*/
    
}

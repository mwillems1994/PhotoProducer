/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Storage;

import Shared.Customer;
import Shared.Order;
import Shared.Photographer;
import Shared.Producer;
import Shared.ProductCopy;
import Shared.User;
import java.util.LinkedList;
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
public class DbControllerTest {
    
    public DbControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getAllUsers method, of class DbController.
     */
    /*@Test
    public void testGetAllUsers() throws Exception {
        System.out.println("getAllUsers");
        LinkedList<User> expResult = null;
        LinkedList<User> result = DbController.getAllUsers();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllCustomers method, of class DbController.
     */
   /* @Test
    public void testGetAllCustomers() throws Exception {
        System.out.println("getAllCustomers");
        LinkedList<Customer> expResult = null;
        LinkedList<Customer> result = DbController.getAllCustomers();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPhotographersFromProducer method, of class DbController.
     */
  /*  @Test
    public void testGetPhotographersFromProducer() throws Exception {
        System.out.println("getPhotographersFromProducer");
        int ProducerID = 0;
        LinkedList<Photographer> expResult = null;
        LinkedList<Photographer> result = DbController.getPhotographersFromProducer(ProducerID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProducerFromID method, of class DbController.
     */
  /*  @Test
    public void testGetProducerFromID() throws Exception {
        System.out.println("getProducerFromID");
        int ProducerID = 0;
        Producer expResult = null;
        Producer result = DbController.getProducerFromID(ProducerID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOrdersFromProducer method, of class DbController.
     */
   /* @Test
    public void testGetOrdersFromProducer() throws Exception {
        System.out.println("getOrdersFromProducer");
        int ProducerID = 0;
        LinkedList<Order> expResult = null;
        LinkedList<Order> result = DbController.getOrdersFromProducer(ProducerID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProductsForOrder method, of class DbController.
     */
   /* @Test
    public void testGetProductsForOrder() throws Exception {
        System.out.println("getProductsForOrder");
        int OrderID = 0;
        LinkedList<ProductCopy> expResult = null;
        LinkedList<ProductCopy> result = DbController.getProductsForOrder(OrderID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/
    
}

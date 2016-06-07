/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

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
public class CustomerTest {

    public CustomerTest() {
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
     * Test of getId method, of class Customer.
     */
    @Test
    public void testGetId() {
        Customer instance = new Customer(1, "test");
        int expResult = 1;
        int result = instance.getId();
        assertEquals("ID gotten is not the same as expected", expResult, result);
    }

    /**
     * Test of getName method, of class Customer.
     */
    @Test
    public void testGetName() {
        Customer instance = new Customer(1, "test");
        String expResult = "test";
        String result = instance.getName();
        assertEquals("Name gotten is not the same as expected", expResult, result);
    }

    /**
     * Test of getShoppingCart method, of class Customer.
     */
    @Test
    public void testGetShoppingCart() throws Exception {
        Customer instance = new Customer(1, "testCustomer");
        instance.addToShoppingCart(new ProductCopy(1, null, "testName", "testDescription", 1.0, null, null));
        LinkedList<ProductCopy> expResult = new LinkedList<ProductCopy>();
        expResult.add(new ProductCopy(1, null, "testName", "testDescription", 1.0, null, null));
        LinkedList<ProductCopy> result = instance.getShoppingCart();
        ProductCopy pc = expResult.getFirst();
        ProductCopy pc2 = result.getFirst();
        assertEquals("Ids are not the same", pc.getProductID(), pc2.getProductID());
        assertEquals("Producers are not the same", pc.getProducer(), pc2.getProducer());
    }

    /**
     * Test of addToShoppingCart method, of class Customer.
     */
    @Test
    public void testAddToShoppingCart() throws Exception {
        ProductCopy product = new ProductCopy(1, null, "testName", "testDescription", 1.0, null, null);
        Customer instance = new Customer(1, "test");
        boolean expResult = true;
        boolean result = instance.addToShoppingCart(product);
        assertEquals("Failed to add product to cart", expResult, result);
        expResult = true;
        result = instance.addToShoppingCart(product);
        assertEquals("Added excisting item to cart went wrong", expResult, result);
    }

    /**
     * Test of removeFromShopping method, of class Customer.
     */
    @Test
    public void testRemoveFromShopping() throws Exception {
        ProductCopy productCopy = new ProductCopy(1, null, "testName", "testDescription", 1.0, null, null);
        Customer instance = new Customer(1, "test");
        boolean expResult = false;
        boolean result = instance.removeFromShopping(productCopy);
        assertEquals("Removing nonExisting item returned true", expResult, result);
        instance.addToShoppingCart(productCopy);
        expResult = true;
        result = instance.removeFromShopping(productCopy);
        assertEquals("Removing item went wrong", expResult, result);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

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
public class ProductTest {
    
    //fields for product
    int productID = 1;
    String name = "pet";
    String description = "voor op je hoofd";
    double price = 1.50;
    Producer producer = null;
    Product product;
    
    public ProductTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        product = new Product(productID, producer, name, description, price);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getProductID method, of class Product.
     */
    @Test
    public void testGetProductID() {
        System.out.println("getProductID");
        Product instance = product;
        int expResult = productID;
        int result = instance.getProductID();
        assertEquals(expResult, result);
    }

    /**
     * Test of getProducer method, of class Product.
     */
    @Test
    public void testGetProducer() {
        System.out.println("getProducer");
        Product instance = product;
        Producer expResult = producer;
        Producer result = instance.getProducer();
        assertEquals(expResult, result);
    }

    /**
     * Test of getName method, of class Product.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Product instance = product;
        String expResult = name;
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDescription method, of class Product.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        Product instance = product;
        String expResult = description;
        String result = instance.getDescription();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPrice method, of class Product.
     */
    @Test
    public void testGetPrice() {
        System.out.println("getPrice");
        Product instance = product;
        double expResult = price;
        double result = instance.getPrice();
        assertEquals(expResult, result, 0.00);
    }

    /**
     * Test of setDescription method, of class Product.
     */
    @Test
    public void testSetDescription() {
        System.out.println("setDescription");
        description = "je hoofd";
        Product instance = product;
        instance.setDescription(description);
        String expResult = description;
        String result = instance.getDescription();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPrice method, of class Product.
     */
    @Test
    public void testSetPrice() {
        System.out.println("setPrice");
        price = 5.99;
        Product instance = product;
        instance.setPrice(price);
        double expResult = price;
        double result = instance.getPrice();
        assertEquals(expResult, result, 0.00);
    }
    
}

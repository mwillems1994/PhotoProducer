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
public class ProductCopyTest {
    int productID = 1;
    String name = "pet";
    String description = "voor op je hoofd";
    double price = 1.50;
    Producer producer = null;
    ProductCopy productCopy;
    Photo photo = null;
    Filter filter = null;
    
    public ProductCopyTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        productCopy = new ProductCopy(productID, producer, name, description, price, photo, filter);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getProductID method, of class ProductCopy.
     */
    @Test
    public void testGetProductID() {
        System.out.println("getProductID");
        ProductCopy instance = productCopy;
        int expResult = productID;
        int result = instance.getProductID();
        assertEquals(expResult, result);
    }

    /**
     * Test of getProducer method, of class ProductCopy.
     */
    @Test
    public void testGetProducer() {
        System.out.println("getProducer");
        ProductCopy instance = productCopy;
        Producer expResult = producer;
        Producer result = instance.getProducer();
        assertEquals(expResult, result);
    }

    /**
     * Test of getName method, of class ProductCopy.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        ProductCopy instance = productCopy;
        String expResult = name;
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDescription method, of class ProductCopy.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        ProductCopy instance = productCopy;
        String expResult = description;
        String result = instance.getDescription();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPrice method, of class ProductCopy.
     */
    @Test
    public void testGetPrice() {
        System.out.println("getPrice");
        ProductCopy instance = productCopy;
        double expResult = price;
        double result = instance.getPrice();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getPhoto method, of class ProductCopy.
     */
    @Test
    public void testGetPhoto() {
        System.out.println("getPhoto");
        ProductCopy instance = productCopy;
        Photo expResult = photo;
        Photo result = instance.getPhoto();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFilter method, of class ProductCopy.
     */
    @Test
    public void testGetFilter() {
        System.out.println("getFilter");
        ProductCopy instance = productCopy;
        Filter expResult = filter;
        Filter result = instance.getFilter();
        assertEquals(expResult, result);
    }
    
}

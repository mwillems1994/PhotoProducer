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
public class ProductPhotographerTest {
    
    double profitPrice = 1.50;
    int photograperProductId = 1;
    int photographerId = 1;
    ProductPhotographer productPhotographer;
    
    int productID = 1;
    String name = "pet";
    String description = "voor op je hoofd";
    double price = 1.50;
    Producer producer = null;
    
    public ProductPhotographerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        productPhotographer = new ProductPhotographer(productID, producer, name, description, price, profitPrice, photographerId, photograperProductId);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getPhotographerId method, of class ProductPhotographer.
     */
    @Test
    public void testGetPhotographerId() {
        System.out.println("getPhotographerId");
        ProductPhotographer instance = productPhotographer;
        int expResult = 1;
        int result = instance.getPhotographerId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getProfitPrice method, of class ProductPhotographer.
     */
    @Test
    public void testGetProfitPrice() {
        System.out.println("getProfitPrice");
        ProductPhotographer instance = productPhotographer;
        double expResult = 1.50;
        double result = instance.getProfitPrice();
        assertEquals(expResult, result, 0.00);
    }

    /**
     * Test of setProfitPrice method, of class ProductPhotographer.
     */
    @Test
    public void testSetProfitPrice() {
        System.out.println("setProfitPrice");
        double newprofitPrice = 2.00;
        ProductPhotographer instance = productPhotographer;
        instance.setProfitPrice(newprofitPrice);
        double expResult = 2.00;
        double result = instance.getProfitPrice();
        assertEquals(expResult, result, 0.00);
    }

    /**
     * Test of getPhotograperProductId method, of class ProductPhotographer.
     */
    @Test
    public void testGetPhotograperProductId() {
        System.out.println("getPhotograperProductId");
        ProductPhotographer instance = productPhotographer;
        int expResult = 1;
        int result = instance.getPhotograperProductId();
        assertEquals(expResult, result);
    }
}

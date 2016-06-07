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
public class PhotographerTest {
    
    public PhotographerTest() {
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
     * Test of getID method, of class Photographer.
     */
    @Test
    public void testGetID() {
        Photographer instance = new Photographer(1, "test");
        int expResult = 1;
        int result = instance.getID();
        assertEquals("Id not as expected", expResult, result);
    }

    /**
     * Test of getName method, of class Photographer.
     */
    @Test
    public void testGetName() {
        Photographer instance = new Photographer(1, "test");
        String expResult = "test";
        String result = instance.getName();
        assertEquals("Returned name not as expected", expResult, result);
    }
    
}

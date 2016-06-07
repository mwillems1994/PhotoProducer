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
public class FilterTest {
    
    public FilterTest() {
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
     * Test of getId method, of class Filter.
     */
    @Test
    public void testGetId() {
        Filter instance = new Filter(1, "test");
        int expResult = 1;
        int result = instance.getId();
        assertEquals("Id not as expected", expResult, result);
    }

    /**
     * Test of getName method, of class Filter.
     */
    @Test
    public void testGetName() {
        Filter instance = new Filter(1, "test");
        String expResult = "test";
        String result = instance.getName();
        assertEquals("Returned name not as expected", expResult, result);
    }
    
}

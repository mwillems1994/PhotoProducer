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
public class PhotoTest {

    public PhotoTest() {
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
     * Test of getId method, of class Photo.
     */
    @Test
    public void testGetId() {
        Photo instance = new Photo(1, "testFilePath");
        int expResult = 1;
        int result = instance.getId();
        assertEquals("Id not as expected", expResult, result);
    }

    /**
     * Test of getFilePath method, of class Photo.
     */
    @Test
    public void testGetFilePath() {
        Photo instance = new Photo(1, "testFilePath");
        String expResult = "testFilePath";
        String result = instance.getFilePath();
        assertEquals("Returned name not as expected", expResult, result);
    }

}

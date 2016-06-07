/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import java.util.ArrayList;
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
public class AlbumTest {
    
    int albumID = 1;
    String albumnaam = "album1";
    ArrayList<Photo> photos;
    Customer customer = new Customer(1, "KevinCunt");
    Photo photo1 = new Photo(1, "pad1");
    Photo photo3 = new Photo(2, "pad2");
    Photo photo2 = new Photo(3, "pad3");
    Album album;
    
    public AlbumTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        photos = new ArrayList<>();
        photos.add(photo1);
        photos.add(photo2);
        photos.add(photo3);
        album = new Album(albumnaam, albumID);
    }
    
    @After
    public void tearDown() {
    }

        /**
     * Test of setPhotos method, of class Album.
     */
    @Test
    public void testGetSetPhotos() {
        System.out.println("setPhotos");
        ArrayList<Photo> givenPhotos = photos;
        Album instance = album;
        instance.setPhotos(givenPhotos);
        int expResult = 3;
        int result = instance.getPhotos().size();
        assertEquals(expResult, result);
    }

    /**
     * Test of setCustomer method, of class Album.
     */
    @Test
    public void testGetSetCustomer() {
        System.out.println("setCustomer");
        Customer givenCustomer = customer;
        Album instance = album;
        instance.setCustomer(givenCustomer);
        Customer expResult = customer;
        Customer result = instance.getCustomer();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNaam method, of class Album.
     */
    @Test
    public void testGetNaam() {
        System.out.println("getNaam");
        Album instance = album;
        String expResult = "album1";
        String result = instance.getNaam();
    }

    /**
     * Test of getID method, of class Album.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        Album instance = album;
        int expResult = 1;
        int result = instance.getID();
        assertEquals(expResult, result);
    }
}

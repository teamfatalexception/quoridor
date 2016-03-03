/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quoridorFE;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kyle
 */
public class PlayerTest {
    
    Player thePlayer = new Player(1, "Jesse", 8080, 0, 4, 4);

    /**
     * Setup the player class before each one of the tests are called
     */
    /*@Before
    public static void setUpClass() {
        Player thePlayer = new Player(1, "Jesse", 8080, 0, 4, 4);
    } */

    /**
     * Test of getName method, of class Player.
     */
    @Test
    public void testGetName() {
        assertEquals(thePlayer.getName(), "Jesse");
    }

    /**
     * Test of getID method, of class Player.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        Player instance = null;
        int expResult = 0;
        int result = instance.getID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of wallsLeft method, of class Player.
     */
    @Test
    public void testWallsLeft() {
        System.out.println("wallsLeft");
        Player instance = null;
        int expResult = 0;
        int result = instance.wallsLeft();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getX method, of class Player.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        Player instance = null;
        int expResult = 0;
        int result = instance.getX();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getY method, of class Player.
     */
    @Test
    public void testGetY() {
        System.out.println("getY");
        Player instance = null;
        int expResult = 0;
        int result = instance.getY();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of decrementWalls method, of class Player.
     */
    @Test
    public void testDecrementWalls() {
        System.out.println("decrementWalls");
        Player instance = null;
        instance.decrementWalls();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setX method, of class Player.
     */
    @Test
    public void testSetX() {
        System.out.println("setX");
        int v = 0;
        Player instance = null;
        instance.setX(v);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setY method, of class Player.
     */
    @Test
    public void testSetY() {
        System.out.println("setY");
        int v = 0;
        Player instance = null;
        instance.setY(v);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

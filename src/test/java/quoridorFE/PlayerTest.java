package quoridorFE;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kyle and Jesse
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
     * Test of getID method, of class Player.
     */
    @Test
    public void testGetID() {
        assertEquals(thePlayer.getID(), 1);
    }

    /**
     * Test of getName method, of class Player.
     */
    @Test
    public void testGetName() {
        assertEquals(thePlayer.getName(), "Jesse");
    }

    /**
     * Test of wallsLeft method, of class Player.
     */
    @Test
    public void testWallsLeft() {
        assertEquals(thePlayer.wallsLeft(), 0);
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

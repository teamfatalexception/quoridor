/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quoridorFE;

import java.util.ArrayList;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kyle
 */
public class MazeTest {
    
    public MazeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }

    /**
     * Test of placeWall method, of class Maze.
     */
    @Test
    public void testPlaceWall() {
        System.out.println("placeWall");
        int col = 0;
        int row = 0;
        String direction = "";
        Maze instance = null;
        instance.placeWall(col, row, direction);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Maze.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Maze instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of placePlayer method, of class Maze.
     */
    @Test
    public void testPlacePlayer() {
        System.out.println("placePlayer");
        ArrayList<Client> clients = null;
        Maze instance = null;
        instance.placePlayer(clients);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class Maze.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Maze.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

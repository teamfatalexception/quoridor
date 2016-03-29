
package quoridorFE;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;

/**
 *
 * @author Kyle
 */
public class ClientTest {
    
    public ClientTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }

    /**
     * Test of start method, of class Client.
     */
    @Ignore
    @Test
    public void testStart() {
        System.out.println("start");
        Client instance = null;
        boolean expResult = false;
        boolean result = instance.start();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendMessage method, of class Client.
     */
    @Ignore
    @Test
    public void testSendMessage() {
        System.out.println("sendMessage");
        String msg = "";
        Client instance = null;
        instance.sendMessage(msg);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of retrieveMessage method, of class Client.
     
    @Ignore
    @Test
    public void testRetrieveMessage() {
        System.out.println("retrieveMessage");
        Client instance = null;
        String expResult = "";
        String result = instance.retrieveMessage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    */

    /**
     * Test of main method, of class Client.
     */
    @Ignore
    @Test
    public void testMain() throws Exception {
        System.out.println("main");
        String[] args = null;
        Client.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of nextTurn method, of class Client.
     */
    @Ignore
    @Test
    public void testNextTurn() throws Exception {
        System.out.println("nextTurn");
        Client currentClient = null;
        int size = 0;
        Client.nextTurn(currentClient, size);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

   /**
    * Test of isWinner method, of class Client.
    **/
    @Test
    public void testIsWinner(){

	System.out.println("No or one players!");
	assertTrue(Client.isWinner());
    }

    @Test
    public void testIsWinner1(){
	System.out.println("First player makes it to goal.");
	Client.Board = new QuoridorBoard(new Player(1, "localhost", 8888, 5, 0, 0), new Player(2, "localhost", 9999, 5, 0, 0));
	//Client.Board.getNodeByCoords(4, 8).setPlayer(Client.Board.playerSet);
	Client.Board.movePawn(1, 5, 0);
	for(int i=1; i<9; i++){
		Client.Board.movePawn(1, 5, i);
	}
	System.out.println(Client.Board.getNodeByPlayerNumber(1).getxPos() + "  " + Client.Board.getNodeByPlayerNumber(1).getyPos());
	assertTrue(Client.isWinner());
    }

    /**
     * Test of isValidMove method, of class Client.
     */
    @Ignore
    @Test
    public void testIsValidMove() {
        System.out.println("isValidMove");
        boolean expResult = false;
        boolean result = Client.isValidMove();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

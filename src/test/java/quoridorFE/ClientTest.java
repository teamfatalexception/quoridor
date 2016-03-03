
package quoridorFE;

import org.junit.*;
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
     */
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
